import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    protected abstract T createTaskManager();

    @BeforeEach
    public void setup() {
        taskManager = createTaskManager();
    }

    @Test
    void testCreateAndGetTask() {
        Task task = new Task("Task 1", "Description 1", Duration.ofMinutes(30), LocalDateTime.now());
        taskManager.createTask(task);

        Task retrievedTask = taskManager.getTaskById(task.getId());
        assertNotNull(retrievedTask);
        assertEquals(task.getTitle(), retrievedTask.getTitle());
        assertEquals(task.getDescription(), retrievedTask.getDescription());
    }

    @Test
    void testUpdateTask() {
        Task task = new Task("Task 2", "Description 2", Duration.ofMinutes(30), LocalDateTime.now());
        taskManager.createTask(task);

        task.setTitle("Updated Task 2");
        taskManager.updateTask(task);

        Task retrievedTask = taskManager.getTaskById(task.getId());
        assertNotNull(retrievedTask);
        assertEquals("Updated Task 2", retrievedTask.getTitle());
    }

    @Test
    void testDeleteTask() {
        Task task = new Task("Task 3", "Description 3", Duration.ofMinutes(30), LocalDateTime.now());
        taskManager.createTask(task);

        taskManager.deleteTask(task.getId());

        Task retrievedTask = taskManager.getTaskById(task.getId());
        assertNull(retrievedTask);
    }

    @Test
    void testAddAndGetEpic() {
        Epic epic = new Epic("Epic 1", "Epic Description 1");
        taskManager.addEpic(epic);

        Epic retrievedEpic = taskManager.getEpicById(epic.getId());
        assertNotNull(retrievedEpic);
        assertEquals("Epic 1", retrievedEpic.getTitle());
    }

    @Test
    void testUpdateEpic() {
        Epic epic = new Epic("Epic 2", "Epic Description 2");
        taskManager.addEpic(epic);

        epic.setTitle("Updated Epic 2");
        taskManager.updateEpic(epic);

        Epic retrievedEpic = taskManager.getEpicById(epic.getId());
        assertNotNull(retrievedEpic);
        assertEquals("Updated Epic 2", retrievedEpic.getTitle());
    }

    @Test
    void testDeleteEpic() {
        Epic epic = new Epic("Epic 3", "Epic Description 3");
        taskManager.addEpic(epic);

        taskManager.deleteEpic(epic.getId());

        Epic retrievedEpic = taskManager.getEpicById(epic.getId());
        assertNull(retrievedEpic);
    }

    @Test
    void testAddAndGetSubtask() {
        Epic epic = new Epic("Epic 4", "Epic Description 4");
        taskManager.addEpic(epic);

        Subtask subtask = new Subtask("Subtask 1", "Subtask Description 1", epic.getId(), Duration.ofMinutes(30), LocalDateTime.now());
        taskManager.addSubtask(subtask);

        Subtask retrievedSubtask = taskManager.getSubtaskById(subtask.getId());
        assertNotNull(retrievedSubtask);
        assertEquals("Subtask 1", retrievedSubtask.getTitle());
    }

    @Test
    void testUpdateSubtask() {
        Epic epic = new Epic("Epic 5", "Epic Description 5");
        taskManager.addEpic(epic);

        Subtask subtask = new Subtask("Subtask 2", "Subtask Description 2", epic.getId(), Duration.ofMinutes(30), LocalDateTime.now());
        taskManager.addSubtask(subtask);

        subtask.setTitle("Updated Subtask 2");
        taskManager.updateSubtask(subtask);

        Subtask retrievedSubtask = taskManager.getSubtaskById(subtask.getId());
        assertNotNull(retrievedSubtask);
        assertEquals("Updated Subtask 2", retrievedSubtask.getTitle());
    }

    @Test
    void testDeleteSubtask() {
        Epic epic = new Epic("Epic 6", "Epic Description 6");
        taskManager.addEpic(epic);

        Subtask subtask = new Subtask("Subtask 3", "Subtask Description 3", epic.getId(), Duration.ofMinutes(30), LocalDateTime.now());
        taskManager.addSubtask(subtask);

        taskManager.deleteSubtask(subtask.getId());

        Subtask retrievedSubtask = taskManager.getSubtaskById(subtask.getId());
        assertNull(retrievedSubtask);
    }

}
