import java.time.LocalDateTime;
import java.time.Duration;

public class CheckTasksOverlap {
    public static boolean doTasksOverlap(LocalDateTime start1, Duration duration1, LocalDateTime start2, Duration duration2) {
        if (start1 == null || start2 == null || duration1 == null || duration2 == null) {
            return false;
        }
        LocalDateTime end1 = start1.plus(duration1);
        LocalDateTime end2 = start2.plus(duration2);
        return (start1.isEqual(start2) || start1.isBefore(start2)) && end1.isAfter(start2)
                || (start2.isEqual(start1) || start2.isBefore(start1)) && end2.isAfter(start1);
    }
}