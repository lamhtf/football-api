package page.lamht.football.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.dto.CompetitionsDto;
import page.lamht.football.dto.MatchDto;
import page.lamht.football.dto.MatchesDto;
import page.lamht.football.entity.Competition;
import page.lamht.football.repository.CompetitionService;
import page.lamht.football.repository.MatchService;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

@RestController
class MatchController {

    Logger logger = LoggerFactory.getLogger(MatchController.class);

    @Autowired
    private MatchService service;

    @GetMapping("/matches")
    String getCompetitions() {
        logger.info("start time: " + new Timestamp(System.currentTimeMillis()));

        WebClient webClient = WebClient.create();

        Mono<MatchesDto> mono = webClient.get()
                .uri("http://localhost:8080/matchesTest/")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(MatchesDto.class);

        MatchesDto dto = mono.block();

        for (MatchDto matchDto : dto.getMatches()) {
            service.save(matchDto);
        }

        logger.info("end time: " + new Timestamp(System.currentTimeMillis()));

        return "Completed Successfully";
    }

}
