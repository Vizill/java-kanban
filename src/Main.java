public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);


        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", 3);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", 3);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", 4);
        Subtask subtask4 = new Subtask("Подзадача 4", "Описание подзадачи 4", 4);

        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);
        taskManager.addSubtask(subtask4);

        System.out.println("\nСписок задач:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task.getTitle() + ": " + task.getStatus());
        }
        System.out.println("Список эпиков:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic.getTitle() + ": " + epic.getStatus());
        }
        System.out.println("\nСписок подзадач:");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask.getTitle() + ": " + subtask.getStatus());
        }

        taskManager.getEpicById(4);

        taskManager.deleteTask(5);

        task1.setStatus(TaskStatus.IN_PROGRESS);
        task2.setStatus(TaskStatus.DONE);
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        subtask2.setStatus(TaskStatus.DONE);
        subtask3.setStatus(TaskStatus.DONE);
        subtask4.setStatus(TaskStatus.DONE);

        taskManager.updateTask(task1);
        taskManager.updateEpicStatus(3);
        taskManager.updateEpicStatus(4);

        System.out.println("\nОбновленные статусы:");
        System.out.println("Статус задачи 1: " + task1.getStatus());
        System.out.println("Статус подзадачи 1: " + subtask1.getStatus());
        System.out.println("Статус подзадачи 2: " + subtask2.getStatus());
        System.out.println("Статус подзадачи 3: " + subtask3.getStatus());
        System.out.println("Статус подзадачи 4: " + subtask4.getStatus());
        System.out.println("Статус эпика 1: " + epic1.getStatus());
        System.out.println("Статус эпика 2: " + epic2.getStatus());

        taskManager.deleteTask(task1.getId());
        taskManager.deleteEpic(epic2.getId());

        System.out.println("\nСписок задач после удаления:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task.getTitle() + ": " + task.getStatus());
        }

        System.out.println("\nСписок эпиков после удаления:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic.getTitle() + ": " + epic.getStatus());
        }
    }
}
