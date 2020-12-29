package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class CompetitionResponse {
    public CompetitionResponse(List<CompetitionMo> competitions, Timestamp lastUpdated){
        this.setCompetitions(competitions);
        this.setLastUpdated(lastUpdated);
    }
    List<CompetitionMo> competitions;
    Timestamp lastUpdated;
}
