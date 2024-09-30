import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagerTest {

    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void testAddAndRetrieveFromHistory() {
        Task task = new Task("Task for history", "Description for history");
        historyManager.add(task);

        assertEquals(1, historyManager.getHistory().size());
        assertEquals(task.getId(), historyManager.getHistory().get(0).getId());
    }

    @Test
    void testRemoveFromHistory() {
        Task task = new Task("Task to remove from history", "Description to remove from history");
        historyManager.add(task);
        historyManager.remove(task.getId());

        assertTrue(historyManager.getHistory().isEmpty());
    }
}