package page.lamht.football.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class TokenSelector {

    private final static Logger logger = LoggerFactory.getLogger(TokenSelector.class);

    private final static List<String> tokens =
            Arrays.asList("b176748f45734c5e9431855fc91fec3d", "461e21480f2545d6930363c31d1c9c44", "9757392856634c3b9738e2167cbbc236");

    private static Integer count = 0;

    public static String getToken(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Integer length = tokens.size();
        Integer result = count%length;
        if (result == 0) count = 0;
        ++count;
//        logger.info("");
        return tokens.get(result);
    }


}
