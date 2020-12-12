package page.lamht.football.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import page.lamht.football.mo.*;
import page.lamht.football.repository.CompetitionTeamService;
import page.lamht.football.repository.StandingService;

import java.sql.Timestamp;
import java.util.List;

@RestController
class FavouriteController {

    private final static Logger logger = LoggerFactory.getLogger(FavouriteController.class);

    @Autowired
    private StandingService service;

    @Autowired
    private CompetitionTeamService ctService;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value="/favourites", produces=MediaType.APPLICATION_JSON_VALUE)
    String getFavourites() throws JsonProcessingException {
        Timestamp callTime = new Timestamp(System.currentTimeMillis());

        List<FavouriteTeamMo> teamList = ctService.findFavourites();
        FavouriteResponse response = new FavouriteResponse(teamList, callTime);
        String result = objectMapper.writeValueAsString(response);
        return result;
    }

}
