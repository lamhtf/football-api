package page.lamht.football.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import page.lamht.football.util.Constants;

@Component
public class PostStartUpRunner implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(PostStartUpRunner.class);

    @Autowired
    AreaController areaController;

    @Autowired
    CompetitionController competitionController;

    @Autowired
    TeamController teamController;

    @Override
    public void run(String...args) throws Exception {
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
    }
}