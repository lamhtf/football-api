package page.lamht.football.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import page.lamht.football.entity.Competition;
import page.lamht.football.entity.Team;

@Service
public class TeamService {

    @Autowired
    private TeamRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Team findById(Long id) {

        String sql = "SELECT * FROM team t WHERE t.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<Team>(Team.class));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    public Team save(Team team){

        Team s = this.findById(team.getId());
        if (s == null){
            team.setNew(true);
        }
        return repository.save(team);
    }

    public Long count() {

        return repository.count();
    }

}
