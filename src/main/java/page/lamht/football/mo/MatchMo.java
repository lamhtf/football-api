package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MatchMo {
    private Long id;
    private SeasonMo season;
    private Timestamp utcDate;
    private String status;
    private Integer matchday;
    private String stage;
    private String group;
    private Timestamp lastUpdated;
    private ScoreMo score;
    private MatchTeamMo homeTeam;
    private MatchTeamMo awayTeam;
    private String refereeName;
//    private List<ReferenceMo> referees;

    @Data
    public static class ScoreMo {
        private String winner;
        private String duration;
        private TimeScoreMo fullTime;
        private TimeScoreMo halfTime;
        private TimeScoreMo extraTime;
        private TimeScoreMo penalties;
    }

    @Data
    public static class TimeScoreMo {
        private Integer homeTeam;
        private Integer awayTeam;

    }

    @Data
    public static class MatchTeamMo {
        private Long id;
        private String name;
    }
}

