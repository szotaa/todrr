package pl.szotaa.todrr.task.repository;

import java.time.Instant;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.szotaa.todrr.task.model.Task;
import pl.szotaa.todrr.user.model.Role;
import pl.szotaa.todrr.user.model.User;
import pl.szotaa.todrr.user.repository.UserRepository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveTaskEntity_creationTimestampAppended() {
        //given
        Task task = Task.builder()
                .name("exampleName")
                .description("exampleDescription")
                .build();

        //when
        taskRepository.save(task);
        taskRepository.flush();

        //then
        assertNotNull(task.getCreationTimestamp());
    }

    @Test
    public void updateSavedEntity_lastModifiedTimestampValueUpdated() {
        //given
        Task task = Task.builder()
                .name("exampleName")
                .description("exampleDescription")
                .build();

        entityManager.persistAndFlush(task);
        long id = task.getId();

        Instant beforeUpdate = entityManager.find(Task.class, id).getLastModified();

        //when
        task.setDescription("updatedDescription");
        taskRepository.saveAndFlush(task);

        //then
        assertNotEquals(beforeUpdate, task.getLastModified());
    }

    @Test
    public void findAllByOwnerId_correctListReturned(){
        //given
        User user = User.builder()
                .email("user@email.com")
                .password("password")
                .isEnabled(true)
                .role(Role.ROLE_USER)
                .build();

        Task task1 = Task.builder()
                .name("task1Name")
                .description("task1Desc")
                .owner(user)
                .build();

        Task task2 = Task.builder()
                .name("task2Name")
                .description("task2Desc")
                .owner(user)
                .build();

        Task task3 = Task.builder()
                .name("task3Name")
                .description("task3Desc")
                .owner(user)
                .build();

        long id = entityManager.persistAndGetId(user, Long.class);
        entityManager.persist(task1);
        entityManager.persist(task2);
        entityManager.persist(task3);
        entityManager.flush();

        //when
        List<Task> tasks = taskRepository.findAllByOwnerId(id);

        //then
        assertEquals(3, tasks.size());
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
        assertTrue(tasks.contains(task3));
    }
}