package pl.szotaa.todrr.task.repository;

import java.time.Instant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.szotaa.todrr.task.model.Task;


import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

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
}