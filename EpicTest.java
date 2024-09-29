import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
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
public void managersReturnsInitializedTaskManager() {
    TaskManager taskManager = Managers.getDefault();
    assertNotNull(taskManager, "TaskManager должен быть проинициализирован.");
}

@Test
public void managersReturnsInitializedHistoryManager() {
    HistoryManager historyManager = Managers.getDefaultHistory();
    assertNotNull(historyManager, "HistoryManager должен быть проинициализирован.");
}

@Test
public void inMemoryTaskManagerAddsAndFindsTasksById() {
    TaskManager taskManager = new InMemoryTaskManager();
    Task task = new Task("Test Task", "Description", TaskStatus.NEW);
    taskManager.createTask(task);

    Task foundTask = taskManager.getTask(task.getId());
    assertNotNull(foundTask, "Задача должна быть найдена по id.");
    assertEquals(task, foundTask, "Найденная задача должна совпадать с добавленной.");
}

@Test
public void tasksWithGeneratedAndGivenIdsDoNotConflict() {
    TaskManager taskManager = new InMemoryTaskManager();
    Task task1 = new Task("Test Task 1", "Description 1", TaskStatus.NEW);
    Task task2 = new Task("Test Task 2", "Description 2", TaskStatus.NEW);

    taskManager.createTask(task1);
    taskManager.createTask(task2);

    Task taskById1 = taskManager.getTask(task1.getId());
    Task taskById2 = taskManager.getTask(task2.getId());

    assertNotNull(taskById1, "Задача с id 1 должна быть найдена.");
    assertNotNull(taskById2, "Задача с id 2 должна быть найдена.");
    assertNotEquals(taskById1, taskById2, "Задачи с разными id не должны конфликтовать.");
}


@Test
public void taskRemainsUnchangedWhenAddedToManager() {
    TaskManager taskManager = new InMemoryTaskManager();
    Task task = new Task("Test Task", "Original Description", TaskStatus.NEW);
    taskManager.createTask(task);

    Task storedTask = taskManager.getTask(task.getId());
    assertEquals(task.getTitle(), storedTask.getTitle(), "Название задачи не должно изменяться.");
    assertEquals(task.getDescription(), storedTask.getDescription(), "Описание задачи не должно изменяться.");
    assertEquals(task.getStatus(), storedTask.getStatus(), "Статус задачи не должен изменяться.");
}

@Test
public void historyManagerSavesTaskVersions() {
    HistoryManager historyManager = new InMemoryHistoryManager();
    Task task = new Task("Test Task", "Description", TaskStatus.NEW);

    historyManager.add(task);

    List<Task> history = historyManager.getHistory();
    assertEquals(1, history.size(), "История должна содержать одну задачу.");
    assertEquals(task, history.get(0), "История должна содержать корректную версию задачи.");
}

public void main() {
}
