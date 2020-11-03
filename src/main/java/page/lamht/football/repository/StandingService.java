package page.lamht.football.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.dto.MatchDto;
import page.lamht.football.entity.Competition;
import page.lamht.football.entity.Match;
import page.lamht.football.entity.Referee;
import page.lamht.football.entity.Season;

import java.sql.Timestamp;

@Service
@Transactional
public class StandingService {

    private final static String INSERT_QUERY = "INSERT INTO public.match VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final static String UPDATE_QUERY = "UPDATE public.\"match\" SET utc_date=?, status=?, venue=?, matchday=?, stage=?, \"group\"=?, last_updated=?, number_of_matches=?, total_goals=?, home_team_id=?, home_team_name=?, home_team_wins=?, home_team_draws=?, home_team_losses=?, away_team_id=?, away_team_name=?, away_team_wins=?, away_team_draws=?, away_team_losses=?, winner=?, duration=?, full_time_home_team=?, full_time_away_team=?, half_time_home_team=?, half_time_away_team=?, extra_time_home_team=?, extra_time_away_team=?, penalties_home_team=?, penalties_away_team=?, referee_id=?, referee_name=?, competition_id=?, season_id=?, created=? WHERE id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TeamService teamService;

    public Match findById(Long id) {

        String sql = "SELECT * FROM standing s WHERE s.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<Match>(Match.class));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    public Match save(MatchDto dto) {

        insertOrUpdate(dto);
        return this.findById(dto.getId());
    }

    private void insertOrUpdate(MatchDto m) {
        Match dbInstance = this.findById(m.getId());
        if (dbInstance == null)
            this.insert(m);
        else
            this.update(m);
    }

    private void insert(MatchDto m) {
        MatchDto.MatchScore s = m.getScore();
        MatchDto.MatchHomeAway f = new MatchDto.MatchHomeAway(),
                h = new MatchDto.MatchHomeAway(),
                e = new MatchDto.MatchHomeAway(),
                p = new MatchDto.MatchHomeAway();
        MatchDto.MatchTeam
                home = m.getHomeTeam(), away = m.getAwayTeam();
        Referee r = new Referee();
        String winner = null, duration = null;
        if (s != null) {
            f = s.getFullTime();
            h = s.getHalfTime();
            e = s.getExtraTime();
            p = s.getPenalties();
            winner = s.getWinner();
            duration = s.getDuration();
        }
        if (m.getReferees().size() > 0)
            r = m.getReferees().get(0);
        Long cId = null, sId = null;
        Competition c = m.getCompetition();
        if (c!=null) cId = c.getId();
        Season se = m.getSeason();
        if (se!=null) sId = se.getId();


        jdbcTemplate.update(INSERT_QUERY,
                m.getId(), m.getUtcDate(), m.getStatus(), m.getVenue(), m.getMatchday(), m.getStage(),
                m.getGroup(), m.getLastUpdated(), m.getNumberOfMatches(), m.getTotalGoals(), home.getId(), home.getName(),
                home.getWins(), home.getDraws(), home.getLosses(), away.getId(), away.getName(), away.getWins(),
                away.getDraws(), away.getLosses(), winner, duration, f.getHomeTeam(), f.getAwayTeam(),
                h.getHomeTeam(), h.getAwayTeam(), e.getHomeTeam(), e.getAwayTeam(), p.getHomeTeam(), p.getAwayTeam(),
                r.getId(), r.getName(), cId, sId, new Timestamp(System.currentTimeMillis())
        );
    }

    private void update(MatchDto m) {
        MatchDto.MatchScore s = m.getScore();
        MatchDto.MatchHomeAway f = new MatchDto.MatchHomeAway(),
                h = new MatchDto.MatchHomeAway(),
                e = new MatchDto.MatchHomeAway(),
                p = new MatchDto.MatchHomeAway();
        MatchDto.MatchTeam
                home = m.getHomeTeam(), away = m.getAwayTeam();
        Referee r = new Referee();
        String winner = null, duration = null;
        if (s != null) {
            f = s.getFullTime();
            h = s.getHalfTime();
            e = s.getExtraTime();
            p = s.getPenalties();
            winner = s.getWinner();
            duration = s.getDuration();
        }
        if (m.getReferees().size() > 0)
            r = m.getReferees().get(0);
        jdbcTemplate.update(UPDATE_QUERY,
                m.getUtcDate(), m.getStatus(), m.getVenue(), m.getMatchday(), m.getStage(),
                m.getGroup(), m.getLastUpdated(), m.getNumberOfMatches(), m.getTotalGoals(), home.getId(), home.getName(),
                home.getWins(), home.getDraws(), home.getLosses(), away.getId(), away.getName(), away.getWins(),
                away.getDraws(), away.getLosses(), winner, duration, f.getHomeTeam(), f.getAwayTeam(),
                h.getHomeTeam(), h.getAwayTeam(), e.getHomeTeam(), e.getAwayTeam(), p.getHomeTeam(), p.getAwayTeam(),
                r.getId(), r.getName(), m.getCompetition().getId(), m.getSeason().getId(), new Timestamp(System.currentTimeMillis()), m.getId()
        );
    }

}
