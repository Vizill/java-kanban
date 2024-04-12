import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> taskHash;
    private HashMap<Integer, Epic> epicHash;
    private HashMap<Integer, Subtask> subtaskHash;
    private int idCount;
    private List<Task> viewedTasks;


    public InMemoryTaskManager() {
        this.taskHash = new HashMap<>();
        this.epicHash = new HashMap<>();
        this.subtaskHash = new HashMap<>();
        this.idCount = 1;
        viewedTasks = new ArrayList<>();
    }

    @Override
    public void createTask(Task task) {
        task.setId(getNextId());
        taskHash.put(task.getId(), task);
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskHash.values());
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = taskHash.get(taskId);
        if (task != null) {
            System.out.println("ID: " + task.getId());
            System.out.println("Название: " + task.getTitle());
            System.out.println("Описание: " + task.getDescription());
            System.out.println("Статус: " + task.getStatus());
        } else {
            System.out.println("Задача с ID " + taskId + " не найдена.");
        }
        updateViewedTasks(task);
        return task;
    }

    @Override
    public void updateTask(Task task) {
        Task t = taskHash.get(task.getId());
        if (t != null) {
            t.setTitle(task.getTitle());
            t.setDescription(task.getDescription());
            t.setStatus(task.getStatus());
            System.out.println("Задача " + task.getTitle() + " успешно обновлена.");
        } else {
            System.out.println("Задача с ID " + task.getId() + " не найдена.");
        }
    }

    @Override
    public void deleteTask(int taskId) {
        Task task = taskHash.remove(taskId);
        if (task != null) {
            System.out.println("Задача " + task.getTitle() + " успешно удалена.");
        } else {
            System.out.println("Задача с ID " + taskId + " не найдена.");
        }
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(getNextId());
        epicHash.put(epic.getId(), epic);
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epicHash.values());
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = epicHash.get(epicId);
        if (epic != null) {
            System.out.println("ID: " + epic.getId());
            System.out.println("Название: " + epic.getTitle());
            System.out.println("Описание: " + epic.getDescription());
            System.out.println("Статус: " + epic.getStatus());
        } else {
            System.out.println("Эпик с ID " + epicId + " не найдена.");
        }
        updateViewedTasks(epic);
        return epic;
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic e = epicHash.get(epic.getId());
        if (e != null) {
            e.setTitle(epic.getTitle());
            e.setDescription(epic.getDescription());
            System.out.println("Эпик " + epic.getTitle() + " успешно обновлен.");
        } else {
            System.out.println("Эпик с ID " + epic.getId() + " не найден.");
        }
    }

    @Override
    public void deleteEpic(int epicId) {
        Epic epic = epicHash.remove(epicId);
        if (epic != null) {
            ArrayList<Subtask> subtasksToRemove = new ArrayList<>();
            for (Subtask subtask : subtaskHash.values()) {
                if (subtask.getParentEpic() == epicId) {
                    subtasksToRemove.add(subtask);
                }
            }
            for (Subtask subtask : subtasksToRemove) {
                deleteSubtask(subtask.getId());
            }
            System.out.println("Эпик " + epic.getTitle() + " успешно удален.");
        } else {
            System.out.println("Эпик с ID " + epicId + " не найден.");
        }
    }

    @Override
    public void addSubtask(Subtask subtask) {
        subtask.setId(getNextId());
        subtaskHash.put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getParentEpic());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtaskHash.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> subtasksOfEpic = new ArrayList<>();
        for (Subtask subtask : subtaskHash.values()) {
            if (subtask.getParentEpic() == epicId) {
                subtasksOfEpic.add(subtask);
            }
        }
        return subtasksOfEpic;
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        Subtask subtask = subtaskHash.get(subtaskId);
        if (subtask != null) {
            System.out.println("ID: " + subtask.getId());
            System.out.println("Название: " + subtask.getTitle());
            System.out.println("Описание: " + subtask.getDescription());
            System.out.println("Статус: " + subtask.getStatus());
        } else {
            System.out.println("Эпик с ID " + subtaskId + " не найдена.");
        }
        updateViewedTasks(subtask);
        return subtask;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        Subtask s = subtaskHash.get(subtask.getId());
        if (s != null) {
            s.setTitle(subtask.getTitle());
            s.setDescription(subtask.getDescription());
            s.setStatus(subtask.getStatus());
            updateEpicStatus(subtask.getParentEpic());
            System.out.println("Подзадача " + subtask.getTitle() + " успешно обновлена.");
        } else {
            System.out.println("Подзадача с ID " + subtask.getId() + " не найдена.");
        }
    }

    @Override
    public void deleteSubtask(int subtaskId) {
        Subtask subtask = subtaskHash.remove(subtaskId);
        if (subtask != null) {
            updateEpicStatus(subtask.getParentEpic());
            System.out.println("Подзадача " + subtask.getTitle() + " успешно удалена.");
        } else {
            System.out.println("Подзадача с ID " + subtaskId + " не найдена.");
        }
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epicHash.get(epicId);
        if (epic != null) {
            calculateEpicStatus(epic);
        } else {
            System.out.println("Эпик с ID " + epicId + " не найден.");
        }
    }

    private void calculateEpicStatus(Epic epic) {
        ArrayList<Subtask> subtasks = getAllSubtasksOfEpic(epic.getId());
        boolean allDone = true;
        if (subtasks.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() == TaskStatus.IN_PROGRESS) {
                epic.setStatus(TaskStatus.IN_PROGRESS);
                return;
            }
        }
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != TaskStatus.DONE) {
                allDone = false;
                break;
            }
        }
        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.NEW);
        }
    }

    private int getNextId() {
        return idCount++;
    }

    @Override
    public void deleteAllTasks() {
        taskHash.clear();
    }

    @Override
    public void deleteAllEpics() {
        epicHash.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        subtaskHash.clear();
    }

    private void updateViewedTasks(Task task) {
        viewedTasks.add(task);
        if (viewedTasks.size() > 10) {
            viewedTasks.remove(0);
        }
    }

    @Override
    public List<Task> getHistory() {
        return  viewedTasks;
    }
}