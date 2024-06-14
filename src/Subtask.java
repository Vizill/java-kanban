import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private int ParentEpic;

    public Subtask(String title, String description, int parentEpic) {
        super(title, description);
        this.ParentEpic = parentEpic;
        this.type = Type.SUBTASK;
    }

    public Subtask(String title, String description, int ParentEpic, Duration duration, LocalDateTime startTime) {
        super(title, description, duration, startTime);
        this.ParentEpic = ParentEpic;
        this.type = Type.SUBTASK;
    }

    public int getParentEpic() {
        return ParentEpic;
    }

    public void setParentEpic(int ParentEpic) {
        this.ParentEpic = ParentEpic;
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
                ", ParentEpic=" + ParentEpic +
                '}';
    }
}
