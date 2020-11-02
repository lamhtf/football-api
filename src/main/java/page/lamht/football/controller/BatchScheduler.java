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

    @Scheduled(cron = "0 10 0 * * ?")
    public String runMatches() {
        logger.info("Current time is :: " + Calendar.getInstance().getTime());
        return areaController.getAreas(TOKEN_1);
    }


}
