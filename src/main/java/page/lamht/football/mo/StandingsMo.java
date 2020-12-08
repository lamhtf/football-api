package page.lamht.football.mo;

import lombok.Data;

import java.util.List;

@Data
public class StandingsMo {

    private String stage;
    private String type;
    private String group;
    private List<StandingMo> table;

    @Data
    public static class StandingMo {
        private Integer position;
        private StandingTeamMo team;
        private Integer playedGames;
        private String form;

        private Integer won;
        private Integer draw;
        private Integer lost;
        private Integer points;
        private Integer goalsFor;
        private Integer goalsAgainst;
        private Integer goalDifference;
    }

    @Data
    public static class StandingTeamMo {
        private Long id;
        private String name;
        private String crestUrl;
    }

}
