package page.lamht.football.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.dto.PersonDto;
import page.lamht.football.entity.Coach;
import page.lamht.football.entity.Player;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class PlayerService {

    private final static Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private final static String INSERT_PLAYER_QUERY = "INSERT INTO public.player (id, \"name\", first_name, last_name, date_of_birth, country_of_birth, nationality, \"position\", shirt_number, team_id, last_updated, created)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String INSERT_COACH_QUERY = "INSERT INTO public.coach (id, \"name\", first_name, last_name, date_of_birth, country_of_birth, nationality, \"position\", shirt_number, team_id, last_updated, created)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String DELETE_PLAYER_QUERY = "DELETE FROM public.player p WHERE p.team_id=?";
    private final static String DELETE_COACH_QUERY = "DELETE FROM public.coach c WHERE c.team_id=?";
    private final static String FIND_COACH_BY_TEAM_ID = "select * from coach c where c.team_id = ? order by date_of_birth";
    private final static String FIND_BY_TEAM_ID = "select * from player p where team_id=? order by position='Attacker',position='Midfielder',position='Defender',position='Goalkeeper',date_of_birth";
//    private final static String FIND_PLAYER_BY_TEAM_ID = "select * from player p where p.team_id =? and p.id=?";
    private final static String FIND_PLAYER = "select * from player p where p.id=?";
//    private final static String COUNT_PLAYER = "select count(*) from player p where p.team_id =? and p.id=?";
    private final static String COUNT_PLAYER = "select count(*) from player p where p.team_id =? and p.id=?";
    private final static String COUNT_COACH = "select count(*) from coach c where c.team_id =? and c.id=?";
    private final static String POST_SCHEDULE_JOB_DATA_PATCH_01 = "update public.player set id = 3343, country_of_birth ='Korea Republic',nationality ='Korea Republic', \"position\"='Attacker' where id = 170281";
    private final static String POST_SCHEDULE_JOB_DATA_PATCH_02 = "update public.player set country_of_birth ='Argentina',nationality ='Argentina', \"position\"='Defender' where id in (170280, 169310)";
    private final static String POST_SCHEDULE_JOB_DATA_PATCH_03 = "update public.player set id = 93, country_of_birth ='Spain',nationality ='Spain', \"position\"='Attacker' where id = 170047";
    private final static String POST_SCHEDULE_JOB_DATA_PATCH_04 = "INSERT INTO public.player (id, \"name\", first_name, last_name, date_of_birth, country_of_birth, nationality, \"position\", shirt_number, team_id, last_updated, created) VALUES(74554, 'Simon Banza', 'Simon Bokote', null, '1996-08-13', 'France', 'France', 'Attacker', 23, 5531, '2020-09-07T21:05:38Z', now())";



    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void postScheduleJobDataPatch() {
        try {
            jdbcTemplate.execute(POST_SCHEDULE_JOB_DATA_PATCH_01);
            jdbcTemplate.execute(POST_SCHEDULE_JOB_DATA_PATCH_02);
            jdbcTemplate.execute(POST_SCHEDULE_JOB_DATA_PATCH_03);
            jdbcTemplate.execute(POST_SCHEDULE_JOB_DATA_PATCH_04);
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    public List<Coach> findCoachesByTeamId(Long teamId) {
        try {
            return jdbcTemplate.query(FIND_COACH_BY_TEAM_ID, new Object[]{teamId}, new BeanPropertyRowMapper<Coach>(Coach.class));
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public List<Player> findPlayersByTeamId(Long teamId) {
        try {
            return jdbcTemplate.query(FIND_BY_TEAM_ID, new Object[]{teamId}, new BeanPropertyRowMapper<Player>(Player.class));
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public Player findPlayerByTeamAndPlayerId(Long teamId, Long playerId) {
        try {
            List<Player> players = jdbcTemplate.query(FIND_PLAYER, new Object[]{playerId}, new BeanPropertyRowMapper<Player>(Player.class));
            return players.get(0);
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public Integer countPlayerByTeamAndPlayerId(Long teamId, Long playerId) {
        return jdbcTemplate.queryForObject(COUNT_PLAYER, new Object[]{teamId, playerId}, Integer.class);
    }

    public Integer countCoachByTeamAndPlayerId(Long teamId, Long playerId) {
        return jdbcTemplate.queryForObject(COUNT_COACH, new Object[]{teamId, playerId}, Integer.class);
    }

    public PersonDto save(Long teamId, PersonDto p) {
        this.insertOnly(teamId, p);
        return p;
    }

    private void insertOnly(Long tId, PersonDto p) {
        this.insert(tId, p);
    }

    private void insert(Long tId, PersonDto p) {
        String dob = p.getDateOfBirth() != null ? p.getDateOfBirth().substring(0, 10) : null;
        if ("PLAYER".equals(p.getRole()) || p.getRole() == null) {
            if (countPlayerByTeamAndPlayerId(tId, p.getId()) > 0) {
                logger.warn("Duplicate player id = " + p.getId() + " " + p.getName() + " in team id = " + tId);
                return;
            }
            jdbcTemplate.update(INSERT_PLAYER_QUERY,
                    p.getId(), p.getName(), p.getFirstName(), p.getLastName(), dob, p.getCountryOfBirth(),
                    p.getNationality(), p.getPosition(), p.getShirtNumber(), tId, p.getLastUpdated(), new Timestamp(System.currentTimeMillis())
            );
        } else {
            if (countCoachByTeamAndPlayerId(tId, p.getId()) > 0) {
                logger.warn("Duplicate coach id = " + p.getId() + " " + p.getName() + " in team id = " + tId);
                return;
            }
            jdbcTemplate.update(INSERT_COACH_QUERY,
                    p.getId(), p.getName(), p.getFirstName(), p.getLastName(), dob, p.getCountryOfBirth(),
                    p.getNationality(), p.getPosition(), p.getShirtNumber(), tId, p.getLastUpdated(), new Timestamp(System.currentTimeMillis())
            );
        }
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
