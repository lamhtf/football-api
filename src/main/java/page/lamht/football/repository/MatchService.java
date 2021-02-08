package page.lamht.football.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.dto.MatchDto;
import page.lamht.football.entity.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MatchService {

    private final static Logger logger = LoggerFactory.getLogger(MatchService.class);

    private final static String INSERT_QUERY = "INSERT INTO public.match VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final static String UPDATE_QUERY = "UPDATE public.\"match\" SET utc_date=?, status=?, venue=?, matchday=?, stage=?, \"group\"=?, last_updated=?, number_of_matches=?, total_goals=?, home_team_id=?, home_team_name=?, home_team_wins=?, home_team_draws=?, home_team_losses=?, away_team_id=?, away_team_name=?, away_team_wins=?, away_team_draws=?, away_team_losses=?, winner=?, duration=?, full_time_home_team=?, full_time_away_team=?, half_time_home_team=?, half_time_away_team=?, extra_time_home_team=?, extra_time_away_team=?, penalties_home_team=?, penalties_away_team=?, referee_id=?, referee_name=?, competition_id=?, season_id=?, created=? WHERE id=? and last_updated<?";
    private final static String LIST_MATCHES = "SELECT m.*, h.short_name as home_team_name, a.short_name as away_team_name FROM public.\"match\" m, public.\"team\" h, public.\"team\" a where m.home_team_id = h.id and m.away_team_id = a.id and competition_id=? and m.last_updated>?";

    private final static String LIST_LAST_MATCHES = "select m.id, m.matchday, m.utc_date, m.home_team_id, home.short_name as home_team_name, m.away_team_id, away.short_name as away_team_name, m.winner, m.full_time_home_team, m.full_time_away_team, home.crest_url as home_team_crest_url, away.crest_url as away_team_crest_url from public.\"match\" m, public.\"team\" home, public.\"team\" away where home.id=m.home_team_id and away.id=m.away_team_id and (m.status='FINISHED' or m.status='AWARDED') and m.competition_id=? and (m.home_team_id=? or m.away_team_id=?) order by m.utc_date desc limit ?";
    private final static String LIST_IN_PLAY_MATCHES = "select m.id, m.matchday, m.utc_date, m.home_team_id, home.short_name as home_team_name, m.away_team_id, away.short_name as away_team_name, m.winner, m.full_time_home_team, m.full_time_away_team,  m.half_time_home_team, m.half_time_away_team, home.crest_url as home_team_crest_url, away.crest_url as away_team_crest_url from public.\"match\" m, public.\"team\" home, public.\"team\" away where home.id=m.home_team_id and away.id=m.away_team_id and m.status='IN_PLAY' and m.competition_id=? and (m.home_team_id=? or m.away_team_id=?) order by m.utc_date desc limit ?";
    private final static String LIST_NEXT_MATCHES = "select m.id, m.matchday, m.utc_date, m.home_team_id, home.short_name as home_team_name, m.away_team_id, away.short_name as away_team_name, home.crest_url as home_team_crest_url, away.crest_url as away_team_crest_url from public.\"match\" m, public.\"team\" home, public.\"team\" away where home.id=m.home_team_id and away.id=m.away_team_id and m.status='SCHEDULED' and m.competition_id=? and (m.home_team_id=? or m.away_team_id=?) order by m.utc_date asc limit ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private TeamService teamService;

    public List<Match> findLastMatches(Long competitionId, Integer limit) {

        List<Match> matches = new ArrayList<>();
        List<Team> teams = teamService.findByCompetitionId(competitionId);
        for (Team t : teams) {
            List<Match> ms = jdbcTemplate.query(LIST_LAST_MATCHES, new Object[]{competitionId, t.getId(), t.getId(), limit}, new BeanPropertyRowMapper<Match>(Match.class));
            matches.addAll(ms);
        }
        return matches;
    }

    public List<Match> findNextMatches(Long competitionId, Integer limit) {
        List<Match> matches = new ArrayList<>();
        List<Team> teams = teamService.findByCompetitionId(competitionId);
        for (Team t : teams) {
            List<Match> ms = jdbcTemplate.query(LIST_NEXT_MATCHES, new Object[]{competitionId, t.getId(), t.getId(), limit}, new BeanPropertyRowMapper<Match>(Match.class));
            matches.addAll(ms);
        }
        return matches;
    }

    public Match findNextMatchByCompetitionIdAndTeamId(Long competitionId, Long teamId) {
        try {
            return jdbcTemplate.queryForObject(LIST_NEXT_MATCHES, new Object[]{competitionId, teamId, teamId, 1}, new BeanPropertyRowMapper<Match>(Match.class));
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public Match findInPlayMatchByCompetitionIdAndTeamId(Long competitionId, Long teamId) {
        try {
            return jdbcTemplate.queryForObject(LIST_IN_PLAY_MATCHES, new Object[]{competitionId, teamId, teamId, 1}, new BeanPropertyRowMapper<Match>(Match.class));
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public Match findLastMatchByCompetitionIdAndTeamId(Long competitionId, Long teamId) {
        try {
            return jdbcTemplate.queryForObject(LIST_LAST_MATCHES, new Object[]{competitionId, teamId, teamId, 1}, new BeanPropertyRowMapper<Match>(Match.class));
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public Match findById(Long id) {
        String sql = "SELECT * FROM match m WHERE m.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<Match>(Match.class));
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public List<Match> findByCompetitionId(Long competitionId, Timestamp lastUpdated) {
        try {
            return jdbcTemplate.query(LIST_MATCHES, new Object[]{competitionId, lastUpdated}, new BeanPropertyRowMapper<Match>(Match.class));
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public Integer countById(Long id) {
        String sql = "SELECT count(*) FROM match m WHERE m.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
    }

    public Integer countTotalWeeksByCompetitionId(Long competitionId) {
        try {
            String sql = "select max(matchday) from public.\"match\" where competition_id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{competitionId}, Integer.class);
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public Integer getCurrentMatchdayByCompetitionId(Long competitionId) {
        try {
            String sql = "select current_matchday from public.season s, public.competition c where c.current_season_id = s.id and c.id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{competitionId}, Integer.class);
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public Match save(MatchDto dto) {

//        Competition c = dto.getCompetition();
//        Competition dbCompetition = competitionService.findById(c.getId());
//        if (dbCompetition == null)
//            competitionService.save(c);
//
//        Season s = dto.getSeason();
//        Season dbSeason = seasonService.findById(s.getId());
//        if (dbCompetition == null)
//            seasonService.save(s);

        insertOrUpdate(dto);
        return this.findById(dto.getId());
    }

    private void insertOrUpdate(MatchDto m) {
        if (countById(m.getId()) == 0)
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
        if (c != null) cId = c.getId();
        Season se = m.getSeason();
        if (se != null) sId = se.getId();

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
                r.getId(), r.getName(), m.getCompetition().getId(), m.getSeason().getId(), new Timestamp(System.currentTimeMillis()), m.getId(),
                m.getLastUpdated()
        );
    }

}
