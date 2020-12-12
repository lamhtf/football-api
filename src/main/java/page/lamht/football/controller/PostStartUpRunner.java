package page.lamht.football.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.stereotype.Component;
import page.lamht.football.entity.Team;
import page.lamht.football.repository.TeamService;
import page.lamht.football.util.Constants;

import java.util.List;

@Component
public class PostStartUpRunner implements CommandLineRunner {
    private final static Logger logger = LoggerFactory.getLogger(PostStartUpRunner.class);

    @Value("${init}")
    Boolean init;

    @Autowired
    BatchScheduler batchScheduler;

    @Override
    public void run(String... args) throws Exception {
        if (init) {
            logger.info("start init");
            batchScheduler.init();
            batchScheduler.initFixtures();
            batchScheduler.initStandings();
            logger.info("complete init");
        }
    }

}