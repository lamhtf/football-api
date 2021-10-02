package page.lamht.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
// non persistent object
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
@Table("standing")
public class Standing implements Serializable {

    public Standing(Long teamId, String teamName, String shortName, String teamCrestUrl){
        this.setTeamId(teamId);
        this.setTeamName(teamName);
        this.setShortName(shortName);
        this.setTeamCrestUrl(teamCrestUrl);
    }

    @MappedCollection(idColumn = "id")
    private Standings standings;
    private Long standingsId;

    private Long teamId;
    private String teamName;
    private String teamCrestUrl;
    private String shortName;

    private Integer position;
    private Integer playedGames;
    private String form;

    private Integer won;
    private Integer draw;
    private Integer lost;
    private Integer points;
    private Integer goalsFor;
    private Integer goalsAgainst;
    private Integer goalDifference;
}