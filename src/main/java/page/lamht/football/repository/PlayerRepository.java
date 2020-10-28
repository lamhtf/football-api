package page.lamht.football.repository;

import org.springframework.data.repository.CrudRepository;
import page.lamht.football.entity.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {
}
