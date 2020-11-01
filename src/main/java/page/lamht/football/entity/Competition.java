package page.lamht.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper=false)
@Data
@Table("competition")
public class Competition extends Entity {

    @MappedCollection(idColumn = "id")
    private Area area;
    private Long areaId;

    private String name;
    private String code;
    private String emblemUrl;
    private String plan;

    @MappedCollection(idColumn = "id")
    private Season currentSeason;
    private Long currentSeasonId;

    @MappedCollection(idColumn = "competition_id")
    private CompetitionTeam competitionTeam;

    private Integer numberOfAvailableSeasons;
    private Timestamp lastUpdated;

}