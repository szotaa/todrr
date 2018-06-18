package pl.szotaa.todrr.task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szotaa.todrr.task.exception.TaskNotFoundException;
import pl.szotaa.todrr.task.model.Task;
import pl.szotaa.todrr.task.repository.TaskRepository;

/**
 * Task service providing CRUD functionality for tasks.
 *
 * @author szotaa
 */

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public void save(Task task){
        taskRepository.save(task);
    }

    public Task findById(long id){
        return taskRepository.findById(id).<TaskNotFoundException>orElseThrow(() -> {throw new TaskNotFoundException(id);});
    }

    public void update(long id, Task updated){
        //TODO: implement update
    }

    public void delete(long id){
        taskRepository.deleteById(id);
    }
}
