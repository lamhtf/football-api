package page.lamht.football.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.entity.Area;
import page.lamht.football.entity.Competition;
import page.lamht.football.entity.CompetitionTeam;
import page.lamht.football.entity.Season;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompetitionService {

    private final static Logger logger = LoggerFactory.getLogger(CompetitionService.class);

    private final static String INSERT_QUERY = "INSERT INTO public.competition VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    private final static String UPDATE_QUERY = "UPDATE public.competition SET area_id=?, \"name\"=?, code=?, emblem_url=?, plan=?, current_season_id=?, number_of_available_seasons=?, last_updated=?, created=? where id=? ";
    private final static String FIND_ALL = "SELECT * FROM competition c where c.id in (2021,2019,2002,2014,2017,2015,2003,2001) and c.last_updated>?";

    private final static String FIND_A_COMPETITION_HAS_UPDATE = "select count(1) from public.\"match\" m where m.competition_id=? and m.last_updated>?";

    @Autowired
    AreaService areaService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    CompetitionTeamService competitionTeamService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Competition> findAll(Timestamp lastUpdated) {
        try {
            List<Competition> competitionList = jdbcTemplate.query(FIND_ALL, new Object[]{lastUpdated}, new BeanPropertyRowMapper<Competition>(Competition.class));
            for (Competition c : competitionList) {
                Season s = seasonService.findById(c.getCurrentSeasonId());
                Area a = areaService.findById(c.getAreaId());
                c.setCurrentSeason(s);
                c.setArea(a);
            }
            return competitionList;
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public List<Competition> findAllByTeamId(Long teamId) {
        try {
            List<Competition> cs = new ArrayList<>();
            List<CompetitionTeam> cts = competitionTeamService.findAllByTeamId(teamId);
            for (CompetitionTeam ct : cts) {
                Competition c = findById(ct.getCompetitionId());
                Area a = areaService.findById(c.getAreaId());
                c.setArea(a);
                cs.add(c);
            }
            return cs;
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public Competition findById(Long id) {
        String sql = "SELECT * FROM competition c WHERE c.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<Competition>(Competition.class));
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public Integer countById(Long id) {
        String sql = "SELECT count(*) FROM competition c WHERE c.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
    }

    public Competition save(Competition competition) {
        Area a = competition.getArea();
        if (a != null) {
            competition.setAreaId(a.getId());
            competition.setArea(null);
            areaService.save(a);
        }

        Season s = competition.getCurrentSeason();
        if (s != null) {
            seasonService.save(s);
            competition.setCurrentSeasonId(s.getId());
        }

        this.insertOrUpdate(competition);
        return competition;
    }

    private void insertOrUpdate(Competition c) {
        if (countById(c.getId()) == 0)
            this.insert(c);
        else
            this.update(c);
    }

    private void insert(Competition c) {
        jdbcTemplate.update(INSERT_QUERY,
                c.getId(), c.getAreaId(), c.getName(), c.getCode(), c.getEmblemUrl(), c.getPlan(), c.getCurrentSeasonId(), c.getNumberOfAvailableSeasons(), c.getLastUpdated(), c.getCreated()
        );
    }

    private void update(Competition c) {
        jdbcTemplate.update(UPDATE_QUERY,
                c.getAreaId(), c.getName(), c.getCode(), c.getEmblemUrl(), c.getPlan(), c.getCurrentSeasonId(), c.getNumberOfAvailableSeasons(), c.getLastUpdated(), c.getCreated(), c.getId()
        );
    }

    public void directUpdate(Competition c, Competition db) {
        c.setAreaId(db.getAreaId());
        c.setCurrentSeasonId(db.getCurrentSeasonId());
        c.setNumberOfAvailableSeasons(db.getNumberOfAvailableSeasons());
        c.setEmblemUrl(db.getEmblemUrl());
        update(c);
    }

    public boolean hasUpdated(Long leagueId, Timestamp lastUpdated) {
        try {
            return jdbcTemplate.queryForObject(FIND_A_COMPETITION_HAS_UPDATE, new Object[]{leagueId, lastUpdated}, Integer.class) > 0;
        } catch (Exception e) {
            logger.error(e.toString());
            return false;
        }
    }

}
