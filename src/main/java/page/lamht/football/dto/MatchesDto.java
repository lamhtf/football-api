package page.lamht.football.dto;

import lombok.Data;
import page.lamht.football.entity.Area;
import page.lamht.football.entity.Competition;

import java.util.List;

@Data
public class MatchesDto {

    private Long count;
    private Object filters;
    private Competition competition;
    private List<MatchDto> matches;
}
