package page.lamht.football.entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper=false)
@Data
@Table("app_info")
public class AppInfo {

    private String os;
    private String version;
    private String url;
    private Timestamp created;
    private Timestamp updated;
}
