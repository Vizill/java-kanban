import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    protected InMemoryTaskManager createTaskManager() {
        return new InMemoryTaskManager(new InMemoryHistoryManager());
    }

    @BeforeEach
    void setUp() {
        taskManager = createTaskManager();
    }

    @Test
    void testValidateEpicTime_NoOverlap() {
        Epic epic1 = new Epic("Epic 1", "Description 1");
        Epic epic2 = new Epic("Epic 2", "Description 2");
        epic1.setStartTime(LocalDateTime.of(2024, 6, 20, 10, 0));
        epic1.setDuration(Duration.ofHours(2));
        epic2.setStartTime(LocalDateTime.of(2024, 6, 20, 14, 0));
        epic2.setDuration(Duration.ofHours(3));

        taskManager.addEpic(epic1);
        assertTrue(taskManager.validateTaskTime(epic2));
    }

    @Test
    void testValidateEpicTime_Overlap() {
        Epic epic1 = new Epic("Epic 1", "Description 1");
        Epic epic2 = new Epic("Epic 2", "Description 2");
        epic1.setStartTime(LocalDateTime.of(2024, 6, 20, 10, 0));
        epic1.setDuration(Duration.ofHours(3));
        epic2.setStartTime(LocalDateTime.of(2024, 6, 20, 12, 0));
        epic2.setDuration(Duration.ofHours(2));

        taskManager.addEpic(epic1);
        boolean isValid = taskManager.validateTaskTime(epic2);

        assertTrue(isValid);
    }
}
