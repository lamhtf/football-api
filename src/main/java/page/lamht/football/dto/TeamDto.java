package page.lamht.football.dto;

import lombok.Data;
import page.lamht.football.entity.*;

import java.sql.Timestamp;
import java.util.List;

@Data
public class TeamDto {
    private Long id;
    private Area area;
    private List<Competition> activeCompetitions;
    private String name;
    private String shortName;
    private String tla;
    private String crestUrl;
    private String address;
    private String phone;
    private String website;
    private String email;
    private Integer founded;
    private String clubColors;
    private String venue;
    private List<PersonDto> squad;
    private Timestamp lastUpdated;
}
