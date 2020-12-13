package page.lamht.football.mo;

import lombok.Data;
import page.lamht.football.entity.Area;
import page.lamht.football.entity.Player;

import java.sql.Timestamp;
import java.util.List;

@Data
public class TeamMo {
    private Long id;
    private AreaMo area;
    private List<CompetitionMo> activeCompetitions;

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
    private List<CoachMo> coaches;
    private List<PlayerMo> players;
    private Timestamp lastUpdated;
}
