package page.lamht.football.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import page.lamht.football.entity.Team;
import page.lamht.football.repository.TeamService;
import page.lamht.football.util.Constants;

import java.util.Calendar;
import java.util.List;

import static page.lamht.football.util.Constants.*;

@Component
public class BatchScheduler {
    Logger logger = LoggerFactory.getLogger(BatchScheduler.class);

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

    @Scheduled(cron = "${schedule.init}")
    public void runInit() {
        logger.info("runInit :: start");
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

        for (Long leagueId : Constants.COMMON_LEAGUE_LIST) {
            logger.info("Initialize team squad");
            List<Team> teamList = teamService.findByCompetitionId(leagueId);
            for (Team t : teamList) {
                squadController.getPlayers(t.getId());
            }
        }
    }

    @Scheduled(cron = "${schedule.fixtures}")
    public void runFixtures() {
        logger.info("runFixtures :: start");
        matchController.getFixtures(Constants.ENGLISH_PREMIER_LEAGUE);
        matchController.getFixtures(Constants.ITALIAN_SERIE_A);
        matchController.getFixtures(Constants.GERMAN_BUNDESLIGA);
        matchController.getFixtures(Constants.SPAINISH_LA_LIGA);
        matchController.getFixtures(Constants.PORTUGUESE_PRIMEIRA_LIGA);
        matchController.getFixtures(Constants.FRENCH_LIGUE_1);
        matchController.getFixtures(Constants.DUTCH_EREDIVISIE);
        matchController.getFixtures(Constants.UEFA_CHAMPION_LEAGUE);
    }

    @Scheduled(cron = "${schedule.standings}")
    public void runStandings() {
        logger.info("runStandings :: start");
        standingController.getStandingTables(Constants.ENGLISH_PREMIER_LEAGUE);
        standingController.getStandingTables(Constants.ITALIAN_SERIE_A);
        standingController.getStandingTables(Constants.GERMAN_BUNDESLIGA);
        standingController.getStandingTables(Constants.SPAINISH_LA_LIGA);
        standingController.getStandingTables(Constants.PORTUGUESE_PRIMEIRA_LIGA);
        standingController.getStandingTables(Constants.FRENCH_LIGUE_1);
        standingController.getStandingTables(Constants.DUTCH_EREDIVISIE);
        standingController.getStandingTables(Constants.UEFA_CHAMPION_LEAGUE);
    }
}
