import java.util.List;

public interface TaskManager {
    void createTask(Task task);

    List<Task> getAllTasks();

    Task getTaskById(int taskId);

    void updateTask(Task task);

    void deleteTask(int taskId);

    void addEpic(Epic epic);

    List<Epic> getAllEpics();

    Epic getEpicById(int epicId);

    void updateEpic(Epic epic);

    void deleteEpic(int epicId);

    void addSubtask(Subtask subtask);

    List<Subtask> getAllSubtasks();

    List<Subtask> getAllSubtasksOfEpic(int epicId);

    Subtask getSubtaskById(int subtaskId);

    void updateSubtask(Subtask subtask);

    void deleteSubtask(int subtaskId);

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    List<Task> viewHistory();

    List<Task> getPrioritizedTasks();
}
