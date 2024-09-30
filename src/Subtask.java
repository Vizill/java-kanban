import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private int parentEpic;

    public Subtask(String title, String description, int parentEpic) {
        super(title, description);
        this.parentEpic = parentEpic;
        this.type = Type.SUBTASK;
    }

    public Subtask(String title, String description, int parentEpic, Duration duration, LocalDateTime startTime) {
        super(title, description, duration, startTime);
        this.parentEpic = parentEpic;
        this.type = Type.SUBTASK;
    }

    public int getParentEpic() {
        return parentEpic;
    }

    public void setParentEpic(int parentEpic) {
        this.parentEpic = parentEpic;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", type=" + type +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", parentEpic=" + parentEpic +
                '}';
    }
}
