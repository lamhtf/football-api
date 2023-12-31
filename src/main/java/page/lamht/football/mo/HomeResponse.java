package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class HomeResponse {

    public HomeResponse(StandingsMo.StandingMo standing, LastMatchMo lastMatch, MatchMo inPlayMatch, NextMatchMo nextMatch, Timestamp lastUpdated) {
        this.setStanding(standing);
        this.setLastMatch(lastMatch);
        this.setInPlayMatch(inPlayMatch);
        this.setNextMatch(nextMatch);
        this.setLastUpdated(lastUpdated);
    }

    StandingsMo.StandingMo standing;
    LastMatchMo lastMatch;
    MatchMo inPlayMatch;
    NextMatchMo nextMatch;
    Timestamp lastUpdated;
}