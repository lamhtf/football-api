package page.lamht.football.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.dto.ScorerDto;
import page.lamht.football.entity.Player;
import page.lamht.football.entity.Scorer;
import page.lamht.football.entity.Team;
import page.lamht.football.mapper.StatisticsMapper;
import page.lamht.football.mo.ScorerMo;
import page.lamht.football.mo.StatisticsResponse;
import page.lamht.football.repository.CompetitionService;
import page.lamht.football.repository.PlayerService;
import page.lamht.football.repository.StatisticsService;
import page.lamht.football.repository.TeamService;
import page.lamht.football.util.TokenSelector;
import page.lamht.football.util.Utils;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static page.lamht.football.util.Constants.X_AUTH_TOKEN;

@RestController
class StatisticController {

    private final static Logger logger = LoggerFactory.getLogger(StatisticController.class);

    @Value("${limit}")
    Integer limit;

    @Value("${leagueLimit}")
    Integer leagueLimit;

    @Autowired
    private TeamService teamService;
    @Autowired
    private StatisticsService service;
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private PlayerService playerService;

    WebClient webClient = WebClient.create();

    ObjectMapper objectMapper = new ObjectMapper();

    String runScorers(@PathVariable String league) {
        try {
            Timestamp lastUpdated = new Timestamp(System.currentTimeMillis());
            logger.debug("start time: " + lastUpdated);

            Long leagueId = Utils.selectLeagueId(league);

            if (competitionService.hasUpdated(leagueId, lastUpdated)) {

                String url = Utils.selectScorerApi(league);
                url = url + "?limit=" + limit;

                Mono<ScorerDto> mono = webClient.get()
                        .uri(url)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(X_AUTH_TOKEN, TokenSelector.getToken())
                        .retrieve()
                        .bodyToMono(ScorerDto.class);

                ScorerDto scorerDto = mono.block();
                service.saveScorers(scorerDto.getCompetition(), scorerDto.getSeason(), scorerDto.getScorers());
            }

            logger.debug("end time: " + new Timestamp(System.currentTimeMillis()));
        } catch (Exception e) {
            logger.info(e.toString());
            return "Fail";
        }
        return "Completed Successfully";
    }

    String initScorers(@PathVariable String league) {
        try {
            logger.debug("start time: " + new Timestamp(System.currentTimeMillis()));

            String url = Utils.selectScorerApi(league);
            url = url + "?limit=" + limit;

            Mono<ScorerDto> mono = webClient.get()
                    .uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(X_AUTH_TOKEN, TokenSelector.getToken())
                    .retrieve()
                    .bodyToMono(ScorerDto.class);

            ScorerDto scorerDto = mono.block();
            service.saveScorers(scorerDto.getCompetition(), scorerDto.getSeason(), scorerDto.getScorers());

            logger.debug("end time: " + new Timestamp(System.currentTimeMillis()));
        } catch (Exception e) {
            logger.info(e.toString());
            return "Fail";
        }
        return "Completed Successfully";
    }

    @GetMapping(value = "/scorers/{league}", produces = MediaType.APPLICATION_JSON_VALUE)
    String getLeagueScorers(@PathVariable String league, @RequestParam Long lastUpdated) throws JsonProcessingException {
        Long leagueId = Utils.selectLeagueId(league);
        if (leagueId == null) return "";
        Timestamp callTime = new Timestamp(System.currentTimeMillis());
        List<Scorer> scorers = new ArrayList<>();
        if (competitionService.hasUpdated(leagueId, new Timestamp(lastUpdated))) {
            scorers = service.findScorerByLeagueId(leagueId, leagueLimit);
            for (Scorer s : scorers) {
                Team t = teamService.findById(s.getTeamId());
                Player p = playerService.findPlayerById(s.getId());
                s.setTeam(t);
                s.setPlayer(p);
            }
        }

        List<ScorerMo> scorerMos = StatisticsMapper.INSTANCE.scorersToScorerMos(scorers);

        StatisticsResponse response = new StatisticsResponse(scorerMos, callTime);

        String result = objectMapper.writeValueAsString(response);
        return result;
    }

    @GetMapping(value = "/scorers/{league}/{teamId}", produces = MediaType.APPLICATION_JSON_VALUE)
    String getTeamScorers(@PathVariable String league, @PathVariable Long teamId, @RequestParam Long lastUpdated) throws JsonProcessingException {
        Long leagueId = Utils.selectLeagueId(league);
        if (leagueId == null) return "";
        Timestamp callTime = new Timestamp(System.currentTimeMillis());
        List<Scorer> scorers = new ArrayList<>();
        if (competitionService.hasUpdated(leagueId, new Timestamp(lastUpdated))) {
            scorers = service.findScorerByLeagueAndTeamId(leagueId, teamId);
            Team t = teamService.findById(teamId);
            for (Scorer s : scorers) {
                Player p = playerService.findPlayerById(s.getId());
                s.setTeam(t);
                s.setPlayer(p);
            }
        }

        List<ScorerMo> scorerMos = StatisticsMapper.INSTANCE.scorersToScorerMos(scorers);

        StatisticsResponse response = new StatisticsResponse(scorerMos, callTime);

        String result = objectMapper.writeValueAsString(response);
        return result;
    }

}
