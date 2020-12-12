package page.lamht.football.mo;

import lombok.Data;
import page.lamht.football.entity.Competition;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Data
public class StandingsResponse {

    public StandingsResponse(CompetitionMo competition, SeasonMo season, List<StandingsMo> standings, Timestamp lastUpdated){
        this.setCompetition(competition);
        this.setSeason(season);
        this.setStandings(standings);
        this.setLastUpdated(lastUpdated);
    };
//    FilterMo filter;
    CompetitionMo competition;
    SeasonMo season;
    List<StandingsMo> standings;
    Timestamp lastUpdated;
}