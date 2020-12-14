package page.lamht.football.dto;

import lombok.Data;
import page.lamht.football.entity.Area;
import page.lamht.football.entity.Competition;
import page.lamht.football.entity.Scorer;
import page.lamht.football.entity.Season;

import java.util.List;

@Data
public class ScorerDto {

    private Long count;
    private Object filters;
    private Competition competition;
    private Season season;
    private List<Scorer> scorers;
}
