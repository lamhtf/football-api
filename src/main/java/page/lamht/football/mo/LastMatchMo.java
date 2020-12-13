package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class LastMatchMo {
    private Long id;
    private Integer matchday;
    private Timestamp utcDate;
    private LastMatchTeam homeTeam;
    private LastMatchTeam awayTeam;
    private String winner;

    @Data
    public static class LastMatchTeam {
        private Long id;
        private String name;
        private Integer score;
        private String crestUrl;
    }

}