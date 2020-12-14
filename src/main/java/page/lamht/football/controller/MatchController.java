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
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.dto.MatchDto;
import page.lamht.football.dto.MatchesDto;
import page.lamht.football.entity.Match;
import page.lamht.football.mapper.LastMatchMapper;
import page.lamht.football.mapper.MatchMapper;
import page.lamht.football.mapper.NextMatchMapper;
import page.lamht.football.mo.*;
import page.lamht.football.repository.MatchService;
import page.lamht.football.util.TokenSelector;
import page.lamht.football.util.Utils;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.List;

import static page.lamht.football.util.Constants.X_AUTH_TOKEN;

@RestController
class MatchController {

    private static final Logger logger = LoggerFactory.getLogger(MatchController.class);

    private static final ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 10)).build();

    private static final WebClient webClient = WebClient.builder().exchangeStrategies(exchangeStrategies).build();

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MatchService service;

    String initFixtures(String league) {
        try {
            logger.debug("start time: " + new Timestamp(System.currentTimeMillis()));

            String url = Utils.selectMatchApi(league);

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
        } catch (Exception e) {
            logger.info(e.toString());
            return "Fail";
        }
        return "Completed Successfully";
    }

    String getFixtures(@PathVariable String league) {
        logger.debug("start time: " + new Timestamp(System.currentTimeMillis()));

        String url = Utils.selectMatchApi(league);
        url = url + "?dateFrom=" + Utils.getYesterday() + "&dateTo=" + Utils.getToday();

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

    @GetMapping(value = "/matches/{league}", produces = MediaType.APPLICATION_JSON_VALUE)
    String getMatches(@PathVariable String league, @RequestParam Long lastUpdated) throws JsonProcessingException {
        Long leagueId = Utils.selectLeagueId(league);
        if (leagueId == null) return "";
        Timestamp callTime = new Timestamp(System.currentTimeMillis());
        List<Match> matchList = service.findByCompetitionId(leagueId, new Timestamp(lastUpdated));
        List<MatchMo> matchMos = MatchMapper.INSTANCE.matchsToMatchMos(matchList);

        MatchResponse response = new MatchResponse(null, matchMos, callTime);

        String result = objectMapper.writeValueAsString(response);
        return result;
    }

    @GetMapping(value = "/matches/last/{league}", produces = MediaType.APPLICATION_JSON_VALUE)
    String getLastMatches(@PathVariable String league, @RequestParam Long lastUpdated) throws JsonProcessingException {
        Long leagueId = Utils.selectLeagueId(league);
        if (leagueId == null) return "";
        Timestamp callTime = new Timestamp(System.currentTimeMillis());

        List<Match> matchList = service.findLastMatches(leagueId, 5);
        List<LastMatchMo> matchMos = LastMatchMapper.INSTANCE.matchsToLastMatchMos(matchList);

        LastMatchesResponse response = new LastMatchesResponse(matchMos, callTime);

        String result = objectMapper.writeValueAsString(response);
        return result;
    }

    @GetMapping(value = "/matches/next/{league}", produces = MediaType.APPLICATION_JSON_VALUE)
    String getNextMatches(@PathVariable String league, @RequestParam Long lastUpdated) throws JsonProcessingException {
        Long leagueId = Utils.selectLeagueId(league);
        if (leagueId == null) return "";
        Timestamp callTime = new Timestamp(System.currentTimeMillis());

        List<Match> matchList = service.findNextMatches(leagueId, 5);
        List<NextMatchMo> matchMos = NextMatchMapper.INSTANCE.matchsToNextMatchMos(matchList);

        NextMatchesResponse response = new NextMatchesResponse(matchMos, callTime);

        String result = objectMapper.writeValueAsString(response);
        return result;
    }
}
