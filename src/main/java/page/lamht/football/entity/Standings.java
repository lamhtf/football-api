package page.lamht.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper=false)
@Data
@Table("standing")
public class Standings extends Entity {

    @MappedCollection(idColumn = "id")
    private Competition competition;
    private Long competition_id;

    private String stage;
    private String type;
    private String group;
}
