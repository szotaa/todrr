package pl.szotaa.todrr.task.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.szotaa.todrr.task.exception.TaskNotFoundException;
import pl.szotaa.todrr.task.service.TaskService;

/**
 * Methods used in @PreAuthorize annotations used in TaskController
 *
 * @author szotaa
 */

@Component
@RequiredArgsConstructor
public class TaskMethodSecurityExpressions {

    private final TaskService taskService;

    /**
     * Checks if user requesting a task is indeed its owner and therefore has permission to read/modify it.
     *
     * @param taskId Requested task id.
     * @param authentication Spring's Security authentication object.
     */

    public boolean isOwner(Long taskId, Authentication authentication) throws TaskNotFoundException {
        return taskService.findById(taskId).getOwner().getEmail().equals(authentication.getName());
    }
}
