package page.lamht.football.dto;

import lombok.Data;
import page.lamht.football.entity.Area;

import java.util.List;

@Data
public class MatchesDto {

    private Long count;
    private Object filters;
    private List<MatchDto> matches;
}
