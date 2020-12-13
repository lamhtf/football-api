package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class TeamResponse {

    public TeamResponse(TeamMo team, Timestamp lastUpdated){
        this.setTeam(team);
        this.setLastUpdated(lastUpdated);
    };

    TeamMo team;
    Timestamp lastUpdated;
}