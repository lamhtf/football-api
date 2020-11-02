package page.lamht.football.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.dto.AreasDto;
import page.lamht.football.entity.Area;
import page.lamht.football.repository.AreaRepository;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.Calendar;

import static page.lamht.football.util.Constants.*;

@RestController
class AreaController {

    Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaRepository areaRepository;

//    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "0/10 * * * * ?")
//    public String runAreas() {
//        logger.info("Current time is :: " + Calendar.getInstance().getTime());
//        return this.getAreas(TOKEN_1);
//    }

    @GetMapping("/areas/{token}")
    String getAreas(@PathVariable String token) {
        logger.info("start time: " + new Timestamp(System.currentTimeMillis()));

        String URL = LOCAL_HOST + "/areasTest";

        if (!StringUtils.isEmpty(token)) URL = AREAS;

        WebClient webClient = WebClient.create();

        Mono<AreasDto> areasDtoMono = webClient.get()
                .uri(URL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(X_AUTH_TOKEN, token)
                .retrieve()
                .bodyToMono(AreasDto.class);

        AreasDto areasDto = areasDtoMono.block();

        for (Area area : areasDto.getAreas()) {
            areaRepository.findById(area.getId()).ifPresentOrElse(
                    a -> areaRepository.save(area),
                    () -> {
                        area.setNew(true);
                        areaRepository.save(area);
                    }
            );
        }

        logger.info("end time: " + new Timestamp(System.currentTimeMillis()));

        return "Completed Successfully";
    }

}
