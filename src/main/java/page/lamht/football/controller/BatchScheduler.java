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
        logger.info("Current time is :: " + Calendar.getInstance().getTime());
        return areaController.getAreas(TOKEN_1);
    }

    @Scheduled(cron = "10 0 0 * * ?")
    public String runCompetitions() {
        logger.info("Current time is :: " + Calendar.getInstance().getTime());
        return competitionController.getCompetitions(TOKEN_1);
    }

    @Scheduled(cron = "0 0/3 12-14 * * ?")
    public String runEPLMatches() {
        logger.info("Current time is :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("EPL", TOKEN_1);
    }

    @Scheduled(cron = "20 0/3 0 * * ?")
    public String runSAMatches() {
        logger.info("Current time is :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("SA", TOKEN_1);
    }

    @Scheduled(cron = "40 0/3 0 * * ?")
    public String runBL1Matches() {
        logger.info("Current time is :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("BL1", TOKEN_1);
    }

    @Scheduled(cron = "0 1/3 12-14 * * ?")
    public String runLLMatches() {
        logger.info("Current time is :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("LL", TOKEN_1);
    }

    @Scheduled(cron = "20 1/3 12-14 * * ?")
    public String runPPLMatches() {
        logger.info("Current time is :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("PPL", TOKEN_1);
    }

    @Scheduled(cron = "40 1/3 12-14 * * ?")
    public String runFL1Matches() {
        logger.info("Current time is :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("FL1", TOKEN_1);
    }

    @Scheduled(cron = "0 2/3 12-14 * * ?")
    public String runDEMatches() {
        logger.info("Current time is :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("DE", TOKEN_1);
    }

    @Scheduled(cron = "20 2/3 12-14 * * ?")
    public String runCLMatches() {
        logger.info("Current time is :: " + Calendar.getInstance().getTime());
        return matchController.getCompetitions("CL", TOKEN_1);
    }

}
