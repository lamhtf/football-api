package page.lamht.football.mo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
public class FavouriteResponse {

    public FavouriteResponse(List<FavouriteTeamMo> teams, Timestamp lastUpdated){
        this.setTeams(teams);
        this.setLastUpdated(lastUpdated);
    }

    List<FavouriteTeamMo> teams;
    Timestamp lastUpdated;
}
