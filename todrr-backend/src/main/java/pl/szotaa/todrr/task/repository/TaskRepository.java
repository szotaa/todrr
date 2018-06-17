package pl.szotaa.todrr.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.szotaa.todrr.task.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
