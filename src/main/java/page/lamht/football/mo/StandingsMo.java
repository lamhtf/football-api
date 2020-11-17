package page.lamht.football.mo;

import lombok.Data;

import java.util.List;

@Data
public class StandingsMo {

    private String stage;
    private String type;
    private String group;
    private List<StandingMo> standings;

    @Data
    static class StandingMo {
        private Long teamId;
        private String teamName;
        private String teamCrestUrl;

        private Integer playedGames;
        private String form;

        private Integer won;
        private Integer draw;
        private Integer lost;
        private Integer point;
        private Integer goalsFor;
        private Integer goalsAgainst;
        private Integer goalsDifference;
    }

}
