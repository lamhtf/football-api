package page.lamht.football.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.dto.CompetitionsDto;
import page.lamht.football.entity.Competition;
import page.lamht.football.mapper.CompetitionMapper;
import page.lamht.football.mo.CompetitionMo;
import page.lamht.football.mo.CompetitionResponse;
import page.lamht.football.repository.CompetitionService;
import page.lamht.football.util.TokenSelector;
import page.lamht.football.util.Utils;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.List;

import static page.lamht.football.util.Constants.X_AUTH_TOKEN;

@RestController
class CompetitionController {

    private final static Logger logger = LoggerFactory.getLogger(CompetitionController.class);

    @Autowired
    private CompetitionService service;

    ObjectMapper objectMapper = new ObjectMapper();

    String getCompetitions() {
        logger.debug("start time: " + new Timestamp(System.currentTimeMillis()));

        String url = Utils.selectCompetitionApi();

        WebClient webClient = WebClient.create();

        Mono<CompetitionsDto> mono = webClient.get()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(X_AUTH_TOKEN, TokenSelector.getToken())
                .retrieve()
                .bodyToMono(CompetitionsDto.class);

        CompetitionsDto dto = mono.block();

        for (Competition competition : dto.getCompetitions()) {
            service.save(competition);
        }

        logger.debug("end time: " + new Timestamp(System.currentTimeMillis()));

        return "Completed Successfully";
    }

    @GetMapping(value="/competitions", produces=MediaType.APPLICATION_JSON_VALUE)
    String getAllCompetitions(@RequestParam Long lastUpdated) throws JsonProcessingException {
        Timestamp callTime = new Timestamp(System.currentTimeMillis());
        List<Competition> competitionList = service.findAll(new Timestamp(lastUpdated));
        List<CompetitionMo> competitionMos = CompetitionMapper.INSTANCE.competitionsToCompetitionMos(competitionList);

        CompetitionResponse response = new CompetitionResponse(competitionMos, callTime);

        String result = objectMapper.writeValueAsString(response);
        return result;
    }

}
