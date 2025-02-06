package tests;

import main.*;
import manager.HistoryManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

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

    @Test
    public void historyDoesNotContainDuplicates() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Test Task", "Description", TaskStatus.NEW);
        task.setId(1);

        historyManager.add(task);
        historyManager.add(task); // Повторное добавление

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "В истории должна остаться только последняя версия задачи.");
    }

    @Test
    public void removingTaskRemovesItFromHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Task 1", "Description", TaskStatus.NEW);
        Task task2 = new Task("Task 2", "Description", TaskStatus.NEW);
        task1.setId(1);
        task2.setId(2);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(1); // Удаляем первую задачу

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История должна содержать одну задачу после удаления.");
        assertEquals(task2, history.get(0), "Оставшаяся задача должна быть task2.");
    }

    @Test
    public void removingNonExistentTaskDoesNotAffectHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Test Task", "Description", TaskStatus.NEW);
        task.setId(1);

        historyManager.add(task);
        historyManager.remove(999); // Удаляем несуществующую задачу

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История не должна измениться.");
        assertEquals(task, history.get(0), "Задача должна остаться в истории.");
    }
}
