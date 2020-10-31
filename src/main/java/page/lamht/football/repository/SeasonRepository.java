package page.lamht.football.repository;

import org.springframework.data.repository.CrudRepository;
import page.lamht.football.entity.Area;
import page.lamht.football.entity.Season;

public interface SeasonRepository extends CrudRepository<Season, Long> {
}
