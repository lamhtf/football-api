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
import page.lamht.football.mo.StandingsMo;
import page.lamht.football.mo.StandingsResponse;
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
    String getFavourites(@PathVariable String league) throws JsonProcessingException {

//        select c.id as competition_id, c.name as competition_name, t.id as team_id, t.short_name, crest_url, club_colors from public.competition c, public.competition_team ct, team t where c.id = ct.competition_id and ct.team_id = t.id and c.id in (2021, 2019, 2002, 2014, 2017, 2015, 2003)
        Long leagueId = Utils.selectLeagueId(league);
        if (leagueId == null) return "";
        List<Standings> standingsList = service.findAllByCompetitionId(leagueId);
        List<StandingsMo> standingsMo = StandingsMapper.INSTANCE.standingssToStandingsMos(standingsList);

        StandingsResponse response = new StandingsResponse(null, null, standingsMo);

        String result = objectMapper.writeValueAsString(response);
        return result;
    }

}
