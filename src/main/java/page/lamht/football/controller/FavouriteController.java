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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.dto.StandingDto;
import page.lamht.football.entity.Standings;
import page.lamht.football.mapper.StandingsMapper;
import page.lamht.football.mo.*;
import page.lamht.football.repository.CompetitionTeamService;
import page.lamht.football.repository.StandingService;
import page.lamht.football.util.TokenSelector;
import page.lamht.football.util.Utils;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.List;

import static page.lamht.football.util.Constants.X_AUTH_TOKEN;

@RestController
class FavouriteController {

    Logger logger = LoggerFactory.getLogger(FavouriteController.class);

    @Autowired
    private StandingService service;

    @Autowired
    private CompetitionTeamService ctService;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value="/favourites", produces=MediaType.APPLICATION_JSON_VALUE)
    String getFavourites() throws JsonProcessingException {

        List<FavouriteMo> moList = ctService.findFavourites();
        FavouriteResponse response = new FavouriteResponse(moList);
        String result = objectMapper.writeValueAsString(response);
        return result;
    }

}
