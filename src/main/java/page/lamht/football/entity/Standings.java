package page.lamht.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
@Table("standing")
public class Standings extends Entity {

    public Standings(String type, String stage, String group){
        this.setType(type);
        this.setStage(stage);
        this.setGroup(group);
    }

    @MappedCollection(idColumn = "id")
    private Competition competition;
    private Long competition_id;

    private String stage;
    private String type;
    private String group;

    private List<Standing> table;
}
