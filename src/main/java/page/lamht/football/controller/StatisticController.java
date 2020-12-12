package page.lamht.football.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.dto.LeagueDto;
import page.lamht.football.entity.CompetitionTeam;
import page.lamht.football.entity.Team;
import page.lamht.football.repository.CompetitionTeamService;
import page.lamht.football.repository.TeamService;
import page.lamht.football.util.TokenSelector;
import page.lamht.football.util.Utils;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

import static page.lamht.football.util.Constants.X_AUTH_TOKEN;

@RestController
class StatisticController {

    private final static Logger logger = LoggerFactory.getLogger(StatisticController.class);

    @Autowired
    private TeamService service;

    @Autowired
    private CompetitionTeamService ctService;


//    @GetMapping("/teams/{league}")
    String getScorers(@PathVariable String league) {
        logger.debug("start time: " + new Timestamp(System.currentTimeMillis()));

        String url = Utils.selectTeamApi(league);

        WebClient webClient = WebClient.create();
        Mono<LeagueDto> leagueDtoMono = webClient.get()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(X_AUTH_TOKEN, TokenSelector.getToken())
                .retrieve()
                .bodyToMono(LeagueDto.class);

        LeagueDto leagueDto = leagueDtoMono.block();

        Long competitionId = leagueDto.getCompetition().getId();
        CompetitionTeam ct = ctService.newInstance(competitionId, null);

        ctService.deleteAll(ct);
        for (Team team : leagueDto.getTeams()) {
            service.save(team);
            CompetitionTeam newCT = ctService.newInstance(competitionId, team.getId());
            ctService.save(newCT);
        }

        logger.debug("end time: " + new Timestamp(System.currentTimeMillis()));

        return "Completed Successfully";
    }

}
