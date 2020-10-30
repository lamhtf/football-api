package page.lamht.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper=false)
@Data
@Table("area")
public class Area extends Entity {

    private String name;
    private String countryCode;
    private String ensignUrl;
    private Integer parentAreaId;
    private String parentArea;

}
