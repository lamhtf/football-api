package page.lamht.football.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;
import page.lamht.football.entity.Entity;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper=false)
@Data
public class PersonDto {

    private Long id;
    private String name;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String countryOfBirth;
    private String nationality;
    private String position;
    private Integer shirtNumber;
    private String role;
    private Timestamp lastUpdated;
}
