package pl.szotaa.todrr.task.controller;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.szotaa.todrr.task.exception.TaskNotFoundException;
import pl.szotaa.todrr.task.model.Task;
import pl.szotaa.todrr.task.service.TaskService;

/**
 * Rest controller resolving various Task related requests.
 *
 * @author szotaa
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
@PreAuthorize("isAuthenticated()")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody Task task){
        taskService.save(task);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Task>> getAllAccessibleTasks() {
        List<Task> tasks = taskService.findAllByCurrentlyAuthenticatedUser();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@taskMethodSecurityExpressions.isOwner(#id, authentication) or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Task> findById(@PathVariable long id) throws TaskNotFoundException {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@taskMethodSecurityExpressions.isOwner(#id, authentication) or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody Task task){
        task.setId(id);
        taskService.update(id, task);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@taskMethodSecurityExpressions.isOwner(#id, authentication) or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable long id){
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }
}
