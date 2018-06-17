package pl.szotaa.todrr.task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szotaa.todrr.task.model.Task;
import pl.szotaa.todrr.task.repository.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public void add(Task task){
        taskRepository.save(task);
    }

    public Task findById(long id){
        return taskRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void update(long id, Task updated){
        //TODO: implement update
    }

    public void delete(long id){
        taskRepository.deleteById(id);
    }
}
