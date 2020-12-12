package page.lamht.football.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.dto.StandingDto;
import page.lamht.football.entity.Standings;
import page.lamht.football.mapper.StandingsMapper;
import page.lamht.football.mo.StandingsMo;
import page.lamht.football.mo.StandingsResponse;
import page.lamht.football.repository.CompetitionService;
import page.lamht.football.repository.CompetitionTeamService;
import page.lamht.football.repository.StandingService;
import page.lamht.football.util.TokenSelector;
import page.lamht.football.util.Utils;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.List;

import static page.lamht.football.util.Constants.X_AUTH_TOKEN;

@RestController
class StandingController {

    private static final Logger logger = LoggerFactory.getLogger(StandingController.class);

    private static final WebClient webClient = WebClient.create();

    @Autowired
    private StandingService service;

    @Autowired
    private CompetitionService cService;

    @Autowired
    private CompetitionTeamService ctService;

    ObjectMapper objectMapper = new ObjectMapper();

    String initStandingTables(@PathVariable String league) {
        logger.debug("start time: " + new Timestamp(System.currentTimeMillis()));

        String url = Utils.selectStandingsApi(league);

        Mono<StandingDto> standingDtoMono = webClient.get()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(X_AUTH_TOKEN, TokenSelector.getToken())
                .retrieve()
                .bodyToMono(StandingDto.class);

        StandingDto standingDto = standingDtoMono.block();
        service.init(standingDto);

        logger.debug("end time: " + new Timestamp(System.currentTimeMillis()));

        return "Completed Successfully";
    }

    String getStandingTables(@PathVariable String league) {
        logger.debug("start time: " + new Timestamp(System.currentTimeMillis()));

        String url = Utils.selectStandingsApi(league);

        Mono<StandingDto> standingDtoMono = webClient.get()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(X_AUTH_TOKEN, TokenSelector.getToken())
                .retrieve()
                .bodyToMono(StandingDto.class);

        StandingDto standingDto = standingDtoMono.block();
        service.save(standingDto);
//        List<StandingsDto> dto = standingDto.getStandings();

        logger.debug("end time: " + new Timestamp(System.currentTimeMillis()));

        return "Completed Successfully";
    }

    @GetMapping(value="/standings/{league}", produces=MediaType.APPLICATION_JSON_VALUE)
    String getStandings(@PathVariable String league, @RequestParam Long lastUpdated) throws JsonProcessingException {
        Long leagueId = Utils.selectLeagueId(league);
        if (leagueId == null) return "";
        Timestamp callTime = new Timestamp(System.currentTimeMillis());
        List<Standings> standingsList = service.findAllByCompetitionId(leagueId, new Timestamp(lastUpdated));
        List<StandingsMo> standingsMo = StandingsMapper.INSTANCE.standingssToStandingsMos(standingsList);

        StandingsResponse response = new StandingsResponse(null, null, standingsMo, callTime);

        String result = objectMapper.writeValueAsString(response);
        return result;
    }

}
