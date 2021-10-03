package page.lamht.football.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import page.lamht.football.entity.Team;
import page.lamht.football.repository.TeamService;
import page.lamht.football.util.Constants;

import java.util.List;

@Component
public class BatchScheduler {
    private final static Logger logger = LoggerFactory.getLogger(BatchScheduler.class);

    private static boolean isInitNow = false;

    @Value("${scheduler}")
    Boolean scheduler;

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
    StatisticController statisticController;
    @Autowired
    TeamService teamService;

    @Scheduled(cron = "${schedule.init}")
    public void runInit() {
        if (!scheduler) {
            logger.info("runInit :: scheduler disabled");
            return;
        }
        logger.info("runInit :: start");
        logger.info("Initialize those data for areas");
        areaController.getAreas();
        logger.info("Initialize those data for competitions");
        competitionController.getCompetitions();
        logger.info("Initialize those data for teams in ENGLISH_PREMIER_LEAGUE");
        teamController.getTeams(Constants.ENGLISH_PREMIER_LEAGUE);
        logger.info("Initialize those data for teams in ITALIAN_SERIE_A");
        teamController.getTeams(Constants.ITALIAN_SERIE_A);
        logger.info("Initialize those data for teams in GERMAN_BUNDESLIGA");
        teamController.getTeams(Constants.GERMAN_BUNDESLIGA);
        logger.info("Initialize those data for teams in SPAINISH_LA_LIGA");
        teamController.getTeams(Constants.SPAINISH_LA_LIGA);
        logger.info("Initialize those data for teams in PORTUGUESE_PRIMEIRA_LIGA");
        teamController.getTeams(Constants.PORTUGUESE_PRIMEIRA_LIGA);
        logger.info("Initialize those data for teams in FRENCH_LIGUE_1");
        teamController.getTeams(Constants.FRENCH_LIGUE_1);
        logger.info("Initialize those data for teams in DUTCH_EREDIVISIE");
        teamController.getTeams(Constants.DUTCH_EREDIVISIE);
        logger.info("Initialize those data for teams in UEFA_CHAMPION_LEAGUE");
        teamController.getTeams(Constants.UEFA_CHAMPION_LEAGUE);
        logger.info("Initialize those data for teams :: postScheduleJobDataPatch");
        teamController.postScheduleJobDataPatch();

        for (Long leagueId : Constants.COMMON_LEAGUE_LIST) {
            logger.info("Initialize team squad league id = " + leagueId);
            List<Team> teamList = teamService.findByCompetitionId(leagueId);
            for (Team t : teamList) {
                squadController.getPlayers(t.getId());
            }
        }
        logger.info("Initialize team squad :: postScheduleJobDataPatch");
        squadController.postScheduleJobDataPatch();

        this.initFixtures();
        this.initStandings();
        this.initStatistics();
    }

    @Scheduled(cron = "${schedule.fixtures}")
    public void runFixtures() {
        if (!scheduler || isInitNow) {
            logger.info("runFixtures :: scheduler disabled or init now");
            return;
        }
        logger.info("runFixtures :: start");
        logger.info("runFixtures :: ENGLISH_PREMIER_LEAGUE");
        matchController.getFixtures(Constants.ENGLISH_PREMIER_LEAGUE);
        logger.info("runFixtures :: ITALIAN_SERIE_A");
        matchController.getFixtures(Constants.ITALIAN_SERIE_A);
        logger.info("runFixtures :: GERMAN_BUNDESLIGA");
        matchController.getFixtures(Constants.GERMAN_BUNDESLIGA);
        logger.info("runFixtures :: SPAINISH_LA_LIGA");
        matchController.getFixtures(Constants.SPAINISH_LA_LIGA);
        logger.info("runFixtures :: PORTUGUESE_PRIMEIRA_LIGA");
        matchController.getFixtures(Constants.PORTUGUESE_PRIMEIRA_LIGA);
        logger.info("runFixtures :: FRENCH_LIGUE_1");
        matchController.getFixtures(Constants.FRENCH_LIGUE_1);
        logger.info("runFixtures :: DUTCH_EREDIVISIE");
        matchController.getFixtures(Constants.DUTCH_EREDIVISIE);
        logger.info("runFixtures :: UEFA_CHAMPION_LEAGUE");
        matchController.getFixtures(Constants.UEFA_CHAMPION_LEAGUE);
    }

