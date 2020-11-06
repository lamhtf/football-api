package page.lamht.football.dto;

import lombok.Data;
import page.lamht.football.entity.Competition;
import page.lamht.football.entity.Season;

@Data
public class StandingDto {
    private Object filter;
    private Competition competition;
    private Season season;
    private StandingsDto standings;
}