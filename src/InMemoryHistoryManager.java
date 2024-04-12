import java.util.ArrayList;
import java.util.List;

class InMemoryHistoryManager implements HistoryManager {
    private List<Task> history;

    public InMemoryHistoryManager() {
        this(new ArrayList<>());
    }

    public InMemoryHistoryManager(List<Task> history) {
        this.history = history;
    }

    @Override
    public void add(Task task) {
        history.add(task);
        if (history.size() > 10) {
            history.remove(0);
        }
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}