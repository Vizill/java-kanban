import java.io.File;

public class Main {
    public static void main(String[] args) {
        File tasksFile = new File("tasks.csv");
        try {
            FileBackedTaskManager taskManager = FileBackedTaskManager.loadFromFile(tasksFile);
            System.out.println("Данные успешно загружены из файла.");
        } catch (LoadException e) {
            System.err.println("Ошибка при загрузке данных из файла: " + e.getMessage());
        }
    }
}
