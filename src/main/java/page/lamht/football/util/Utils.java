package page.lamht.football.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static page.lamht.football.util.Constants.*;

public final class Utils {

    private static Boolean _LOCAL_TEST_ = false;

    public static String selectScorerApi(String league){

        String url = switch (league) {
            case ENGLISH_PREMIER_LEAGUE -> EPL_SCORERS;
            case ITALIAN_SERIE_A -> SA_SCORERS;
            case GERMAN_BUNDESLIGA -> BL1_SCORERS;
            case SPAINISH_LA_LIGA -> LL_SCORERS;
            case PORTUGUESE_PRIMEIRA_LIGA -> PPL_SCORERS;
            case FRENCH_LIGUE_1 -> FL1_SCORERS;
            case DUTCH_EREDIVISIE -> DE_SCORERS;
            case UEFA_CHAMPION_LEAGUE -> CL_SCORERS;
            default -> null;
        };

        if (_LOCAL_TEST_) url = LOCAL_HOST + "/matchesTest/" + league;
        return url;
    }
    public static String selectMatchApi(String league){

        String url = switch (league) {
            case ENGLISH_PREMIER_LEAGUE -> EPL_MATCHES;
            case ITALIAN_SERIE_A -> SA_MATCHES;
            case GERMAN_BUNDESLIGA -> BL1_MATCHES;
            case SPAINISH_LA_LIGA -> LL_MATCHES;
            case PORTUGUESE_PRIMEIRA_LIGA -> PPL_MATCHES;
            case FRENCH_LIGUE_1 -> FL1_MATCHES;
            case DUTCH_EREDIVISIE -> DE_MATCHES;
            case UEFA_CHAMPION_LEAGUE -> CL_MATCHES;
            default -> null;
        };

        if (_LOCAL_TEST_) url = LOCAL_HOST + "/matchesTest/" + league;
        return url;
    }

    public static String selectStandingsApi(String league){

        String url = switch (league) {
            case ENGLISH_PREMIER_LEAGUE -> EPL_TABLE;
            case ITALIAN_SERIE_A -> SA_TABLE;
            case GERMAN_BUNDESLIGA -> BL1_TABLE;
            case SPAINISH_LA_LIGA -> LL_TABLE;
            case PORTUGUESE_PRIMEIRA_LIGA -> PPL_TABLE;
            case FRENCH_LIGUE_1 -> FL1_TABLE;
            case DUTCH_EREDIVISIE -> DE_TABLE;
            case UEFA_CHAMPION_LEAGUE -> CL_TABLE;
            default -> null;
        };

        if (_LOCAL_TEST_) return LOCAL_HOST + "/standingsTest/" + league;
        return url;
    }

    public static String selectAreaApi(){
        if (_LOCAL_TEST_) return LOCAL_HOST + "/areasTest/";
        return AREAS;
    }

    public static String selectCompetitionApi(){
        if (_LOCAL_TEST_) return LOCAL_HOST + "/competitionsTest/";
        return COMPETITIONS;
    }

    public static String selectTeamApi(String league){

        String url = switch (league) {
            case ENGLISH_PREMIER_LEAGUE -> EPL_TEAMS;
            case ITALIAN_SERIE_A -> SA_TEAMS;
            case GERMAN_BUNDESLIGA -> BL1_TEAMS;
            case SPAINISH_LA_LIGA -> LL_TEAMS;
            case PORTUGUESE_PRIMEIRA_LIGA -> PPL_TEAMS;
            case FRENCH_LIGUE_1 -> FL1_TEAMS;
            case DUTCH_EREDIVISIE -> DE_TEAMS;
            case UEFA_CHAMPION_LEAGUE -> CL_TEAMS;
            default -> null;
        };

        if (_LOCAL_TEST_) return LOCAL_HOST + "/teamsTest/" + league;
        return url;
    }

    public static String selectSquadApi(Long teamId){
        if (_LOCAL_TEST_) return "";
        return TEAM_SQUAD + "/" +teamId;
    }

    public static Long selectLeagueId(String league){
        Long leagueId = switch (league) {
            case ENGLISH_PREMIER_LEAGUE -> EPL_LEAGUE_ID;
            case ITALIAN_SERIE_A -> SA_LEAGUE_ID;
            case GERMAN_BUNDESLIGA -> BL1_LEAGUE_ID;
            case SPAINISH_LA_LIGA -> LL_LEAGUE_ID;
            case PORTUGUESE_PRIMEIRA_LIGA -> PPL_LEAGUE_ID;
            case FRENCH_LIGUE_1 -> FL1_LEAGUE_ID;
            case DUTCH_EREDIVISIE -> DE_LEAGUE_ID;
            case UEFA_CHAMPION_LEAGUE -> CL_LEAGUE_ID;
            default -> null;
        };
        return leagueId;
    }

    public static String getLast3days(){
        Instant now = Instant.now();
        Instant yesterday = now.minus(3, ChronoUnit.DAYS);
        return yesterday.toString().substring(0,10);
    }

    public static String getToday(){
        Instant now = Instant.now();
        return now.toString().substring(0,10);
    }

}
