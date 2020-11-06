package page.lamht.football.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.entity.Season;
import page.lamht.football.entity.Team;

@Service
@Transactional
public class SeasonService {

    private final static String INSERT_QUERY = "INSERT INTO public.season VALUES (?, ?, ?, ?, ?, ?)";
    private final static String UPDATE_QUERY = "UPDATE public.season SET start_date=?, end_date=?, current_matchday=?, winner_name=?, created=? WHERE id=?";

    @Autowired
    private SeasonRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Season findById(Long id) {
        String sql = "SELECT * FROM season s WHERE s.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<Season>(Season.class));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    public Integer countById(Long id) {
        String sql = "SELECT count(*) FROM season s WHERE s.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    public Season save(Season season) {

        Team t = season.getWinner();
        if (t != null) {
            season.setWinnerName(t.getName());
        }

        insertOrUpdate(season);
        return season;
    }

    private void insertOrUpdate(Season s) {
        if (countById(s.getId()) > 0)
            this.insert(s);
        else
            this.update(s);
    }

    private void insert(Season s) {
        jdbcTemplate.update(INSERT_QUERY,
                s.getId(), s.getStartDate(), s.getEndDate(), s.getCurrentMatchday(), s.getWinnerName(), s.getCreated()
        );
    }

    private void update(Season s) {
        jdbcTemplate.update(UPDATE_QUERY,
                s.getStartDate(), s.getEndDate(), s.getCurrentMatchday(), s.getWinnerName(), s.getCreated(), s.getId()
        );
    }

    public Long count() {

        return repository.count();
    }

}
