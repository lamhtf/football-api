package page.lamht.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper=false)
@Data
@Table("competition")
public class Competition extends Entity {

    private Area area;
    private String name;
    private String code;
    private String emblemUrl;
    private String plan;
    @Transient
    private Season currentSeason;
    private String parentArea;
    private Integer numberOfAvailableSeasons;
    @Transient
    private Timestamp lastUpdated;

}