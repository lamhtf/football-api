package page.lamht.football.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("player")
public class Player {

    @Id
    private Long id;

    private String name;

    public Player(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Player() {

    }
}
