package page.lamht.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper=false)
@Data
@Table("player")
public class Player extends Entity {

    private String name;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String countryOfBirth;
    private String nationality;
    private String position;
    private Integer shirtNumber;
    private Long teamId;
    private Timestamp lastUpdated;
}
