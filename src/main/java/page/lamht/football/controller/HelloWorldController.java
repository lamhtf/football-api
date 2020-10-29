package page.lamht.football.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import page.lamht.football.dto.AreasDto;
import page.lamht.football.entity.Area;
import page.lamht.football.repository.AreaRepository;
//import org.springframework.web.reactive.function.client.WebClient;

@RestController
class HelloWorldController {

  @Autowired
  private AreaRepository areaRepository;

  @GetMapping("/hello")
  String helloWorld(){



//    WebClient client3 = WebClient
//            .builder()
//            .baseUrl("http://localhost:8080")
//            .defaultCookie("cookieKey", "cookieValue")
//            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//            .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
//            .build();
    String json = "";

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      AreasDto areas = objectMapper.readValue(json, AreasDto.class);

      for (Area area: areas.getAreas()){
          areaRepository.findById(area.getId()).ifPresentOrElse(
            a-> areaRepository.save(area),
            ()-> {
              area.setNew(true);
             areaRepository.save(area);
            }
          );
      };
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
    }

    Iterable<Area> areas = areaRepository.findAll();

    areas.forEach(area -> System.out.println(area.getId()));

    return "Hello AWS! Successfully connected to the database!";
  }

}
