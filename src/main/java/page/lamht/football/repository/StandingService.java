package page.lamht.football.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.dto.StandingDto;
import page.lamht.football.dto.StandingsDto;
import page.lamht.football.entity.Area;
import page.lamht.football.entity.Competition;
import page.lamht.football.entity.Standing;
import page.lamht.football.entity.Standings;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class StandingService {

    private final static String INSERT_QUERY = "INSERT INTO public.standings (competition_id, stage, \"type\", \"group\")VALUES(?,?,?,?)";
    private final static String INSERT_SS_QUERY = "INSERT INTO public.standing VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String DELETE_ALL_SS_QUERY = "DELETE FROM public.standing s WHERE s.standings_id = ?";
    private final static String GET_POSITION_BY_LEAGUE_ID_TEAM_ID = "SELECT standings_id, \"position\", team_id, team_name, team_crest_url, played_games, form, won, draw, lost, points, goals_for, goals_against, goal_difference FROM public.standings ss, public.standing s where ss.id=s.standings_id and ss.\"type\" = 'TOTAL' and ss.competition_id = ? and s.team_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TeamService teamService;

    @Autowired
    private CompetitionService competitionService;

    public List<Standings> findAllByCompetitionId(Long competitionId, Timestamp lastUpdated) {
        String sql = "SELECT s.* FROM standings s, competition c WHERE c.id=s.competition_id and s.competition_id=? and c.last_updated>?";
        String sql2 = "SELECT s.*, t.short_name FROM standing s, team t WHERE s.team_id = t.id and s.standings_id=? order by s.position";

        List<Standings> standings = jdbcTemplate.query(sql, new Object[]{competitionId, lastUpdated}, new BeanPropertyRowMapper<Standings>(Standings.class));
        for (Standings ss : standings) {
            List<Standing> table = jdbcTemplate.query(sql2, new Object[]{ss.getId()}, new BeanPropertyRowMapper<Standing>(Standing.class));
            ss.setTable(table);
        }
        try {
            return standings;
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    public Standings findByAll(Long competitionId, String stage, String type, String group) {
        String sql = "SELECT * FROM standings s WHERE s.competition_id=? and s.stage=? and s.type=?";
        try {
            if (group != null) {
                sql += " and s.group=?";
                return jdbcTemplate.queryForObject(sql, new Object[]{competitionId, stage, type, group}, new BeanPropertyRowMapper<Standings>(Standings.class));
            }
            return jdbcTemplate.queryForObject(sql, new Object[]{competitionId, stage, type}, new BeanPropertyRowMapper<Standings>(Standings.class));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    public Integer countByAll(Long competitionId, String stage, String type, String group) {
        String sql = "SELECT count(*) FROM standings s WHERE s.competition_id=? and s.stage=? and s.type=?";
        if (group != null) {
            sql += " and s.group=?";
            return jdbcTemplate.queryForObject(sql, new Object[]{competitionId, stage, type, group}, Integer.class);
        }
        return jdbcTemplate.queryForObject(sql, new Object[]{competitionId, stage, type}, Integer.class);
    }

    public StandingDto save(StandingDto dto) {
        Competition c = dto.getCompetition();
        Competition db = competitionService.findById(c.getId());
        if (c.getLastUpdated().after(db.getLastUpdated())) {
            competitionService.directUpdate(c, db);
            insertOnly(dto);
        }
        return dto;
    }

    public StandingDto init(StandingDto dto) {
        insertOnly(dto);
        return dto;
    }

    private void insertOnly(StandingDto dto) {
        List<StandingsDto> sList = dto.getStandings();
        Competition c = dto.getCompetition();

        for (StandingsDto s : sList) {

            if (countByAll(c.getId(), s.getStage(), s.getType(), s.getGroup()) == 0)
                this.insert(s, c);
            Standings dbInstance = findByAll(c.getId(), s.getStage(), s.getType(), s.getGroup());
            Long sId = dbInstance.getId();
            deleteAllStatsByStandingsId(sId);
            List<StandingsDto.StandingStatistic> statisticsList = s.getTable();
            for (StandingsDto.StandingStatistic stats : statisticsList) {
                insertStatistics(sId, stats);
            }
        }
    }

    private void insert(StandingsDto s, Competition c) {
        jdbcTemplate.update(INSERT_QUERY,
                c.getId(), s.getStage(), s.getType(), s.getGroup()
        );
    }

    private void insertStatistics(Long sId, StandingsDto.StandingStatistic ss) {
        StandingsDto.StandingTeam st = ss.getTeam();
        jdbcTemplate.update(INSERT_SS_QUERY,
                sId, ss.getPosition(), st.getId(), st.getName(), st.getCrestUrl(), ss.getPlayedGames(),
                ss.getForm(), ss.getWon(), ss.getDraw(), ss.getLost(), ss.getPoints(), ss.getGoalsFor(),
                ss.getGoalsAgainst(), ss.getGoalDifference()
        );
    }

    private void deleteAllStatsByStandingsId(Long sId) {
        jdbcTemplate.update(DELETE_ALL_SS_QUERY, sId);
    }

    public Standing findStandingByLeagueIdAndTeamId(Long leagueId, Long teamId) {
        try {
            return jdbcTemplate.queryForObject(GET_POSITION_BY_LEAGUE_ID_TEAM_ID, new Object[]{leagueId, teamId}, new BeanPropertyRowMapper<Standing>(Standing.class));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }
}
