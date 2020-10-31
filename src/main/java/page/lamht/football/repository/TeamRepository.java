package page.lamht.football.repository;

import org.springframework.data.repository.CrudRepository;
import page.lamht.football.entity.Season;
import page.lamht.football.entity.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {
}
