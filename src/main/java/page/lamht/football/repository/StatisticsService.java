package page.lamht.football.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.entity.*;

import java.util.List;

@Service
@Transactional
public class StatisticsService {

    private final static String INSERT_SCORER_QUERY = "INSERT INTO public.scorer (id, competition_id, season_id, player_id, team_id, number_of_goals) VALUES(?, ?, ?, ?, ?, ?)";
    private final static String UPDATE_SCORER_QUERY = "UPDATE public.scorer SET number_of_goals=? WHERE competition_id=?, season_id=?, id=?, team_id=?";

    private final static String FIND_SCORER = "SELECT * FROM scorer s WHERE s.competition_id=? and s.season_id=? and s.id=? and s.team_id=?";
    private final static String COUNT_SCORER = "SELECT count(*) FROM scorer s WHERE s.competition_id=? and s.season_id=? and s.id=? and s.team_id=?";

    private final static String FIND_SCORER_BY_COMPETITION_ID = "SELECT * FROM scorer s WHERE s.competition_id=? limit ?";
    private final static String FIND_SCORER_BY_TEAM_ID = "SELECT * FROM scorer s WHERE s.team_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Scorer> findScorerByLeagueId(Long competitionId, Integer leagueLimit){
        return jdbcTemplate.query(FIND_SCORER_BY_COMPETITION_ID, new Object[]{competitionId}, new BeanPropertyRowMapper<Scorer>(Scorer.class));
    }

    public List<Scorer> findScorerByTeamId(Long teamId){
        return jdbcTemplate.query(FIND_SCORER_BY_TEAM_ID, new Object[]{teamId}, new BeanPropertyRowMapper<Scorer>(Scorer.class));
    }

    public Scorer findScorerByIds(Long competitionId, Long seasonId, Long playerId, Long teamId) {
        try {
            return jdbcTemplate.queryForObject(FIND_SCORER, new Object[]{competitionId, seasonId, playerId, teamId}, new BeanPropertyRowMapper<Scorer>(Scorer.class));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    public Integer countScorerByIds(Long competitionId, Long seasonId, Long playerId, Long teamId) {
        return jdbcTemplate.queryForObject(COUNT_SCORER, new Object[]{competitionId, seasonId, playerId, teamId}, Integer.class);
    }

    public String saveScorers(Competition competition, Season season, List<Scorer> scorers) {

        Long competitionId = competition.getId();
        Long seasonId = season.getId();
        for (Scorer s: scorers) {
            Long playerId = s.getPlayer().getId();
            Long teamId = s.getTeam().getId();
            Integer goals = s.getNumberOfGoals();
            insertOrUpdate(competitionId, seasonId, playerId, teamId, goals);
        }
        return "";
    }

    private void insertOrUpdate(Long cId, Long sId, Long pId, Long tId, Integer goals) {
        if (countScorerByIds(cId, sId, pId, tId) == 0)
            this.insert(cId, sId, pId, tId, goals);
        else
            this.update(cId, sId, pId, tId, goals);
    }

    private void insert(Long cId, Long sId, Long pId, Long tId, Integer goals) {
        jdbcTemplate.update(INSERT_SCORER_QUERY,
                pId, cId, sId, pId, tId, goals
        );
    }

    private void update(Long cId, Long sId, Long pId, Long tId, Integer goals) {
        jdbcTemplate.update(UPDATE_SCORER_QUERY,
                goals, cId, sId, pId, tId
        );
    }

}
