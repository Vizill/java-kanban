public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");

        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");

        inMemoryTaskManager.addEpic(epic1);
        inMemoryTaskManager.addEpic(epic2);


        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", 3);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", 3);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", 4);
        Subtask subtask4 = new Subtask("Подзадача 4", "Описание подзадачи 4", 4);

        inMemoryTaskManager.addSubtask(subtask1);
        inMemoryTaskManager.addSubtask(subtask2);
        inMemoryTaskManager.addSubtask(subtask3);
        inMemoryTaskManager.addSubtask(subtask4);

        System.out.println("\nСписок задач:");
        for (Task task : inMemoryTaskManager.getAllTasks()) {
            System.out.println(task.getTitle() + ": " + task.getStatus());
        }
        System.out.println("Список эпиков:");
        for (Epic epic : inMemoryTaskManager.getAllEpics()) {
            System.out.println(epic.getTitle() + ": " + epic.getStatus());
        }
        System.out.println("\nСписок подзадач:");
        for (Subtask subtask : inMemoryTaskManager.getAllSubtasks()) {
            System.out.println(subtask.getTitle() + ": " + subtask.getStatus());
        }

        inMemoryTaskManager.getEpicById(4);
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);

        inMemoryTaskManager.deleteTask(5);

        task1.setStatus(TaskStatus.IN_PROGRESS);
        task2.setStatus(TaskStatus.DONE);
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        subtask2.setStatus(TaskStatus.DONE);
        subtask3.setStatus(TaskStatus.DONE);
        subtask4.setStatus(TaskStatus.DONE);

        inMemoryTaskManager.updateSubtask(subtask1);
        inMemoryTaskManager.updateSubtask(subtask2);
        inMemoryTaskManager.updateSubtask(subtask3);
        inMemoryTaskManager.updateSubtask(subtask4);

        System.out.println("\nОбновленные статусы:");
        System.out.println("Статус задачи 1: " + task1.getStatus());
        System.out.println("Статус подзадачи 1: " + subtask1.getStatus());
        System.out.println("Статус подзадачи 2: " + subtask2.getStatus());
        System.out.println("Статус подзадачи 3: " + subtask3.getStatus());
        System.out.println("Статус подзадачи 4: " + subtask4.getStatus());
        System.out.println("Статус эпика 1: " + epic1.getStatus());
        System.out.println("Статус эпика 2: " + epic2.getStatus());

        System.out.println(inMemoryTaskManager.getAllSubtasks());

        inMemoryTaskManager.deleteTask(task1.getId());
        inMemoryTaskManager.deleteEpic(epic2.getId());

        System.out.println(inMemoryTaskManager.getAllEpics());
        System.out.println(inMemoryTaskManager.getAllSubtasks());

        System.out.println("\nСписок задач после удаления:");
        for (Task task : inMemoryTaskManager.getAllTasks()) {
            System.out.println(task.getTitle() + ": " + task.getStatus());
        }

        System.out.println("\nСписок эпиков после удаления:");
        for (Epic epic : inMemoryTaskManager.getAllEpics()) {
            System.out.println(epic.getTitle() + ": " + epic.getStatus());
        }

        System.out.println("История:" + inMemoryTaskManager.getHistory());
    }
}
