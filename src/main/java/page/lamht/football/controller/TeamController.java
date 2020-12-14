package page.lamht.football.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.dto.LeagueDto;
import page.lamht.football.entity.CompetitionTeam;
import page.lamht.football.entity.Match;
import page.lamht.football.entity.Team;
import page.lamht.football.mapper.NextMatchMapper;
import page.lamht.football.mapper.TeamMapper;
import page.lamht.football.mo.NextMatchMo;
import page.lamht.football.mo.NextMatchesResponse;
import page.lamht.football.mo.TeamMo;
import page.lamht.football.mo.TeamResponse;
import page.lamht.football.repository.CompetitionTeamService;
import page.lamht.football.repository.TeamService;
import page.lamht.football.util.TokenSelector;
import page.lamht.football.util.Utils;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.List;

import static page.lamht.football.util.Constants.X_AUTH_TOKEN;

@RestController
class TeamController {

    private final static Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService service;

    @Autowired
    private CompetitionTeamService ctService;

    ObjectMapper objectMapper = new ObjectMapper();

    String getTeams(@PathVariable String league) {
        try {
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
        } catch (Exception e) {
            logger.info(e.toString());
            return "Fail";
        }
        return "Completed Successfully";
    }


    //    @GetMapping("/teams/{teamId}")
    @GetMapping(value = "/team/{teamId}", produces = MediaType.APPLICATION_JSON_VALUE)
    String getTeamDetail(@PathVariable Long teamId, @RequestParam Long lastUpdated) throws JsonProcessingException {
        Timestamp callTime = new Timestamp(System.currentTimeMillis());

        Team t = service.findTeamDetailById(teamId);

        TeamMo mo = TeamMapper.INSTANCE.teamToTeamMo(t);

        TeamResponse response = new TeamResponse(mo, callTime);

        String result = objectMapper.writeValueAsString(response);
        return result;
    }


}
