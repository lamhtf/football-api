package page.lamht.football.mo;

import lombok.Data;

@Data
public class SeasonMo {
    private Long id;
    private String startDate;
    private String endDate;
    private Integer currentMatchday;
}
