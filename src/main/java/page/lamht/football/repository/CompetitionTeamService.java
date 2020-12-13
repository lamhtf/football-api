package page.lamht.football.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.entity.CompetitionTeam;
import page.lamht.football.mo.FavouriteTeamMo;

import java.util.List;

@Service
@Transactional
public class CompetitionTeamService {

    private final static String INSERT_QUERY = "INSERT INTO public.competition_team (competition_id, team_id, created) VALUES (?,?,?)";
    private final static String DELETE_QUERY = "DELETE FROM public.competition_team WHERE competition_id=?";
    private final static String LIST_FAVOURITES = "select c.id as competition_id, c.code, c.name as competition_name, t.id as team_id, t.short_name, crest_url, club_colors from public.competition c, public.competition_team ct, team t where c.id = ct.competition_id and ct.team_id = t.id and c.id in (2021, 2019, 2002, 2014, 2017, 2015, 2003) order by code, short_name";
    private final static String LIST_BY_COMPETITION_ID = "SELECT * FROM competition_team ct INNER JOIN team t on ct.team_id = t.id WHERE ct.competition_id=?";
    private final static String LIST_BY_BOTH_ID = "SELECT * FROM competition_team ct WHERE ct.competition_id=? and ct.team_id = ?";
    private final static String COUNT_BY_COMPETITION_ID = "SELECT count(*) FROM competition_team ct WHERE ct.competition_id=?";
    private final static String LIST_COMPETITIONS_BY_TEAM_ID = "select * from competition_team ct where ct.team_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CompetitionTeam> findAllByTeamId(Long teamId){
        return jdbcTemplate.query(LIST_COMPETITIONS_BY_TEAM_ID, new Object[]{teamId}, new BeanPropertyRowMapper<CompetitionTeam>(CompetitionTeam.class));
    }

    public List<FavouriteTeamMo> findFavourites(){
        return jdbcTemplate.query(LIST_FAVOURITES, new BeanPropertyRowMapper<FavouriteTeamMo>(FavouriteTeamMo.class));
    }

    public List<CompetitionTeam> findByCompetitionId(Long competitionId) {

        try {
//            List<Team> teams = jdbcTemplate.query(sql, new Object[]{competitionId}, new BeanPropertyRowMapper<Team>(Team.class));
            return jdbcTemplate.query(LIST_BY_COMPETITION_ID, new Object[]{competitionId}, new BeanPropertyRowMapper<CompetitionTeam>(CompetitionTeam.class));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    public CompetitionTeam findByBothId(Long competitionId, Long teamId) {

        try {
            return jdbcTemplate.queryForObject(LIST_BY_BOTH_ID, new Object[]{competitionId, teamId}, new BeanPropertyRowMapper<CompetitionTeam>(CompetitionTeam.class));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    public Integer countByCompetitionId(Long competitionId) {

        return jdbcTemplate.queryForObject(COUNT_BY_COMPETITION_ID, new Object[]{competitionId}, Integer.class);
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
