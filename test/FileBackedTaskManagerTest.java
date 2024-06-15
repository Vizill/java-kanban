import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest {

    private static final String TEST_FILE_PATH = "test_tasks.json";
    private FileBackedTaskManager taskManager;

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
        File testFile = new File(TEST_FILE_PATH);
        taskManager = new FileBackedTaskManager(testFile);
    }

    @AfterEach
    void tearDown() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testLoadNonExistentFile() {
        assertThrows(LoadException.class, () -> {
            FileBackedTaskManager.loadFromFile(new File("non_existent_file.json"));
        });
    }

    @Test
    void testCalculateEpicStatusAllNewSubtasks() {
        Epic epic = new Epic("Epic 1", "Epic description");

        Subtask subtask1 = new Subtask("Subtask 1", "Subtask description", epic.getId());
        Subtask subtask2 = new Subtask("Subtask 2", "Subtask description", epic.getId());

        subtask1.setStatus(TaskStatus.NEW);
        subtask2.setStatus(TaskStatus.NEW);

        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        taskManager.calculateEpicStatus(epic);

        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

}
