package page.lamht.football.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("player")
public class Player extends Entity {

    private String name;

}
