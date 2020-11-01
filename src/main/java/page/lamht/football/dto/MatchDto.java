package page.lamht.football.dto;

import lombok.Data;
import page.lamht.football.entity.Competition;
import page.lamht.football.entity.Referee;
import page.lamht.football.entity.Season;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
public class MatchDto {
    private Long id;
    private Competition competition;
    private Season season;
    private Timestamp utcDate;
    private String status;
    private String venue;
    private Integer matchday;
    private String stage;
    private String group;
    private Integer numberOfMatches;
    private Integer totalGoals;
    private Timestamp lastUpdated;
    private Object odds;
    private MatchScore score;
    private MatchTeam homeTeam;
    private MatchTeam awayTeam;
    private List<Referee> referees;


    @Data
    public static class MatchTeam {
        private Long id;
        private String name;
        private Long wins;
        private Long draws;
        private Long losses;
    }

    @Data
    public static class MatchHomeAway {
        private Integer homeTeam;
        private Integer awayTeam;
    }

    @Data
    public static class MatchScore {
        private String winner;
        private String duration;
        private MatchHomeAway fullTime;
        private MatchHomeAway halfTime;
        private MatchHomeAway extraTime;
        private MatchHomeAway penalties;
    }
}
