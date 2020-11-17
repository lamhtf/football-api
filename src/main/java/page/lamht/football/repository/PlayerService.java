package page.lamht.football.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.dto.PersonDto;
import page.lamht.football.entity.Player;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class PlayerService {

    private final static String INSERT_PLAYER_QUERY = "INSERT INTO public.player (id, \"name\", first_name, last_name, date_of_birth, country_of_birth, nationality, \"position\", shirt_number, team_id, last_updated, created)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String INSERT_COACH_QUERY = "INSERT INTO public.coach (id, \"name\", first_name, last_name, date_of_birth, country_of_birth, nationality, \"position\", shirt_number, team_id, last_updated, created)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String DELETE_PLAYER_QUERY = "DELETE FROM public.player p WHERE p.team_id=?";
    private final static String DELETE_COACH_QUERY = "DELETE FROM public.coach c WHERE c.team_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Player> findByTeamId(Long teamId) {
        String sql = "SELECT * FROM player p WHERE p.team_id = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{teamId}, new BeanPropertyRowMapper<Player>(Player.class));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    public PersonDto save(Long teamId, PersonDto p) {
        this.insertOnly(teamId, p);
        return p;
    }

    private void insertOnly(Long tId, PersonDto p) {
        this.insert(tId, p);
    }

    private void insert(Long tId, PersonDto p) {
        String dob = p.getDateOfBirth()!=null? p.getDateOfBirth().substring(0,10):null;
        if ("PLAYER".equals(p.getRole()))
            jdbcTemplate.update(INSERT_PLAYER_QUERY,
                    p.getId(), p.getName(), p.getFirstName(), p.getLastName(), dob, p.getCountryOfBirth(),
                    p.getNationality(), p.getPosition(), p.getShirtNumber(), tId, p.getLastUpdated(), new Timestamp(System.currentTimeMillis())
            );
        else
            jdbcTemplate.update(INSERT_COACH_QUERY,
                    p.getId(), p.getName(), p.getFirstName(), p.getLastName(), dob, p.getCountryOfBirth(),
                    p.getNationality(), p.getPosition(), p.getShirtNumber(), tId, p.getLastUpdated(), new Timestamp(System.currentTimeMillis())
            );
    }

    public void deleteAll(Long teamId) {
        jdbcTemplate.update(DELETE_PLAYER_QUERY,
                teamId
        );
        jdbcTemplate.update(DELETE_COACH_QUERY,
                teamId
        );
    }
}