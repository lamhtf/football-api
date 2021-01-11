package page.lamht.football.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.entity.Match;
import page.lamht.football.entity.Standing;
import page.lamht.football.mapper.LastMatchMapper;
import page.lamht.football.mapper.NextMatchMapper;
import page.lamht.football.mapper.StandingsMapper;
import page.lamht.football.mo.HomeResponse;
import page.lamht.football.mo.LastMatchMo;
import page.lamht.football.mo.NextMatchMo;
import page.lamht.football.mo.StandingsMo;
import page.lamht.football.repository.MatchService;
import page.lamht.football.repository.StandingService;
import page.lamht.football.util.Utils;

import java.sql.Timestamp;

@RestController
class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private static final WebClient webClient = WebClient.create();

    @Autowired
    private StandingService service;

    @Autowired
    private MatchService mService;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value = "/home/{league}/{teamId}", produces = MediaType.APPLICATION_JSON_VALUE)
    String getStandings(@PathVariable String league, @PathVariable Long teamId, @RequestParam Long lastUpdated) throws JsonProcessingException {
        Long leagueId = Utils.selectLeagueId(league);
        if (leagueId == null) return "";
        Timestamp callTime = new Timestamp(System.currentTimeMillis());
        Standing standing = service.findStandingByLeagueIdAndTeamId(leagueId, teamId);
        StandingsMo.StandingMo standingMo = StandingsMapper.INSTANCE.standingToStandingMo(standing);

        Match lastMatch = mService.findLastMatchByCompetitionIdAndTeamId(leagueId, teamId);
        Match nextMatch = mService.findNextMatchByCompetitionIdAndTeamId(leagueId, teamId);

        LastMatchMo lastMatchMo = null;
        NextMatchMo nextMatchMo = null;
        if (lastMatch != null) {
            lastMatchMo = LastMatchMapper.INSTANCE.matchToLastMatchMo(lastMatch);
        }
        if (nextMatch != null) {
            nextMatchMo = NextMatchMapper.INSTANCE.matchToNextMatchMo(nextMatch);
        }
        HomeResponse response = new HomeResponse(standingMo, lastMatchMo, nextMatchMo, callTime);
        String result = objectMapper.writeValueAsString(response);
        return result;
    }

}
