package page.lamht.football.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.dto.CompetitionDto;
import page.lamht.football.entity.Competition;
import page.lamht.football.repository.CompetitionRepository;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

@RestController
class CompetitionController {

  Logger logger = LoggerFactory.getLogger(CompetitionController.class);

  @Autowired
  private CompetitionRepository repository;

  @GetMapping("/competitions")
  String getCompetitions(){
    logger.info("start time: " + new Timestamp(System.currentTimeMillis()));

    WebClient webClient = WebClient.create();

    Mono<CompetitionDto> mono = webClient.get()
            .uri("http://localhost:8080/competitionTest/")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .retrieve()
            .bodyToMono(CompetitionDto.class);

      CompetitionDto dto = mono.block();

    for (Competition competition: dto.getCompetitions()){
        repository.findById(competition.getId()).ifPresentOrElse(
          c-> repository.save(competition),
          ()-> {
            competition.setNew(true);
              repository.save(competition);
          }
        );
    };

    logger.info("end time: " + new Timestamp(System.currentTimeMillis()));

    return "Get Competitions Success";
  }

}