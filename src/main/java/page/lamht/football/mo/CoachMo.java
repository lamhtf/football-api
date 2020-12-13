package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CoachMo {
    private Long id;
    private String name;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String countryOfBirth;
    private String nationality;
    private Timestamp lastUpdated;
}
