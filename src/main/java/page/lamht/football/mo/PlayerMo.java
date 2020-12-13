package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PlayerMo {
    private Long id;
    private String name;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String countryOfBirth;
    private String nationality;
    private String position;
    private Integer shirtNumber;
    private Timestamp lastUpdated;
}
