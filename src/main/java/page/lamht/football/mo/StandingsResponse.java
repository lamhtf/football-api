package page.lamht.football.mo;

import lombok.Data;
import page.lamht.football.entity.Competition;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Data
public class StandingsResponse {

    public StandingsResponse(List<StandingsMo> standings, Timestamp lastUpdated){
        this.setStandings(standings);
        this.setLastUpdated(lastUpdated);
    };
    List<StandingsMo> standings;
    Timestamp lastUpdated;
}