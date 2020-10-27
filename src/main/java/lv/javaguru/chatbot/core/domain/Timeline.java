package lv.javaguru.chatbot.core.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "timeline")
@Table(name = "timeline")
@IdClass(BaseEntity.class)
public class Timeline extends BaseEntity {

    @Id
    private Long id;

    @OneToOne (optional=false, mappedBy="creationDate")
    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    public Timeline() {
    }

    public Timeline(String title) {
        this.title = title;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timeline)) return false;
        if (!super.equals(o)) return false;
        Timeline timeline = (Timeline) o;
        return id.equals(timeline.id) &&
                creationDate.equals(timeline.creationDate) &&
                title.equals(timeline.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, creationDate, title);
    }
}
