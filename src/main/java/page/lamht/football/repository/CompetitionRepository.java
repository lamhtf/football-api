package page.lamht.football.repository;

import org.springframework.data.repository.CrudRepository;
import page.lamht.football.entity.Area;
import page.lamht.football.entity.Competition;

public interface CompetitionRepository extends CrudRepository<Competition, Long> {
}
