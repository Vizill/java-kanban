import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    void createTask(Task task);

    ArrayList<Task> getAllTasks();

    Task getTaskById(int taskId);

    void updateTask(Task task);

    void deleteTask(int taskId);

    void addEpic(Epic epic);

    ArrayList<Epic> getAllEpics();

    Epic getEpicById(int epicId);

    void updateEpic(Epic epic);

    void deleteEpic(int epicId);

    void addSubtask(Subtask subtask);

    ArrayList<Subtask> getAllSubtasks();

    ArrayList<Subtask> getAllSubtasksOfEpic(int epicId);

    Subtask getSubtaskById(int subtaskId);

    void updateSubtask(Subtask subtask);

    void deleteSubtask(int subtaskId);

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();
}
