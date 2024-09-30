package unitTests;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public static class EpicTest {

    @Test
    public void tasksWithSameIdAreEqual() {
        Task task1 = new Task("Test Task 1", "Description", TaskStatus.NEW);
        Task task2 = new Task("Test Task 2", "Description", TaskStatus.NEW);
        task1.setId(1);
        task2.setId(1);

        assertEquals(task1, task2, "Задачи с одинаковым id должны быть равны.");
    }

    @Test
    public void subtasksWithSameIdAreEqual() {
        Epic epic = new Epic("Test Epic", "Description");
        Subtask subtask1 = new Subtask("Test Subtask 1", "Description", TaskStatus.NEW, epic.getId());
        Subtask subtask2 = new Subtask("Test Subtask 2", "Description", TaskStatus.NEW, epic.getId());
        subtask1.setId(1);
        subtask2.setId(1);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым id должны быть равны.");
    }

    @Test
    public void epicsWithSameIdAreEqual() {
        Epic epic1 = new Epic("Test Epic 1", "Description");
        Epic epic2 = new Epic("Test Epic 2", "Description");
        epic1.setId(1);
        epic2.setId(1);

        assertEquals(epic1, epic2, "Эпики с одинаковым id должны быть равны.");
    }
}

@Test
public void epicCannotBeAddedAsItsOwnSubtask() {
    Epic epic = new Epic("Test Epic", "Description");
    epic.setId(1);

    Subtask invalidSubtask = new Subtask("Invalid Subtask", "Invalid", TaskStatus.NEW, epic.getId());
    invalidSubtask.setEpicId(epic.getId());

    assertNotEquals(epic.getId(), invalidSubtask.getId(), "Эпик не может быть добавлен в виде подзадачи самому себе.");
}

@Test
public void subtaskCannotBeItsOwnEpic() {
    Epic epic = new Epic("Test Epic", "Description");
    Subtask subtask = new Subtask("Test Subtask", "Description", TaskStatus.NEW, epic.getId());

    assertNotEquals(subtask.getId(), epic.getId(), "Подзадача не может стать своим же эпиком.");
}

@Test
public void managersReturnsInitializedHistoryManager() {
    HistoryManager historyManager = Managers.getDefaultHistory();
    assertNotNull(historyManager, "HistoryManager должен быть проинициализирован.");
}

public void main() {
}
