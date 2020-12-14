package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class StatisticsResponse {

    public StatisticsResponse(List<ScorerMo> scorers, Timestamp lastUpdated){
        this.setScorers(scorers);
        this.setLastUpdated(lastUpdated);
    };
    List<ScorerMo> scorers;
    Timestamp lastUpdated;
}