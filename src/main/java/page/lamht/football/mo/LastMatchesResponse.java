package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class LastMatchesResponse {

    public LastMatchesResponse(List<LastMatchMo> matchMos, Timestamp lastUpdated){
        this.setMatches(matchMos);
        this.setLastUpdated(lastUpdated);
    };
    List<LastMatchMo> matches;
    Timestamp lastUpdated;
}