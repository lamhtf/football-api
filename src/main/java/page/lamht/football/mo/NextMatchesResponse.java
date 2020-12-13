package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class NextMatchesResponse {

    public NextMatchesResponse(List<NextMatchMo> matchMos, Timestamp lastUpdated){
        this.setMatches(matchMos);
        this.setLastUpdated(lastUpdated);
    };
//    FilterMo filter;
    List<NextMatchMo> matches;
    Timestamp lastUpdated;
}