package page.lamht.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper=false)
@Data
@Table("match")
public class Match extends Entity {

    private Timestamp utcDate;
    private String status;
    private String venue;
    private Integer matchday;
    private String stage;
    private String group;
    private Timestamp last_updated;
    @Transient
    private String numberOfMatches;
    private Integer totalGoals;
    private Long homeTeamId;
    private String homeTeamName;
    private Integer homeTeamWins;
    private Integer homeTeamDraws;
    private Integer homeTeamLosses;
    private String homeTeamCrestUrl;
    private Long awayTeamId;
    private String awayTeamName;
    private Integer awayTeamWins;
    private Integer awayTeamDraws;
    private Integer awayTeamLosses;
    private String awayTeamCrestUrl;
    private String winner;
    private String duration;
    private Integer fullTimeHomeTeam;
    private Integer fullTimeAwayTeam;
    private Integer halfTimeHomeTeam;
    private Integer halfTimeAwayTeam;
    private Integer extraTimeHomeTeam;
    private Integer extraTimeAwayTeam;
    private Integer penaltiesHomeTeam;
    private Integer penaltiesAwayTeam;

    @MappedCollection(idColumn = "id")
    private Referee referee;
    private Long refereeId;
    private String refereeName;

    @MappedCollection(idColumn = "id")
    private Competition competition;
    private Long competitionId;

    @MappedCollection(idColumn = "id")
    private Season season;
    private Long seasonId;

}


