import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    public void testTaskEqualityById() {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        task1.setId(1);
        task2.setId(1);
        assertEquals(task1, task2);
    }

    @Test
    public void testTaskSubclassEqualityById() {
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", 1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", 1);
        assertEquals(subtask1, subtask2);

        Epic epic1 = new Epic("Epic 1", "Description 1");
        Epic epic2 = new Epic("Epic 2", "Description 2");
        epic1.setId(1);
        epic2.setId(1);
        assertEquals(epic1, epic2);
    }

    @Test
    public void testSubtaskSetSelfAsParentEpic() {
        TaskManager inMemoryTaskManager = Manager.getDefaultTaskManager();
        Subtask subtask = new Subtask("Subtask 1", "Description 1", 1);
        inMemoryTaskManager.addSubtask(subtask);

        assertEquals(1, subtask.getParentEpic());
    }

    @Test
    public void testGetDefaultTaskManager() {
        TaskManager taskManager = Manager.getDefaultTaskManager();
        assertNotNull(taskManager);
    }

    @Test
    public void testDefaultHistoryManager() {
        HistoryManager historyManager = Manager.getDefaultHistoryManager();
        assertNotNull(historyManager);
    }

    @Test
    public void testInMemoryTaskManagerAddAndGetTask() {
        TaskManager taskManager = Manager.getDefaultTaskManager();
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        assertEquals(task1, taskManager.getTaskById(task1.getId()));
        assertEquals(task2, taskManager.getTaskById(task2.getId()));
    }

    @Test
    public void testTaskIdConflict() {
        TaskManager taskManager = Manager.getDefaultTaskManager();
        Task task1 = new Task("Task 1", "Description 1");
        task1.setId(1);
        taskManager.createTask(task1);

        Task task2 = new Task("Task 2", "Description 2");
        taskManager.createTask(task2);

        assertEquals(2, taskManager.getAllTasks().size());
    }

    @Test
    public void testHistoryManagerAddTask() {
        HistoryManager historyManager = Manager.getDefaultHistoryManager();
        Task task = new Task("Task", "Description");
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size());
    }

}