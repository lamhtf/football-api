package page.lamht.football.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.entity.Area;
import page.lamht.football.entity.Season;

@Service
@Transactional
public class AreaService {

    private final static String INSERT_QUERY = "INSERT INTO public.area VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final static String UPDATE_QUERY = "UPDATE public.area SET \"name\"=?, country_code=?, ensign_url=?, parent_area_id=?, parent_area=?, created=? WHERE id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Area findById(Long id) {
        String sql = "SELECT * FROM area a WHERE a.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<Area>(Area.class));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    public Integer countById(Long id) {
        String sql = "SELECT count(*) FROM area a WHERE a.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
    }

    public Area save(Area area) {

        this.insertOrUpdate(area);
        return area;
    }

    private void insertOrUpdate(Area a) {
        if (countById(a.getId()) == 0)
            this.insert(a);
//        else update(a);
    }

    private void insert(Area a) {
        jdbcTemplate.update(INSERT_QUERY,
                a.getId(), a.getName(), a.getCountryCode(), a.getEnsignUrl(), a.getParentAreaId(), a.getParentArea(), a.getCreated()
        );
    }

    private void update(Area a) {
        jdbcTemplate.update(UPDATE_QUERY,
                a.getName(), a.getCountryCode(), a.getEnsignUrl(), a.getParentAreaId(), a.getParentArea(), a.getCreated(), a.getId()
        );
    }

}
