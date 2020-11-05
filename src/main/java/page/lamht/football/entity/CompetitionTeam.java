package page.lamht.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper=false)
@Data
@Table("competition_team")
public class CompetitionTeam extends Entity {

    private Long competitionId;
    private Competition competition;

    private Long teamId;
    private Team team;

}
