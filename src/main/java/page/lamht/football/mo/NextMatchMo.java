package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class NextMatchMo {
    private Long id;
    private Integer matchday;
    private Timestamp utcDate;
    private NextMatchTeam homeTeam;
    private NextMatchTeam awayTeam;

    @Data
    public static class NextMatchTeam {
        private Long id;
        private String name;
    }

}