package page.lamht.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

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
