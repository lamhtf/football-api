package page.lamht.football.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import page.lamht.football.entity.Area;
import page.lamht.football.entity.Competition;
import page.lamht.football.entity.Season;

@Service
public class CompetitionService {

    private final static String INSERT_QUERY = "INSERT INTO public.competition VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    private final static String UPDATE_QUERY = "UPDATE public.competition SET area_id=?, \"name\"=?, code=?, emblem_url=?, plan=?, current_season_id=?, number_of_available_seasons=?, last_updated=?, created=? where id=? ";

    @Autowired
    AreaService areaService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    private CompetitionRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Competition findById(Long id) {

        String sql = "SELECT * FROM competition c WHERE c.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<Competition>(Competition.class));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    public Competition save(Competition competition) {

        Area a = competition.getArea();
        if (a != null) {
            competition.setAreaId(a.getId());
            competition.setArea(null);
            areaService.save(a);
        }

        Season s = competition.getCurrentSeason();
        if (s != null){
            seasonService.save(s);
            competition.setCurrentSeasonId(s.getId());
        }

        this.insertOrUpdate(competition);
        return competition;
    }

    private void insertOrUpdate(Competition c) {
        Competition dbInstance = this.findById(c.getId());
        if (dbInstance == null)
            this.insert(c);
        else
            this.update(c);
    }

    private void insert(Competition c) {
        jdbcTemplate.update(INSERT_QUERY,
                c.getId(), c.getAreaId(), c.getName(), c.getCode(), c.getEmblemUrl(), c.getPlan(), c.getCurrentSeasonId(), c.getNumberOfAvailableSeasons(), c.getLastUpdated(), c.getCreated()
        );
    }

    private void update(Competition c) {
        jdbcTemplate.update(UPDATE_QUERY,
                c.getAreaId(), c.getName(), c.getCode(), c.getEmblemUrl(), c.getPlan(), c.getCurrentSeasonId(), c.getNumberOfAvailableSeasons(), c.getLastUpdated(), c.getCreated(), c.getId()
        );
    }

    public Long count() {

        return repository.count();
    }
}
