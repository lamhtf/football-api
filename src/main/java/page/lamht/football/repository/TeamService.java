package page.lamht.football.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.entity.Area;
import page.lamht.football.entity.Competition;
import page.lamht.football.entity.Team;

@Service
@Transactional
public class TeamService {

    private final static String INSERT_QUERY = "INSERT INTO public.team VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String UPDATE_QUERY = "UPDATE public.team SET area_id=?, \"name\"=?, short_name=?, tla=?, crest_url=?, address=?, phone=?, website=?, email=?, founded=?, club_colors=?, venue=?, last_updated=?, created=? WHERE id=?";

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

    public Team save(Team team) {

        this.insertOrUpdate(team);
        return team;
    }

    private void insertOrUpdate(Team t) {
        Team dbInstance = this.findById(t.getId());
        if (dbInstance == null)
            this.insert(t);
        else
            this.update(t);
    }

    private void insert(Team t) {
        jdbcTemplate.update(INSERT_QUERY,
                t.getId(), t.getArea().getId(), t.getName(), t.getShortName(), t.getTla(), t.getCrestUrl(),
                t.getAddress(), t.getPhone(), t.getWebsite(), t.getEmail(), t.getFounded(), t.getClubColors(),
                t.getVenue(), t.getLastUpdated(), t.getCreated()
        );
    }

    private void update(Team t) {
        jdbcTemplate.update(UPDATE_QUERY,
                t.getArea().getId(), t.getName(), t.getShortName(), t.getTla(), t.getCrestUrl(),
                t.getAddress(), t.getPhone(), t.getWebsite(), t.getEmail(), t.getFounded(), t.getClubColors(),
                t.getVenue(), t.getLastUpdated(), t.getCreated(), t.getId()
        );
    }
}
