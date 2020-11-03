package page.lamht.football.util;

import static page.lamht.football.util.Constants.*;

public final class Utils {

    private static Boolean _LOCAL_TEST_ = true;

    public static String selectMatchApi(String league){

        String URL = switch (league) {
            case "EPL" -> EPL_MATCHES;
            case "SA" -> SA_MATCHES;
            case "BL1" -> BL1_MATCHES;
            case "LL" -> LL_MATCHES;
            case "PPL" -> PPL_MATCHES;
            case "FL1" -> FL1_MATCHES;
            case "DE" -> DE_MATCHES;
            default -> CL_MATCHES;
        };

        if (_LOCAL_TEST_) URL = LOCAL_HOST + "/matchesTest/" + league;
        return URL;
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
