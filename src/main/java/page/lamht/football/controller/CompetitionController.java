package page.lamht.football.controller;

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
import page.lamht.football.dto.CompetitionsDto;
import page.lamht.football.entity.Competition;
import page.lamht.football.repository.CompetitionService;
import page.lamht.football.util.Utils;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

@RestController
class CompetitionController {

    Logger logger = LoggerFactory.getLogger(CompetitionController.class);

    @Value("${xauth.token}")
    private String token;

    @Autowired
    private CompetitionService service;

    @GetMapping("/competitions}")
    String getCompetitions() {
        logger.debug("start time: " + new Timestamp(System.currentTimeMillis()));

        String url = Utils.selectCompetitionApi();

        WebClient webClient = WebClient.create();

        Mono<CompetitionsDto> mono = webClient.get()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(CompetitionsDto.class);

        CompetitionsDto dto = mono.block();

        for (Competition competition : dto.getCompetitions()) {
            service.save(competition);
        }

        logger.debug("end time: " + new Timestamp(System.currentTimeMillis()));

        return "Completed Successfully";
    }

}
