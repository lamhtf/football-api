package page.lamht.football.mo;

import lombok.Data;

@Data
public class FavouriteMo {

    private Integer competitionId;
    private String code;
    private String competitionName;
    private Integer teamId;
    private String shortName;
    private String crestUrl;
    private String clubColors;

}
