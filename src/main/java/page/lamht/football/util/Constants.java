package page.lamht.football.util;

import java.util.List;

public final class Constants {

    public final static String X_AUTH_TOKEN = "X-Auth-Token";

    public final static String LOCAL_HOST = "http://localhost:8080";
    public final static String API_HOST_V2 = "https://api.football-data.org/v2";

    public final static String AREAS = API_HOST_V2 + "/areas";
    public final static String COMPETITIONS = API_HOST_V2 + "/competitions";
    public final static String TEAM_SQUAD = API_HOST_V2 + "/teams";

    public final static String ENGLISH_PREMIER_LEAGUE = "PL";
    public final static String ITALIAN_SERIE_A = "SA";
    public final static String GERMAN_BUNDESLIGA = "BL1";
    public final static String SPAINISH_LA_LIGA = "PD";
    public final static String PORTUGUESE_PRIMEIRA_LIGA = "PPL";
    public final static String FRENCH_LIGUE_1 = "FL1";
    public final static String DUTCH_EREDIVISIE = "DED";
    public final static String UEFA_CHAMPION_LEAGUE = "CL";

    public final static Long EPL_LEAGUE_ID = Long.valueOf(2021);
    public final static Long SA_LEAGUE_ID = Long.valueOf(2019);
    public final static Long BL1_LEAGUE_ID = Long.valueOf(2002);
    public final static Long LL_LEAGUE_ID = Long.valueOf(2014);
    public final static Long PPL_LEAGUE_ID = Long.valueOf(2017);
    public final static Long FL1_LEAGUE_ID = Long.valueOf(2015);
    public final static Long DE_LEAGUE_ID = Long.valueOf(2003);
    public final static Long CL_LEAGUE_ID = Long.valueOf(2001);

    public final static List<Long> COMMON_LEAGUE_LIST =
            List.of(EPL_LEAGUE_ID, SA_LEAGUE_ID, BL1_LEAGUE_ID, LL_LEAGUE_ID, PPL_LEAGUE_ID, FL1_LEAGUE_ID, DE_LEAGUE_ID, CL_LEAGUE_ID);

    public final static String EPL_TABLE = API_HOST_V2 + "/competitions/PL/standings";
    public final static String SA_TABLE = API_HOST_V2 + "/competitions/SA/standings";
    public final static String BL1_TABLE = API_HOST_V2 + "/competitions/BL1/standings";
    public final static String LL_TABLE = API_HOST_V2 + "/competitions/PD/standings";
    public final static String PPL_TABLE = API_HOST_V2 + "/competitions/PPL/standings";
    public final static String FL1_TABLE = API_HOST_V2 + "/competitions/FL1/standings";
    public final static String DE_TABLE = API_HOST_V2 + "/competitions/DED/standings";
    public final static String CL_TABLE = API_HOST_V2 + "/competitions/CL/standings";

    public final static String EPL_SCORERS = API_HOST_V2 + "/competitions/2021/scorers";
    public final static String SA_SCORERS = API_HOST_V2 + "/competitions/2019/scorers";
    public final static String BL1_SCORERS = API_HOST_V2 + "/competitions/2002/scorers";
    public final static String LL_SCORERS = API_HOST_V2 + "/competitions/2014/scorers";
    public final static String PPL_SCORERS = API_HOST_V2 + "/competitions/2017/scorers";
    public final static String FL1_SCORERS = API_HOST_V2 + "/competitions/2015/scorers";
    public final static String DE_SCORERS = API_HOST_V2 + "/competitions/2003/scorers";
    public final static String CL_SCORERS = API_HOST_V2 + "/competitions/2001/scorers";

    public final static String EPL_MATCHES = API_HOST_V2 + "/competitions/2021/matches";
    public final static String SA_MATCHES = API_HOST_V2 + "/competitions/2019/matches";
    public final static String BL1_MATCHES = API_HOST_V2 + "/competitions/2002/matches";
    public final static String LL_MATCHES = API_HOST_V2 + "/competitions/2014/matches";
    public final static String PPL_MATCHES = API_HOST_V2 + "/competitions/2017/matches";
    public final static String FL1_MATCHES = API_HOST_V2 + "/competitions/2015/matches";
    public final static String DE_MATCHES = API_HOST_V2 + "/competitions/2003/matches";
    public final static String CL_MATCHES = API_HOST_V2 + "/competitions/2001/matches";

    public final static String EPL_TEAMS = API_HOST_V2 + "/competitions/2021/teams";
    public final static String SA_TEAMS = API_HOST_V2 + "/competitions/2019/teams";
    public final static String BL1_TEAMS = API_HOST_V2 + "/competitions/2002/teams";
    public final static String LL_TEAMS = API_HOST_V2 + "/competitions/2014/teams";
    public final static String PPL_TEAMS = API_HOST_V2 + "/competitions/2017/teams";
    public final static String FL1_TEAMS = API_HOST_V2 + "/competitions/2015/teams";
    public final static String DE_TEAMS = API_HOST_V2 + "/competitions/2003/teams";
    public final static String CL_TEAMS = API_HOST_V2 + "/competitions/2001/teams";

}
