package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserReadNewsPrimaryKey implements Serializable {

    @Column(name = "news_id")
    private Long newsId;

    @Column(name = "user_id")
    private Long userId;
}
