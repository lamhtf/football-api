package page.lamht.football.dto;

import lombok.Data;
import page.lamht.football.entity.Competition;

import java.util.List;

@Data
public class CompetitionDto {

    private Long count;
    private Object filters;
    private List<Competition> competitions;
}
