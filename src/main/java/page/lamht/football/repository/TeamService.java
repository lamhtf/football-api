package page.lamht.football.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.entity.*;

import java.util.List;

@Service
@Transactional
public class TeamService {

    private final static Logger logger = LoggerFactory.getLogger(TeamService.class);

    private final static String INSERT_QUERY = "INSERT INTO public.team VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String UPDATE_QUERY = "UPDATE public.team SET area_id=?, \"name\"=?, short_name=?, tla=?, crest_url=?, address=?, phone=?, website=?, email=?, founded=?, club_colors=?, venue=?, last_updated=?, created=? WHERE id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AreaService areaService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private CompetitionService competitionService;

    public Team findTeamDetailById(Long teamId){
        Team team = findById(teamId);
        Area area = areaService.findById(team.getAreaId());
        List<Coach> coaches = playerService.findCoachesByTeamId(teamId);
        List<Player> players = playerService.findPlayersByTeamId(teamId);
        List<Competition> ccompetitions = competitionService.findAllByTeamId(teamId);
        team.setArea(area);
        team.setCoaches(coaches);
        team.setPlayers(players);
        team.setActiveCompetitions(ccompetitions);
        return team;
    }

    public Team findById(Long id) {
        String sql = "SELECT * FROM team t WHERE t.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<Team>(Team.class));
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public Integer countById(Long id) {
        String sql = "SELECT count(*) FROM team t WHERE t.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
    }

    public List<Team> findByCompetitionId(Long competitionId) {
        String sql = "SELECT * FROM competition_team ct INNER JOIN team t on ct.team_id = t.id WHERE ct.competition_id=?";
        try {
            return jdbcTemplate.query(sql, new Object[]{competitionId}, new BeanPropertyRowMapper<Team>(Team.class));
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public Team save(Team team) {
        this.insertOrUpdate(team);
        return team;
    }

    private void insertOrUpdate(Team t) {
        if (countById(t.getId()) == 0)
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
