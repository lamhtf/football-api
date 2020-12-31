package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MatchResponse {
    public MatchResponse(List<MatchMo> matches, Integer total, Integer current, Timestamp lastUpdated){
        this.setMatches(matches);
        this.setTotal(total);
        this.setCurrent(current);
        this.setLastUpdated(lastUpdated);
    }

    private List<MatchMo> matches;
    private Integer total;
    private Integer current;
    private Timestamp lastUpdated;
}
