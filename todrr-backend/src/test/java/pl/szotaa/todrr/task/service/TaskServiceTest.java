package pl.szotaa.todrr.task.service;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import pl.szotaa.todrr.task.model.Task;
import pl.szotaa.todrr.task.repository.TaskRepository;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void save_repositoryGotCalled() {
        //given
        Task task = Task.builder()
                .id(1L)
                .name("exampleName")
                .description("exampleDescription")
                .build();

        //when
        taskService.save(task);

        //then
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test(expected = RuntimeException.class)
    public void findById_incorrectId_exceptionThrown() {
        //given
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        taskService.findById(1L);
    }

    @Test
    public void findById_correctId_taskReturned() {
        //given
        Task task = Task.builder()
                .id(1L)
                .name("exampleName")
                .description("exampleDescription")
                .build();

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        //when
        Task found = taskService.findById(1L);

        //then
        assertEquals(found, task);
    }

    @Test
    public void delete_correctId_repositoryGotCalled() {
        //when
        taskService.delete(1L);

        //then
        verify(taskRepository, times(1)).deleteById(anyLong());
    }
}
