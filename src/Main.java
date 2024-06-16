import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Создаем менеджер задач
        HistoryManager historyManager = new InMemoryHistoryManager();
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);

        // Создаем задачи
        Task task1 = new Task("Task 1", "Description 1", Duration.ofHours(2), LocalDateTime.of(2024, 6, 15, 10, 0));
        Task task2 = new Task("Task 2", "Description 2", Duration.ofHours(3), LocalDateTime.of(2024, 6, 15, 8, 0));
        Task task3 = new Task("Task 3", "Description 3", Duration.ofHours(1), LocalDateTime.of(2024, 6, 15, 12, 0));
        Epic epic1 = new Epic("Epic 1", "Description 1", Duration.ofHours(4), LocalDateTime.of(2024, 6, 15, 14, 0));
        // Добавляем задачи в менеджер
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        taskManager.addEpic(epic1);

        // Получаем список всех задач
        List<Task> allTasks = taskManager.getAllTasks();
        System.out.println("Список всех задач:");
        for (Task task : allTasks) {
            System.out.println(task);
        }
        System.out.println(epic1);

        // Получаем отсортированный список приоритетных задач
        List<Task> prioritizedTasks = taskManager.getPrioritizedTasks();
        System.out.println("\nОтсортированный список приоритетных задач:");
        for (Task task : prioritizedTasks) {
            System.out.println(task);
        }

        // Добавляем подзадачу к задаче
        Subtask subtask1 = new Subtask("Subtask 1", "Subtask of Epic 1", epic1.getId(), Duration.ofMinutes(30), LocalDateTime.of(2024, 6, 15, 11, 0));
        taskManager.addSubtask(subtask1);
        epic1.addSubtask(subtask1);

        // Обновляем задачу
        task1.setDescription("Updated description of Epic 1");
        taskManager.updateEpic(epic1);

        // Получаем обновленный список приоритетных задач
        prioritizedTasks = taskManager.getPrioritizedTasks();
        System.out.println("\nОбновленный отсортированный список приоритетных задач:");
        for (Task task : prioritizedTasks) {
            System.out.println(task);
        }
        System.out.println(epic1);
    }
}
