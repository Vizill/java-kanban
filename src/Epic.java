import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;

    Epic(String title, String description) {
        super(title, description);
        this.subtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    protected TaskStatus calculateEpicStatus() {
        boolean allNew = true;
        boolean allDone = true;

        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != TaskStatus.NEW) {
                allNew = false;
            }
            if (subtask.getStatus() != TaskStatus.DONE) {
                allDone = false;
            }
        }

        if (subtasks.isEmpty() || allNew) {
            return TaskStatus.NEW;
        } else if (allDone) {
            return TaskStatus.DONE;
        } else {
            return TaskStatus.IN_PROGRESS;
        }
    }

    public String getType() {
        return "Epic";
    }
}
