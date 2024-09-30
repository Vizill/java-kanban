import java.time.LocalDateTime;
import java.time.Duration;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> taskHash;
    private Map<Integer, Epic> epicHash;
    private Map<Integer, Subtask> subtaskHash;
    private int idCount;
    private HistoryManager historyManager;
    private Set<Task> prioritizedTasks;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this();
        this.historyManager = historyManager;
    }

    public InMemoryTaskManager() {
        this.taskHash = new HashMap<>();
        this.epicHash = new HashMap<>();
        this.subtaskHash = new HashMap<>();
        this.idCount = 1;
        this.historyManager = new InMemoryHistoryManager();
        this.prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime, Comparator.nullsLast(LocalDateTime::compareTo)));
    }

    @Override
    public void createTask(Task task) {
        if (validateTaskTime(task)) {
            task.setId(getNextId());
            taskHash.put(task.getId(), task);
            if (task.getStartTime() != null) {
                prioritizedTasks.add(task);
            }
        } else {
            System.out.println("Ошибка: задача пересекается по времени с другой задачей.");
        }
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskHash.values());
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = taskHash.get(taskId);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public void updateTask(Task task) {
        Task existingTask = taskHash.get(task.getId());
        if (validateTaskTime(task)) {
            if (existingTask != null) {
                if (existingTask.getStartTime() != null) {
                    prioritizedTasks.remove(existingTask);
                }
                taskHash.put(task.getId(), task);
                if (task.getStartTime() != null) {
                    prioritizedTasks.add(task);
                }
            }
        } else {
            System.out.println("Ошибка: задача пересекается по времени с другой задачей.");
        }
    }

    @Override
    public void deleteTask(int taskId) {
        Task task = taskHash.remove(taskId);
        if (task != null && task.getStartTime() != null) {
            prioritizedTasks.remove(task);
        }
    }

    @Override
    public void addEpic(Epic epic) {
        if (validateTaskTime(epic)) {
            epic.setId(getNextId());
            epicHash.put(epic.getId(), epic);
        } else {
            System.out.println("Ошибка: эпик пересекается по времени с другой задачей или эпиком.");
        }
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epicHash.values());
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = epicHash.get(epicId);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic existingEpic = epicHash.get(epic.getId());
        if (existingEpic != null) {
            epicHash.put(epic.getId(), epic);
        }
    }

    @Override
    public void deleteEpic(int epicId) {
        Epic epic = epicHash.remove(epicId);
        if (epic != null) {
            List<Subtask> subtasksToRemove = new ArrayList<>();
            for (Subtask subtask : subtaskHash.values()) {
                if (subtask.getParentEpic() == epicId) {
                    subtasksToRemove.add(subtask);
                }
            }
            for (Subtask subtask : subtasksToRemove) {
                deleteSubtask(subtask.getId());
            }
        }
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (validateTaskTime(subtask)) {
            if (subtask.getParentEpic() != subtask.getId()) {
                subtask.setId(getNextId());
                subtaskHash.put(subtask.getId(), subtask);
                updateEpicStatus(subtask.getParentEpic());
            } else {
                System.out.println("Ошибка: подзадача пересекается по времени с эпиком.");
            }
        } else {
            System.out.println("Ошибка: подзадача пересекается по времени с другой задачей.");
        }
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
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        Subtask existingSubtask = subtaskHash.get(subtask.getId());
        if (validateTaskTime(subtask)) {
            if (existingSubtask != null) {
                if (existingSubtask.getStartTime() != null) {
                    prioritizedTasks.remove(existingSubtask);
                }
                subtaskHash.put(subtask.getId(), subtask);
                if (subtask.getStartTime() != null) {
                    prioritizedTasks.add(subtask);
                }
                updateEpicStatus(subtask.getParentEpic());
            }
        } else {
            System.out.println("Ошибка: подзадача пересекается по времени с другой задачей.");
        }
    }

    @Override
    public void deleteSubtask(int subtaskId) {
        Subtask subtask = subtaskHash.remove(subtaskId);
        if (subtask != null) {
            if (subtask.getStartTime() != null) {
                prioritizedTasks.remove(subtask);
            }
            updateEpicStatus(subtask.getParentEpic());
        }
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epicHash.get(epicId);
        if (epic != null) {
            calculateEpicStatus(epic);
            epic.updateEpicTimes();
        }
    }

    protected void calculateEpicStatus(Epic epic) {
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
        prioritizedTasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epicHash.clear();
        prioritizedTasks.clear();
        subtaskHash.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        subtaskHash.clear();
        prioritizedTasks.clear();
        for (Epic epic : epicHash.values()) {
            updateEpicStatus(epic.getId());
        }
    }

    @Override
    public List<Task> viewHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    protected boolean validateTaskTime(Task newTask) {
        LocalDateTime newStart = newTask.getStartTime();
        Duration newDuration = newTask.getDuration();

        return prioritizedTasks.stream()
                .noneMatch(task -> {
                    LocalDateTime existingStart = task.getStartTime();
                    Duration existingDuration = task.getDuration();
                    return CheckTasksOverlap.doTasksOverlap(newStart, newDuration, existingStart, existingDuration);
                });
    }
}
