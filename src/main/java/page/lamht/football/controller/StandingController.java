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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.dto.StandingDto;
import page.lamht.football.dto.StandingsDto;
import page.lamht.football.entity.CompetitionTeam;
import page.lamht.football.entity.Standings;
import page.lamht.football.entity.Team;
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

    Logger logger = LoggerFactory.getLogger(StandingController.class);

    @Autowired
    private StandingService service;

    @Autowired
    private CompetitionTeamService ctService;

    ObjectMapper objectMapper = new ObjectMapper();

    String getStandingTables(@PathVariable String league) {
        logger.debug("start time: " + new Timestamp(System.currentTimeMillis()));

        String url = Utils.selectStandingsApi(league);

        WebClient webClient = WebClient.create();
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
    String getStandings(@PathVariable String league) throws JsonProcessingException {
        Long leagueId = Utils.selectLeagueId(league);
        if (leagueId == null) return "";
        List<Standings> standingsList = service.findAllByCompetitionId(leagueId);

        String result = objectMapper.writeValueAsString(standingsList);
        return result;
    }

}
