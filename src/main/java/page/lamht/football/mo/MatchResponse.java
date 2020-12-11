package page.lamht.football.mo;

import lombok.Data;

import java.util.List;

@Data
public class MatchResponse {
    public MatchResponse(CompetitionMo competition, List<MatchMo> matches){
        this.setCompetition(competition);
        this.setMatches(matches);
    }

    private CompetitionMo competition;
    private List<MatchMo> matches;
}
