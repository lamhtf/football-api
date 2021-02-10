package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MatchResponse {
    public MatchResponse(List<MatchMo> matches, Integer total, Integer current, Integer min, Timestamp lastUpdated){
        this.setMatches(matches);
        this.setTotal(total);
        this.setMin(min);
        this.setCurrent(current);
        this.setLastUpdated(lastUpdated);
    }

    private List<MatchMo> matches;
    private Integer total;
    private Integer current;
    private Integer min;
    private Timestamp lastUpdated;
}
