package pl.szotaa.todrr.task.model;

import java.time.Instant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@JsonTest
@RunWith(SpringRunner.class)
public class TaskJsonIT {

    @Autowired
    private JacksonTester<Task> jacksonTester;

    @Test
    public void serialize_allFieldsProvided_jsonProperlySerialized() throws Exception {
        //given
        Instant now = Instant.now();

        Task task = Task.builder()
                .id(1L)
                .name("name")
                .description("description")
                .creationTimestamp(now)
                .lastModified(now)
                .build();

        //when
        JsonContent<Task> jsonTask = jacksonTester.write(task);

        //then
        assertThat(jsonTask).hasJsonPathValue("@.id");
        assertThat(jsonTask).hasJsonPathValue("@.name");
        assertThat(jsonTask).hasJsonPathValue("@.description");
        assertThat(jsonTask).hasJsonPathValue("@.creationTimestamp");
        assertThat(jsonTask).hasJsonPathValue("@.lastModified");

    }


    @Test
    public void deserialize_allFieldsProvided_jsonProperlyDeserialized() throws Exception {
        //given
        String jsonTask = "{\n" +
                "  \"id\": \"1\",\n" +
                "  \"name\": \"name\",\n" +
                "  \"description\": \"description\",\n" +
                "  \"creationTimestamp\": \"11111\",\n" +
                "  \"lastModified\": \"111111\"\n" +
                "}";

        //when
        Task task = jacksonTester.parseObject(jsonTask);

        //then
        assertEquals(1L, task.getId(), 0);
        assertNotNull(task.getName());
        assertNotNull(task.getDescription());
        assertNotNull(task.getCreationTimestamp());
        assertNotNull(task.getLastModified());
    }
}