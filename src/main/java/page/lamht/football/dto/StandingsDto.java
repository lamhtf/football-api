package page.lamht.football.dto;

import lombok.Data;
import page.lamht.football.entity.Area;

import java.util.List;

@Data
class StandingTeam {
    private Long id;
    private String name;
    private String crestUrl;
}

@Data
class StandingTable {
    private Integer position;
    private String form;
    private Integer won;
    private Integer draw;
    private Integer lost;
    private Integer points;
    private Integer goalsFor;
    private Integer goalsAgainst;
    private Integer goalsDifference;
}

@Data
public class StandingsDto {

    private String stage;
    private String type;
    private String group;
    private List<StandingTable> table;
}
