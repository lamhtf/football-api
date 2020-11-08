package page.lamht.football.controller;

import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import page.lamht.football.entity.Team;
import page.lamht.football.repository.TeamService;
import page.lamht.football.util.Constants;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class PostStartUpRunner implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(PostStartUpRunner.class);

    @Autowired
    AreaController areaController;
    @Autowired
    CompetitionController competitionController;
    @Autowired
    TeamController teamController;
    @Autowired
    MatchController matchController;
    @Autowired
    StandingController standingController;
    @Autowired
    SquadController squadController;
    @Autowired
    TeamService teamService;

    @Override
    public void run(String... args) throws Exception {
        //Do something here
        logger.info("Initialize those data for areas");
        areaController.getAreas();
        logger.info("Initialize those data for competitions");
        competitionController.getCompetitions();
        logger.info("Initialize those data for teams");
        teamController.getTeams(Constants.ENGLISH_PREMIER_LEAGUE);
        teamController.getTeams(Constants.ITALIAN_SERIE_A);
        teamController.getTeams(Constants.GERMAN_BUNDESLIGA);
        teamController.getTeams(Constants.SPAINISH_LA_LIGA);
        teamController.getTeams(Constants.PORTUGUESE_PRIMEIRA_LIGA);
        teamController.getTeams(Constants.FRENCH_LIGUE_1);
        teamController.getTeams(Constants.DUTCH_EREDIVISIE);
        teamController.getTeams(Constants.UEFA_CHAMPION_LEAGUE);

        logger.info("Initialize those data for matches (fixtures)");
        matchController.getCompetitions(Constants.ENGLISH_PREMIER_LEAGUE);
        matchController.getCompetitions(Constants.ITALIAN_SERIE_A);
        matchController.getCompetitions(Constants.GERMAN_BUNDESLIGA);
        matchController.getCompetitions(Constants.SPAINISH_LA_LIGA);
        matchController.getCompetitions(Constants.PORTUGUESE_PRIMEIRA_LIGA);
        matchController.getCompetitions(Constants.FRENCH_LIGUE_1);
        matchController.getCompetitions(Constants.DUTCH_EREDIVISIE);
        matchController.getCompetitions(Constants.UEFA_CHAMPION_LEAGUE);

        logger.info("Initialize those data for standings");
        standingController.getTeams(Constants.ENGLISH_PREMIER_LEAGUE);
        standingController.getTeams(Constants.ITALIAN_SERIE_A);
        standingController.getTeams(Constants.GERMAN_BUNDESLIGA);
        standingController.getTeams(Constants.SPAINISH_LA_LIGA);
        standingController.getTeams(Constants.PORTUGUESE_PRIMEIRA_LIGA);
        standingController.getTeams(Constants.FRENCH_LIGUE_1);
        standingController.getTeams(Constants.DUTCH_EREDIVISIE);
        standingController.getTeams(Constants.UEFA_CHAMPION_LEAGUE);

        for (Long leagueId : Constants.COMMON_LEAGUE_LIST) {
            logger.info("Initialize team squad");
            List<Team> teamList = teamService.findByCompetitionId(leagueId);
            for (Team t : teamList) {
                squadController.getPlayers(t.getId());
            }
        }
    }
}