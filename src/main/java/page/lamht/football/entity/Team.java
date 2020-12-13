package page.lamht.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.List;

@EqualsAndHashCode(callSuper=false)
@Data
@Table("team")
public class Team extends Entity {

    @MappedCollection(idColumn = "id")
    private Area area;
    private Long areaId;

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
    private Timestamp lastUpdated;

    @MappedCollection(idColumn = "team_id")
    private CompetitionTeam competitionTeam;

    private List<Competition> activeCompetitions;
    private List<Coach> coaches;
    private List<Player> players;
}