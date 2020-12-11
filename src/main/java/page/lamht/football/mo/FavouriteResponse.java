package page.lamht.football.mo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FavouriteResponse {

    public FavouriteResponse(List<FavouriteTeamMo> teams){
        this.setTeams(teams);
    }

    List<FavouriteTeamMo> teams;
}
