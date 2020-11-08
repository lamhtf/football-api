package page.lamht.football.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.entity.CompetitionTeam;
import page.lamht.football.entity.Team;

import java.util.List;

@Service
@Transactional
public class CompetitionTeamService {

    private final static String INSERT_QUERY = "INSERT INTO public.competition_team (competition_id, team_id, created) VALUES (?,?,?)";
    private final static String DELETE_QUERY = "DELETE FROM public.competition_team WHERE competition_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CompetitionTeam> findByCompetitionId(Long competitionId) {

        String sql = "SELECT * FROM competition_team ct INNER JOIN team t on ct.team_id = t.id WHERE ct.competition_id=?";
        try {
//            List<Team> teams = jdbcTemplate.query(sql, new Object[]{competitionId}, new BeanPropertyRowMapper<Team>(Team.class));
            return jdbcTemplate.query(sql, new Object[]{competitionId}, new BeanPropertyRowMapper<CompetitionTeam>(CompetitionTeam.class));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

//    public List<Customer> findAll() {
//        String sql = "SELECT * FROM CUSTOMER";
//        return jdbcTemplate.query(
//                sql,
//                (rs, rowNum) ->
//                        new Customer(
//                                rs.getLong("id"),
//                                rs.getString("name"),
//                                rs.getInt("age"),
//                                rs.getTimestamp("created_date").toLocalDateTime()
//                        )
//        );
//    }

    public CompetitionTeam findByBothId(Long competitionId, Long teamId) {

        String sql = "SELECT * FROM competition_team ct WHERE ct.competition_id=? and ct.team_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{competitionId, teamId}, new BeanPropertyRowMapper<CompetitionTeam>(CompetitionTeam.class));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    public Integer countByCompetitionId(Long competitionId) {

        String sql = "SELECT count(*) FROM competition_team ct WHERE ct.competition_id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{competitionId}, Integer.class);
    }

    public CompetitionTeam save(CompetitionTeam ct) {
        this.insertOnly(ct);
        return ct;
    }

    private void insertOnly(CompetitionTeam ct) {
//        CompetitionTeam dbInstance = this.findByBothId(ct.getCompetitionId(), ct.getTeamId());
//        if (dbInstance == null)
        this.insert(ct);
    }

    private void insert(CompetitionTeam ct) {
        jdbcTemplate.update(INSERT_QUERY,
                ct.getCompetitionId(), ct.getTeamId(), ct.getCreated()
        );
    }

    public void deleteAll(CompetitionTeam ct) {
        Integer count = countByCompetitionId(ct.getCompetitionId());
        if (count > 0)
            jdbcTemplate.update(DELETE_QUERY,
                    ct.getCompetitionId()
            );
    }

    public CompetitionTeam newInstance(Long competitionId, Long teamId){
        CompetitionTeam ct = new CompetitionTeam();
        ct.setCompetitionId(competitionId);
        ct.setTeamId(teamId);
        return ct;
    }
}
