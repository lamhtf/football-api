package page.lamht.football.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.dto.PersonDto;
import page.lamht.football.entity.Coach;
import page.lamht.football.entity.Competition;
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
    private final static String FIND_COACH_BY_TEAM_ID = "select * from coach c where c.team_id = ? order by date_of_birth";
    private final static String FIND_BY_TEAM_ID = "select * from player p where team_id=? order by position='Attacker',position='Midfielder',position='Defender',position='Goalkeeper',date_of_birth";
    private final static String FIND_PLAYER = "select * from player p where p.id=? ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Coach> findCoachesByTeamId(Long teamId) {
        return jdbcTemplate.query(FIND_COACH_BY_TEAM_ID, new Object[]{teamId}, new BeanPropertyRowMapper<Coach>(Coach.class));
    }

    public List<Player> findPlayersByTeamId(Long teamId) {
        return jdbcTemplate.query(FIND_BY_TEAM_ID, new Object[]{teamId}, new BeanPropertyRowMapper<Player>(Player.class));
    }

    public Player findPlayerById(Long playerId){
        try {
            return jdbcTemplate.queryForObject(FIND_PLAYER, new Object[]{playerId}, new BeanPropertyRowMapper<Player>(Player.class));
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
