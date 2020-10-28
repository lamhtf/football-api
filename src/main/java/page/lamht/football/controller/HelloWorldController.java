package page.lamht.football.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import page.lamht.football.entity.Player;
import page.lamht.football.repository.PlayerRepository;

@RestController
class HelloWorldController {

  private final PlayerRepository playerRepository;

  HelloWorldController(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  @GetMapping("/hello")
  String helloWorld(){

    Iterable<Player> players = playerRepository.findAll();

    return "Hello AWS! Successfully connected to the database!";
  }

}