    @Scheduled(cron = "${schedule.standings}")
    public void runStandings() {
        if (!scheduler || isInitNow) {
            logger.info("runStandings :: scheduler disabled or init now");
            return;
        }
        logger.info("runStandings :: start");
        logger.info("runStandings :: ENGLISH_PREMIER_LEAGUE");
        standingController.getStandingTables(Constants.ENGLISH_PREMIER_LEAGUE);
        logger.info("runStandings :: ITALIAN_SERIE_A");
        standingController.getStandingTables(Constants.ITALIAN_SERIE_A);
        logger.info("runStandings :: GERMAN_BUNDESLIGA");
        standingController.getStandingTables(Constants.GERMAN_BUNDESLIGA);
        logger.info("runStandings :: SPAINISH_LA_LIGA");
        standingController.getStandingTables(Constants.SPAINISH_LA_LIGA);
        logger.info("runStandings :: PORTUGUESE_PRIMEIRA_LIGA");
        standingController.getStandingTables(Constants.PORTUGUESE_PRIMEIRA_LIGA);
        logger.info("runStandings :: FRENCH_LIGUE_1");
        standingController.getStandingTables(Constants.FRENCH_LIGUE_1);
        logger.info("runStandings :: DUTCH_EREDIVISIE");
        standingController.getStandingTables(Constants.DUTCH_EREDIVISIE);
        logger.info("runStandings :: UEFA_CHAMPION_LEAGUE");
        standingController.getStandingTables(Constants.UEFA_CHAMPION_LEAGUE);
        logger.info("runStandings :: postScheduleJobDataPatch");
        standingController.postScheduleJobDataPatch();
    }

    @Scheduled(cron = "${schedule.statistics}")
    public void runStatistics() {
        if (!scheduler || isInitNow) {
            logger.info("runStatistics :: scheduler disabled or init now");
            return;
        }
        logger.info("runStatistics :: start");
        logger.info("runStatistics :: ENGLISH_PREMIER_LEAGUE");
        statisticController.runScorers(Constants.ENGLISH_PREMIER_LEAGUE);
        logger.info("runStatistics :: ITALIAN_SERIE_A");
        statisticController.runScorers(Constants.ITALIAN_SERIE_A);
        logger.info("runStatistics :: GERMAN_BUNDESLIGA");
        statisticController.runScorers(Constants.GERMAN_BUNDESLIGA);
        logger.info("runStatistics :: SPAINISH_LA_LIGA");
        statisticController.runScorers(Constants.SPAINISH_LA_LIGA);
        logger.info("runStatistics :: PORTUGUESE_PRIMEIRA_LIGA");
        statisticController.runScorers(Constants.PORTUGUESE_PRIMEIRA_LIGA);
        logger.info("runStatistics :: FRENCH_LIGUE_1");
        statisticController.runScorers(Constants.FRENCH_LIGUE_1);
        logger.info("runStatistics :: DUTCH_EREDIVISIE");
        statisticController.runScorers(Constants.DUTCH_EREDIVISIE);
        logger.info("runStatistics :: UEFA_CHAMPION_LEAGUE");
        statisticController.runScorers(Constants.UEFA_CHAMPION_LEAGUE);
        logger.info("runStatistics :: postScheduleJobDataPatch");
        statisticController.postScheduleJobDataPatch();
    }


    /****
     *
     * sosad to write these init functions
     */
    public void init() {
        isInitNow = true;
        logger.info("runInit :: start");
        logger.info("Initialize those data for areas");
        areaController.getAreas();
        logger.info("Initialize those data for competitions");
        competitionController.getCompetitions();
        logger.info("Initialize those data for teams in ENGLISH_PREMIER_LEAGUE");
        teamController.getTeams(Constants.ENGLISH_PREMIER_LEAGUE);
        logger.info("Initialize those data for teams in ITALIAN_SERIE_A");
        teamController.getTeams(Constants.ITALIAN_SERIE_A);
        logger.info("Initialize those data for teams in GERMAN_BUNDESLIGA");
        teamController.getTeams(Constants.GERMAN_BUNDESLIGA);
        logger.info("Initialize those data for teams in SPAINISH_LA_LIGA");
        teamController.getTeams(Constants.SPAINISH_LA_LIGA);
        logger.info("Initialize those data for teams in PORTUGUESE_PRIMEIRA_LIGA");
        teamController.getTeams(Constants.PORTUGUESE_PRIMEIRA_LIGA);
        logger.info("Initialize those data for teams in FRENCH_LIGUE_1");
        teamController.getTeams(Constants.FRENCH_LIGUE_1);
        logger.info("Initialize those data for teams in DUTCH_EREDIVISIE");
        teamController.getTeams(Constants.DUTCH_EREDIVISIE);
        logger.info("Initialize those data for teams in UEFA_CHAMPION_LEAGUE");
        teamController.getTeams(Constants.UEFA_CHAMPION_LEAGUE);
        logger.info("Initialize those data for teams :: postScheduleJobDataPatch");
        teamController.postScheduleJobDataPatch();

        for (Long leagueId : Constants.COMMON_LEAGUE_LIST) {
            logger.info("Initialize team squad id = " + leagueId);
            List<Team> teamList = teamService.findByCompetitionId(leagueId);
            for (Team t : teamList) {
                squadController.getPlayers(t.getId());
            }
        }
        logger.info("Initialize team squad id :: postScheduleJobDataPatch");
        squadController.postScheduleJobDataPatch();
    }

