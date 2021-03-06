package pl.szotaa.todrr.task.service;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.szotaa.todrr.task.exception.TaskNotFoundException;
import pl.szotaa.todrr.task.model.Task;
import pl.szotaa.todrr.task.repository.TaskRepository;
import pl.szotaa.todrr.user.model.User;

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
        task.setOwner(getOwnerFromSecurityContext());
        taskRepository.save(task);
    }

    public Task findById(Long id) throws TaskNotFoundException {
        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    public List<Task> findAllByCurrentlyAuthenticatedUser(){
        User user = getOwnerFromSecurityContext();
        if(user != null){
            return taskRepository.findAllByOwnerId(user.getId());
        }
        return Collections.emptyList();
    }

    public void update(Long id, Task updated){
        updated.setId(id);
        taskRepository.save(updated);
    }

    public void delete(Long id){
        taskRepository.deleteById(id);
    }

    private User getOwnerFromSecurityContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            return (User) authentication.getPrincipal();
        }
        return null;
    }
}
