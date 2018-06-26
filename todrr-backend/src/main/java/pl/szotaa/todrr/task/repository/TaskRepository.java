package pl.szotaa.todrr.task.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.szotaa.todrr.task.model.Task;

/**
 * Spring's Data JPA repository providing Task's persistence functionality.
 *
 * @author szotaa
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByOwnerId(Long ownerId);
}
