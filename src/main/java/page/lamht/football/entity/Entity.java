package page.lamht.football.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Entity implements Serializable, Persistable {

    @Id
    public Long id;

    private Timestamp created = new Timestamp(System.currentTimeMillis());

    @Transient
    public boolean isNew = false;
}