    public void initFixtures() {
        logger.info("initFixtures :: start");
        logger.info("initFixtures :: ENGLISH_PREMIER_LEAGUE");
        matchController.initFixtures(Constants.ENGLISH_PREMIER_LEAGUE);
        logger.info("initFixtures :: ITALIAN_SERIE_A");
        matchController.initFixtures(Constants.ITALIAN_SERIE_A);
        logger.info("initFixtures :: GERMAN_BUNDESLIGA");
        matchController.initFixtures(Constants.GERMAN_BUNDESLIGA);
        logger.info("initFixtures :: SPAINISH_LA_LIGA");
        matchController.initFixtures(Constants.SPAINISH_LA_LIGA);
        logger.info("initFixtures :: PORTUGUESE_PRIMEIRA_LIGA");
        matchController.initFixtures(Constants.PORTUGUESE_PRIMEIRA_LIGA);
        logger.info("initFixtures :: FRENCH_LIGUE_1");
        matchController.initFixtures(Constants.FRENCH_LIGUE_1);
        logger.info("initFixtures :: DUTCH_EREDIVISIE");
        matchController.initFixtures(Constants.DUTCH_EREDIVISIE);
        logger.info("initFixtures :: UEFA_CHAMPION_LEAGUE");
        matchController.initFixtures(Constants.UEFA_CHAMPION_LEAGUE);
    }

    public void initStandings() {
        logger.info("initStandings :: start");
        logger.info("initStandings :: ENGLISH_PREMIER_LEAGUE");
        standingController.initStandingTables(Constants.ENGLISH_PREMIER_LEAGUE);
        logger.info("initStandings :: ITALIAN_SERIE_A");
        standingController.initStandingTables(Constants.ITALIAN_SERIE_A);
        logger.info("initStandings :: GERMAN_BUNDESLIGA");
        standingController.initStandingTables(Constants.GERMAN_BUNDESLIGA);
        logger.info("initStandings :: SPAINISH_LA_LIGA");
        standingController.initStandingTables(Constants.SPAINISH_LA_LIGA);
        logger.info("initStandings :: PORTUGUESE_PRIMEIRA_LIGA");
        standingController.initStandingTables(Constants.PORTUGUESE_PRIMEIRA_LIGA);
        logger.info("initStandings :: FRENCH_LIGUE_1");
        standingController.initStandingTables(Constants.FRENCH_LIGUE_1);
        logger.info("initStandings :: DUTCH_EREDIVISIE");
        standingController.initStandingTables(Constants.DUTCH_EREDIVISIE);
        logger.info("initStandings :: UEFA_CHAMPION_LEAGUE");
        standingController.initStandingTables(Constants.UEFA_CHAMPION_LEAGUE);
        logger.info("initStandings :: postScheduleJobDataPatch");
        standingController.postScheduleJobDataPatch();
    }

    public void initStatistics() {
        logger.info("initStatistics :: start");
        logger.info("initStatistics :: ENGLISH_PREMIER_LEAGUE");
        statisticController.initScorers(Constants.ENGLISH_PREMIER_LEAGUE);
        logger.info("initStatistics :: ITALIAN_SERIE_A");
        statisticController.initScorers(Constants.ITALIAN_SERIE_A);
        logger.info("initStatistics :: GERMAN_BUNDESLIGA");
        statisticController.initScorers(Constants.GERMAN_BUNDESLIGA);
        logger.info("initStatistics :: SPAINISH_LA_LIGA");
        statisticController.initScorers(Constants.SPAINISH_LA_LIGA);
        logger.info("initStatistics :: PORTUGUESE_PRIMEIRA_LIGA");
        statisticController.initScorers(Constants.PORTUGUESE_PRIMEIRA_LIGA);
        logger.info("initStatistics :: FRENCH_LIGUE_1");
        statisticController.initScorers(Constants.FRENCH_LIGUE_1);
        logger.info("initStatistics :: DUTCH_EREDIVISIE");
        statisticController.initScorers(Constants.DUTCH_EREDIVISIE);
        logger.info("initStatistics :: UEFA_CHAMPION_LEAGUE");
        statisticController.initScorers(Constants.UEFA_CHAMPION_LEAGUE);
        logger.info("initStatistics :: postScheduleJobDataPatch");
        statisticController.postScheduleJobDataPatch();
        isInitNow = false;
    }
}
