package page.lamht.football.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.dto.CompetitionsDto;
import page.lamht.football.dto.MatchDto;
import page.lamht.football.dto.MatchesDto;
import page.lamht.football.entity.Competition;
import page.lamht.football.repository.CompetitionService;
import page.lamht.football.repository.MatchService;
import page.lamht.football.util.TokenSelector;
import page.lamht.football.util.Utils;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

import static page.lamht.football.util.Constants.*;

@RestController
class MatchController {

    Logger logger = LoggerFactory.getLogger(MatchController.class);

    @Autowired
    private MatchService service;

//    @GetMapping("/matches/{league}")
    String getFixtures(@PathVariable String league) {
        logger.debug("start time: " + new Timestamp(System.currentTimeMillis()));

        String url = Utils.selectMatchApi(league);

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 10)).build();
        WebClient webClient = WebClient.builder().exchangeStrategies(exchangeStrategies).build();

        Mono<MatchesDto> mono = webClient.get()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(X_AUTH_TOKEN, TokenSelector.getToken())
                .retrieve()
                .bodyToMono(MatchesDto.class);

        MatchesDto dto = mono.block();

        for (MatchDto matchDto : dto.getMatches()) {
            matchDto.setCompetition(dto.getCompetition());
            service.save(matchDto);
        }

        logger.debug("end time: " + new Timestamp(System.currentTimeMillis()));

        return "Completed Successfully";
    }

}
