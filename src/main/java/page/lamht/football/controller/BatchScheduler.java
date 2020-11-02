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

//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0/10 * * * * ?")
    public String runAreas() {
        logger.info("Current time is :: " + Calendar.getInstance().getTime());
//        return areaController.getAreas(TOKEN_1);
        return areaController.getAreas(null);
    }
}
