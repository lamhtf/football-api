package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MatchResponse {
    public MatchResponse(List<MatchMo> matches, Timestamp lastUpdated){
        this.setMatches(matches);
        this.setLastUpdated(lastUpdated);
    }

    private List<MatchMo> matches;
    private Timestamp lastUpdated;
}
