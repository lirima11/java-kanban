package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
        task1 = new Task("main.Task 1", "Description 1", TaskStatus.NEW);
        task1.setId(1);
        task2 = new Task("main.Task 2", "Description 2", TaskStatus.NEW);
        task2.setId(2);
    }

    @Test
    void historyManagerSavesTaskVersions() {
        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "История должна содержать две задачи.");
        assertEquals(task1, history.get(0), "Первая задача должна быть task1.");
        assertEquals(task2, history.get(1), "Вторая задача должна быть task2.");
    }

    @Test
    void duplicateEntriesShouldKeepOnlyLatest() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1); // Добавляем task1 снова

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "История должна содержать две задачи, без дубликатов.");
        assertEquals(task2, history.get(0), "Первым должен быть task2, так как task1 добавлен повторно.");
        assertEquals(task1, history.get(1), "Последним должен быть task1.");
    }

    @Test
    void removeTaskFromHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(task1.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "После удаления должна остаться одна задача.");
        assertEquals(task2, history.get(0), "В истории должна остаться только task2.");
    }

    @Test
    void removingNonExistentTaskShouldNotAffectHistory() {
        historyManager.add(task1);
        historyManager.remove(999); // Удаляем несуществующую задачу

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История не должна измениться.");
        assertEquals(task1, history.get(0), "В истории должна остаться task1.");
    }
}
