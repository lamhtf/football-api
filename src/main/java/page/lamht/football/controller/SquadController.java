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
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.dto.LeagueDto;
import page.lamht.football.dto.PersonDto;
import page.lamht.football.dto.TeamDto;
import page.lamht.football.entity.CompetitionTeam;
import page.lamht.football.entity.Player;
import page.lamht.football.entity.Team;
import page.lamht.football.repository.CompetitionTeamService;
import page.lamht.football.repository.PlayerService;
import page.lamht.football.repository.TeamService;
import page.lamht.football.util.TokenSelector;
import page.lamht.football.util.Utils;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

import static page.lamht.football.util.Constants.X_AUTH_TOKEN;

@RestController
class SquadController {

    private final static Logger logger = LoggerFactory.getLogger(SquadController.class);

    @Autowired
    private PlayerService service;

    //    @GetMapping("/squad/{teamId}")
    String getPlayers(@PathVariable Long teamId) {
        try {
            logger.debug("start time: " + new Timestamp(System.currentTimeMillis()));

            String url = Utils.selectSquadApi(teamId);

            if (StringUtils.isEmpty(url)) return "";

            WebClient webClient = WebClient.create();
            Mono<TeamDto> teamDtoMono = webClient.get()
                    .uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(X_AUTH_TOKEN, TokenSelector.getToken())
                    .retrieve()
                    .bodyToMono(TeamDto.class);

            TeamDto teamDto = teamDtoMono.block();

            service.deleteAll(teamId);
            for (PersonDto p : teamDto.getSquad()) {
                service.save(teamId, p);
            }

            logger.debug("end time: " + new Timestamp(System.currentTimeMillis()));
        } catch (Exception e) {
            logger.info(e.toString());
            return "Fail";
        }
        return "Completed Successfully";
    }

}
