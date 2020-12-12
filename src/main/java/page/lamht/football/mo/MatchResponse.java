package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MatchResponse {
    public MatchResponse(CompetitionMo competition, List<MatchMo> matches, Timestamp lastUpdated){
        this.setCompetition(competition);
        this.setMatches(matches);
        this.setLastUpdated(lastUpdated);
    }

    private CompetitionMo competition;
    private List<MatchMo> matches;
    private Timestamp lastUpdated;
}
