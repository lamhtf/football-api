package page.lamht.football.dto;

import lombok.Data;
import page.lamht.football.entity.Competition;
import page.lamht.football.entity.Season;
import page.lamht.football.entity.Team;

import java.util.List;

@Data
public class LeagueDto {
    private Long count;
    private Object filter;
    private Competition competition;
    private Season season;
    private List<Team> teams;
}
