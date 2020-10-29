package page.lamht.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper=false)
@Data
@Table("player")
public class Player extends Entity {

    private String name;

}
