package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class CompetitionResponse {
    public CompetitionResponse(List<CompetitionMo> completitions, Timestamp lastUpdated){
        this.setCompletitions(completitions);
        this.setLastUpdated(lastUpdated);
    }
    List<CompetitionMo> completitions;
    Timestamp lastUpdated;
}
