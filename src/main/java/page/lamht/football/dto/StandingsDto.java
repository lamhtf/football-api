package page.lamht.football.dto;

import lombok.Data;

import java.util.List;

@Data
public class StandingsDto {
    private String stage;
    private String type;
    private String group;
    private List<StandingStatistic> table;

    @Data
    public static class StandingTeam {
        private Long id;
        private String name;
        private String crestUrl;
    }

    @Data
    public static class StandingStatistic {
        private Integer position;
        private StandingTeam team;
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

}
