package pl.szotaa.todrr.task.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Entity class representing task desired to be done by user;
 *
 * @author szotaa
 */

@Data
@Entity
@Builder
@Table(name = "tasks")
public class Task {

    @NotEmpty
    private String name;

    @NotNull
    private String description;

    @CreationTimestamp
    private Instant creationTimestamp;

    @UpdateTimestamp
    private Instant lastModified;
}
