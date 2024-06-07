import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("tasks.csv");
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        manager.loadTasksFromFile();
    }
}
