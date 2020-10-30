package page.lamht.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper=false)
@Data
@Table("area")
public class Season extends Entity {

    private String startDate;
    private String endDate;
    private String currentMatchday;
    private String winner;

}