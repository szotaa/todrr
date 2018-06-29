package pl.szotaa.todrr.task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pl.szotaa.todrr.user.model.User;

/**
 * Entity class representing task desired to be done by user;
 *
 * @author szotaa
 */

@Data
@Entity
@Builder
@Table(name = "tasks")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "owner_id")
    private User owner;

    @CreationTimestamp
    private Instant creationTimestamp;

    @UpdateTimestamp
    private Instant lastModified;
}
