package page.lamht.football.mo;

import lombok.Data;

@Data
public class ScorerMo {
    private Long id;
    private PlayerMo player;
    private ScorerTeamMo team;
    private Integer numberOfGoals;

    @Data
    public static class ScorerTeamMo{
        private Long id;
        private String name;
        private String crestUrl;
    }
}