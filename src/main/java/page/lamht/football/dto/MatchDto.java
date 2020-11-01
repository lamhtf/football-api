package page.lamht.football.dto;

import lombok.Data;
import page.lamht.football.entity.Competition;
import page.lamht.football.entity.Referee;
import page.lamht.football.entity.Season;

import java.sql.Timestamp;
import java.util.Set;

@Data
class MatchTeam {
    private Long id;
    private String name;
}

@Data
class MatchHomeAway {
    private Integer homeTeam;
    private Integer awayTeam;
}

@Data
class MatchScore {
    private String winner;
    private String duration;
    private MatchHomeAway fullTime;
    private MatchHomeAway halfTime;
    private MatchHomeAway extraTime;
    private MatchHomeAway penalties;
}

@Data
public class MatchDto {
    private Long id;
    private Competition competition;
    private Season season;
    private Timestamp utcDate;
    private MatchScore score;
    private MatchTeam homeTeam;
    private MatchTeam awayTeam;
    private Set<Referee> referees;
}
