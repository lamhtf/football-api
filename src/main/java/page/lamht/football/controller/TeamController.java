package page.lamht.football.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.dto.AreasDto;
import page.lamht.football.dto.TeamDto;
import page.lamht.football.entity.Area;
import page.lamht.football.entity.Team;
import page.lamht.football.repository.AreaRepository;
import page.lamht.football.repository.MatchService;
import page.lamht.football.repository.TeamService;
import page.lamht.football.util.Utils;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

import static page.lamht.football.util.Constants.X_AUTH_TOKEN;

@RestController
class TeamController {

    Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService service;

    @GetMapping("/teams/{league}/{token}")
    String getTeams(@PathVariable String league, @PathVariable String token) {
        if (StringUtils.isEmpty(token)) return null;
        logger.debug("start time: " + new Timestamp(System.currentTimeMillis()));

        String url = Utils.selectTeamApi(league);

        WebClient webClient = WebClient.create();
        Mono<TeamDto> teamDtoMono = webClient.get()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(X_AUTH_TOKEN, token)
                .retrieve()
                .bodyToMono(TeamDto.class);

        TeamDto teamDto = teamDtoMono.block();
        for (Team team : teamDto.getTeams()) {
            service.save(team);
        }

        logger.debug("end time: " + new Timestamp(System.currentTimeMillis()));

        return "Completed Successfully";
    }

}
