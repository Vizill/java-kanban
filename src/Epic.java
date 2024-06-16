import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks;
    private LocalDateTime endTime;

    public Epic(String title, String description) {
        super(title, description);
        this.type = Type.EPIC;
        this.subtasks = new ArrayList<>();
    }

    public Epic(String title, String description, Duration duration, LocalDateTime startTime) {
        super(title, description, duration, startTime);
        this.type = Type.EPIC;
        this.subtasks = new ArrayList<>();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        updateEpicTimes();
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
        updateEpicTimes();
    }

    public void updateEpicTimes() {
        if (subtasks.isEmpty()) {
            this.duration = Duration.ZERO;
            this.startTime = null;
            this.endTime = null;
            return;
        }

        Duration totalDuration = Duration.ZERO;
        LocalDateTime earliestStartTime = LocalDateTime.MAX;
        LocalDateTime latestEndTime = LocalDateTime.MIN;

        for (Subtask subtask : subtasks) {
            totalDuration = totalDuration.plus(subtask.getDuration());
            if (subtask.getStartTime() != null) {
                if (subtask.getStartTime().isBefore(earliestStartTime)) {
                    earliestStartTime = subtask.getStartTime();
                }
                if (subtask.getEndTime().isAfter(latestEndTime)) {
                    latestEndTime = subtask.getEndTime();
                }
            }
        }

        this.duration = totalDuration;
        this.startTime = earliestStartTime;
        this.endTime = latestEndTime;
    }

    @Override
    public Duration getDuration() {
        updateEpicTimes();
        return duration;
    }

    @Override
    public LocalDateTime getStartTime() {
        updateEpicTimes();
        return startTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        updateEpicTimes();
        return endTime;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", type=" + type +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", subtasks=" + subtasks +
                '}';
    }
}
