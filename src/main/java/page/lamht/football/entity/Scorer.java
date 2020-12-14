package page.lamht.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper=false)
@Data
@Table("scorer")
public class Scorer extends Entity {

    private Long competitionId;
    private Long seasonId;
    private Long playerId;
    private Player player;
    private Long teamId;
    private Team team;
    private Integer numberOfGoals;
}
