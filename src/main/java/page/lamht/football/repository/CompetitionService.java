package page.lamht.football.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import page.lamht.football.entity.*;
import page.lamht.football.mapper.MatchMapper;
import page.lamht.football.mo.MatchMo;
import page.lamht.football.mo.MatchResponse;
import page.lamht.football.util.Utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompetitionService {

    private final static String INSERT_QUERY = "INSERT INTO public.competition VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    private final static String UPDATE_QUERY = "UPDATE public.competition SET area_id=?, \"name\"=?, code=?, emblem_url=?, plan=?, current_season_id=?, number_of_available_seasons=?, last_updated=?, created=? where id=? ";
    private final static String FIND_ALL = "SELECT * FROM competition c where c.id in (2021,2019,2002,2014,2017,2015,2003,2001) and c.last_updated>?";

    @Autowired
    AreaService areaService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    CompetitionTeamService competitionTeamService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Competition> findAll(Timestamp lastUpdated) {
        List<Competition> competitionList = jdbcTemplate.query(FIND_ALL, new Object[]{lastUpdated}, new BeanPropertyRowMapper<Competition>(Competition.class));
        for (Competition c: competitionList){
            Season s = seasonService.findById(c.getCurrentSeasonId());
            Area a = areaService.findById(c.getAreaId());
            c.setCurrentSeason(s);
            c.setArea(a);
        }
        return competitionList;
    }

    public List<Competition> findAllByTeamId(Long teamId){
        List<Competition> cs = new ArrayList<>();
        List<CompetitionTeam> cts = competitionTeamService.findAllByTeamId(teamId);
        for (CompetitionTeam ct: cts){
            Competition c = findById(ct.getCompetitionId());
            Area a = areaService.findById(c.getAreaId());
            c.setArea(a);
            cs.add(c);
        }
        return cs;
    }

    public Competition findById(Long id) {
        String sql = "SELECT * FROM competition c WHERE c.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<Competition>(Competition.class));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
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
        if (s != null){
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

    public void directUpdate(Competition c, Competition db){
        c.setAreaId(db.getAreaId());
        c.setCurrentSeasonId(db.getCurrentSeasonId());
        c.setNumberOfAvailableSeasons(db.getNumberOfAvailableSeasons());
        c.setEmblemUrl(db.getEmblemUrl());
        update(c);
    }

}
