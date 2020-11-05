package page.lamht.football.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Calendar;

import static page.lamht.football.util.Constants.TOKEN_1;

@Controller
public class BatchScheduler {

    Logger logger = LoggerFactory.getLogger(BatchScheduler.class);

    @Autowired
    private AreaController areaController;
    @Autowired
    private CompetitionController competitionController;
    @Autowired
    private MatchController matchController;

    @Scheduled(cron = "0 0 0 * * ?")
    public String runAreas() {
        logger.info("runAreas :: " + Calendar.getInstance().getTime());
        return areaController.getAreas(TOKEN_1);
    }

    @Scheduled(cron = "10 0 0 * * ?")
    public String runCompetitions() {
        logger.info("runCompetitions :: " + Calendar.getInstance().getTime());
        return competitionController.getCompetitions(TOKEN_1);
    }

//    @Scheduled(cron = "0 1 0 * * ?")
//    public String runTeams() {
//        logger.info("runEPLTeams :: " + Calendar.getInstance().getTime());
//        return areaController.getAreas(TOKEN_1);
//    }


    @Scheduled(cron = "0 0/3 0-7,19-23 * * ?")
    public String runEPLMatches() {
        logger.info("runEPLMatches :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("EPL", TOKEN_1);
    }

    @Scheduled(cron = "20 0/3 0-7,19-23 * * ?")
    public String runSAMatches() {
        logger.info("runSAMatches :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("SA", TOKEN_1);
    }

    @Scheduled(cron = "40 0/3 0-7,19-23 * * ?")
    public String runBL1Matches() {
        logger.info("runBL1Matches :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("BL1", TOKEN_1);
    }

    @Scheduled(cron = "0 1/3 0-7,19-23 * * ?")
    public String runLLMatches() {
        logger.info("runLLMatches :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("LL", TOKEN_1);
    }

    @Scheduled(cron = "20 1/3 0-7,19-23 * * ?")
    public String runPPLMatches() {
        logger.info("runPPLMatches :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("PPL", TOKEN_1);
    }

    @Scheduled(cron = "40 1/3 0-7,19-23 * * ?")
    public String runFL1Matches() {
        logger.info("runFL1Matches :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("FL1", TOKEN_1);
    }

    @Scheduled(cron = "0 2/3 0-7,19-23 * * ?")
    public String runDEMatches() {
        logger.info("runDEMatches :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("DE", TOKEN_1);
    }

    @Scheduled(cron = "20 2/3 0-7,17,19-23 * * ?")
    public String runCLMatches() {
        logger.info("runCLMatches :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("CL", TOKEN_1);
    }

}