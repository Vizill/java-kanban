import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    protected abstract T createTaskManager();

    @BeforeEach
    void setUp() {
        taskManager = createTaskManager();
    }

    @AfterEach
    void tearDown() {
        taskManager = null;
    }

    @Test
    void testCreateAndGetTask() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.createTask(task);

        Task retrievedTask = taskManager.getTaskById(task.getId());
        assertEquals(task.getTitle(), retrievedTask.getTitle());
        assertEquals(task.getDescription(), retrievedTask.getDescription());
    }

    @Test
    void testUpdateTask() {
        Task task = new Task("Task 2", "Description 2");
        taskManager.createTask(task);

        task.setTitle("Updated Task 2");
        taskManager.updateTask(task);

        Task retrievedTask = taskManager.getTaskById(task.getId());
        assertEquals("Updated Task 2", retrievedTask.getTitle());
    }

    @Test
    void testDeleteTask() {
        Task task = new Task("Task 3", "Description 3");
        taskManager.createTask(task);

        taskManager.deleteTask(task.getId());

        Task retrievedTask = taskManager.getTaskById(task.getId());
        assertNull(retrievedTask);
    }
    }