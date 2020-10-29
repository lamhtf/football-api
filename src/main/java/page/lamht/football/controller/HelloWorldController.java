package page.lamht.football.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import page.lamht.football.dto.AreasDto;
import page.lamht.football.entity.Area;
import page.lamht.football.repository.AreaRepository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.Collections;

@RestController
class HelloWorldController {

  @Autowired
  private AreaRepository areaRepository;

  @GetMapping("/hello")
  String helloWorld(){
    System.out.println("start time: " + new Timestamp(System.currentTimeMillis()));

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

    System.out.println("end time: " + new Timestamp(System.currentTimeMillis()));

    return "Hello AWS! Successfully connected to the database!";
  }

}
