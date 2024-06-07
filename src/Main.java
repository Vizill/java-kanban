public class Main {

    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = Manager.getDefaultTaskManager();
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        Task task3 = new Task("Задача 3", "Описание задачи 3");
        Task task4 = new Task("Задача 4", "Описание задачи 4");
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        inMemoryTaskManager.createTask(task3);
        inMemoryTaskManager.createTask(task4);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.add(task4);
        System.out.println("История просмотров после добавления задач:");
        for (Task task : historyManager.getHistory()) {
            System.out.println(task);
        }
        historyManager.add(task2);
        historyManager.add(task3);
        System.out.println("История просмотров после повторного добавления task2 и task3:");
        for (Task task : historyManager.getHistory()) {
            System.out.println(task);
        }
        historyManager.remove(2);
        System.out.println("История просмотров после удаления задачи с id=2:");
        for (Task task : historyManager.getHistory()) {
            System.out.println(task);
        }
        System.out.println("Успех");
        System.out.println("Успех");
        System.out.println("Успех");
    }
}
