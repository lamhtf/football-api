package page.lamht.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper=false)
@Data
@Table("season")
public class Season extends Entity {

    private String startDate;
    private String endDate;
    private Integer currentMatchday;

    @MappedCollection(idColumn = "id")
    private Team winner;
    @Transient
    private Long winnerId;
    private String winnerName;

}