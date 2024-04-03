import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private int taskIdCount;

    public TaskManager() {
        tasks = new HashMap<>();
        taskIdCount = 1;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public void updateEpicStatus() {
        for (Task task : tasks.values()) {
            if (task instanceof Epic) {
                Epic epic = (Epic) task;
                TaskStatus epicStatus = epic.calculateEpicStatus();
                epic.setStatus(epicStatus);
            }
        }
    }

    public void getAllTasks() {
        System.out.println("Список задач:");
        updateEpicStatus();
        for (Task task : getTasks().values()) {
            if (!"Epic".equals(task.getType()) && !"Subtask".equals(task.getType())) {
                System.out.println(task.getTitle() + ": " + task.getStatus());
            }
            if ("Epic".equals(task.getType())) {
                System.out.println(task.getTitle() + ": " + task.getStatus());
            }
            if ("Subtask".equals(task.getType())) {
                System.out.println(task.getTitle() + ": " + task.getStatus());
            }
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public Task getTaskById(int taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            System.out.println("ID: " + task.getId());
            System.out.println("Название: " + task.getTitle());
            System.out.println("Описание: " + task.getDescription());
            System.out.println("Статус: " + task.getStatus());
        } else {
            System.out.println("Задача с ID " + taskId + " не найдена.");
        }
        return task;
    }

    public void createTask(Task task) {
        task.setId(taskIdCount);
        tasks.put(taskIdCount, task);
        taskIdCount++;
    }

    public void updateTask(int taskId, String newTitle, String newDescription, TaskStatus newStatus) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.setTitle(newTitle);
            task.setDescription(newDescription);
            task.setStatus(newStatus);
            tasks.put(taskId, task);
            System.out.println("Задача с ID " + taskId + " успешно обновлена.");
        } else {
            System.out.println("Задача с ID " + taskId + " не найдена.");
        }
    }

    public void deleteTask(int taskId) {
        tasks.remove(taskId);
    }

    public ArrayList<Subtask> getAllSubtasks(int epicId) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getClass() == Subtask.class && ((Subtask) task).getParentEpic() == epicId) {
                subtasks.add((Subtask) task);
            }
        }
        return subtasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskManager that = (TaskManager) o;
        return taskIdCount == that.taskIdCount && Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks, taskIdCount);
    }
}
