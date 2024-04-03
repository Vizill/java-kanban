public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", 1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", 1);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", 2);

        epic1.getSubtasks().add(subtask1);
        epic2.getSubtasks().add(subtask2);
        epic2.getSubtasks().add(subtask3);

        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(epic1);
        taskManager.createTask(epic2);
        taskManager.createTask(subtask1);
        taskManager.createTask(subtask2);
        taskManager.createTask(subtask3);

        taskManager.getAllTasks();

        taskManager.updateTask(5, "Подзадача 1 изм", "Описание подзадачи 3 изм", TaskStatus.NEW);

        taskManager.getTaskById(5);

        taskManager.deleteTask(5);

        task1.setStatus(TaskStatus.IN_PROGRESS);
        task2.setStatus(TaskStatus.DONE);
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        subtask2.setStatus(TaskStatus.DONE);
        subtask3.setStatus(TaskStatus.DONE);

        taskManager.getAllTasks();
    }
}
