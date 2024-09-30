package unitTests;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class HistoryManagerTest {

    @Test
    public void historyManagerSavesTaskVersions() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Test Task", "Description", TaskStatus.NEW);

        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История должна содержать одну задачу.");
        assertEquals(task, history.get(0), "История должна содержать корректную версию задачи.");
    }
}
