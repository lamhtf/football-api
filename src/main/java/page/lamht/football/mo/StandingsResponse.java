package page.lamht.football.mo;

import lombok.Data;
import page.lamht.football.entity.Competition;

import java.util.List;

@Data
public class StandingsResponse {

    public StandingsResponse(CompetitionMo competition, SeasonMo season, List<StandingsMo> standings){
        this.setCompetition(competition);
        this.setSeason(season);
        this.setStandings(standings);
    };
//    FilterMo filter;
    CompetitionMo competition;
    SeasonMo season;
    List<StandingsMo> standings;
}