package page.lamht.football.util;

import static page.lamht.football.util.Constants.*;

public final class Utils {

    private static Boolean _LOCAL_TEST_ = true;

    public static String selectMatchApi(String league){

        String url = switch (league) {
            case "EPL" -> EPL_MATCHES;
            case "SA" -> SA_MATCHES;
            case "BL1" -> BL1_MATCHES;
            case "LL" -> LL_MATCHES;
            case "PPL" -> PPL_MATCHES;
            case "FL1" -> FL1_MATCHES;
            case "DE" -> DE_MATCHES;
            default -> CL_MATCHES;
        };

        if (_LOCAL_TEST_) url = LOCAL_HOST + "/matchesTest/" + league;
        return url;
    }

    public static String selectStandingsApi(String league){

        String url = switch (league) {
            case "EPL" -> EPL_TABLE;
            case "SA" -> SA_TABLE;
            case "BL1" -> BL1_TABLE;
            case "LL" -> LL_TABLE;
            case "PPL" -> PPL_TABLE;
            case "FL1" -> FL1_TABLE;
            case "DE" -> DE_TABLE;
            default -> CL_TABLE;
        };

        if (_LOCAL_TEST_) url = LOCAL_HOST + "/standingsTest/" + league;
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

}
