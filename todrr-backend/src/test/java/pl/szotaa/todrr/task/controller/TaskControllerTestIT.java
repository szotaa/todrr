package pl.szotaa.todrr.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.szotaa.todrr.task.model.Task;
import pl.szotaa.todrr.task.service.TaskService;


import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private TaskController taskController;

    @MockBean
    private TaskService taskService;

    @Test
    @WithMockUser
    public void save_201created() throws Exception {
        //given
        Task task = Task.builder()
                .name("exampleName")
                .description("exampleDescription")
                .build();

        //when&then
        mockMvc.perform(post("/api/task")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(task))
            .with(csrf()))
                .andExpect(status().isCreated());

        verify(taskService, times(1)).save(any(Task.class));
    }

    @Test
    @WithMockUser
    public void findById_200ok() throws Exception {
        //given
        Task task = Task.builder()
                .name("exampleName")
                .description("exampleDescription")
                .build();

        when(taskService.findById(anyLong())).thenReturn(task);

        //when&then
        mockMvc.perform(get("/api/task/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", Is.is(task.getName())))
                .andExpect(jsonPath("$.description", Is.is(task.getDescription())));

        verify(taskService, times(1)).findById(anyLong());
    }

    @Test
    @WithMockUser
    public void update_200ok() throws Exception {
        //given
        Task task = Task.builder()
                .name("exampleName")
                .description("exampleDescription")
                .build();

        //when&then
        mockMvc.perform(put("/api/task/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(task))
                .with(csrf()))
                    .andExpect(status().isOk());

        verify(taskService, times(1)).update(anyLong(), any(Task.class));
    }

    @Test
    @WithMockUser
    public void delete_200ok() throws Exception {
        mockMvc.perform(delete("/api/task/{id}", 1L)
                .with(csrf()))
                    .andExpect(status().isOk());

        verify(taskService, times(1)).delete(anyLong());
    }

    private static String asJsonString(Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }
}