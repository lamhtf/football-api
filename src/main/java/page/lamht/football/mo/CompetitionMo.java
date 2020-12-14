package page.lamht.football.mo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CompetitionMo {
    private Long id;
    private AreaMo area;
    private String name;
    private String code;
    private String emblemUrl;
    private String plan;
    private SeasonMo currentSeason;
    private Integer numberOfAvailableSeasons;
    private Timestamp lastUpdated;
}
