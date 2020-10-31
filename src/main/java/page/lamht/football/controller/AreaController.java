package page.lamht.football.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import page.lamht.football.dto.AreasDto;
import page.lamht.football.entity.Area;
import page.lamht.football.repository.AreaRepository;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

@RestController
class AreaController {

  Logger logger = LoggerFactory.getLogger(AreaController.class);

  @Autowired
  private AreaRepository areaRepository;

  @GetMapping("/areas")
  String getAreas(){
      logger.info("start time: " + new Timestamp(System.currentTimeMillis()));

    WebClient webClient = WebClient.create();

    Mono<AreasDto> areasDtoMono = webClient.get()
            .uri("https://api.football-data.org/v2/areas/")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .retrieve()
            .bodyToMono(AreasDto.class);

    AreasDto areasDto = areasDtoMono.block();

    for (Area area: areasDto.getAreas()){
        areaRepository.findById(area.getId()).ifPresentOrElse(
          a-> areaRepository.save(area),
          ()-> {
            area.setNew(true);
           areaRepository.save(area);
          }
        );
    };

    logger.info("end time: " + new Timestamp(System.currentTimeMillis()));

    return "Completed Successfully";
  }

}
