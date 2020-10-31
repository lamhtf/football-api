package page.lamht.football.repository;

import org.springframework.data.repository.CrudRepository;
import page.lamht.football.entity.Competition;
import page.lamht.football.entity.Team;

public interface CompetitionRepository extends CrudRepository<Competition, Long> {
}
