package test;

import main.*;
import manager.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager() {
            @Override
            public void deleteTask(int id) {

            }

            @Override
            public void deleteEpic(int id) {

            }
        };
    }

    @Test
    void createAndRetrieveTask() {
        Task task = new Task("Task 1", "Description", TaskStatus.NEW);
        Task createdTask = taskManager.createTask(task);

        Task retrievedTask = taskManager.getTask(createdTask.getId());
        assertNotNull(retrievedTask, "Задача должна существовать.");
        assertEquals(task.getTitle(), retrievedTask.getTitle(), "Названия должны совпадать.");
    }

    @Test
    void createAndRetrieveEpic() {
        Epic epic = new Epic("Epic 1", "Epic Description");
        Epic createdEpic = taskManager.createEpic(epic);

        Epic retrievedEpic = taskManager.getEpic(createdEpic.getId());
        assertNotNull(retrievedEpic, "Эпик должен существовать.");
        assertEquals(epic.getTitle(), retrievedEpic.getTitle(), "Названия должны совпадать.");
    }

    @Test
    void createAndRetrieveSubtask() {
        Epic epic = taskManager.createEpic(new Epic("Epic", "Epic Desc"));
        Subtask subtask = new Subtask("Subtask", "Subtask Desc", TaskStatus.NEW, epic.getId());
        Subtask createdSubtask = taskManager.createSubtask(subtask);

        Subtask retrievedSubtask = taskManager.getSubtask(createdSubtask.getId());
        assertNotNull(retrievedSubtask, "Подзадача должна существовать.");
        assertEquals(subtask.getTitle(), retrievedSubtask.getTitle(), "Названия подзадач должны совпадать.");
    }

    @Test
    void deletingTaskRemovesFromHistory() {
        Task task = taskManager.createTask(new Task("Task", "Desc", TaskStatus.NEW));
        taskManager.getTask(task.getId()); // Добавляем в историю
        taskManager.deleteTask(task.getId());

        List<Task> history = taskManager.getHistory();
        assertFalse(history.contains(task), "Удалённая задача не должна быть в истории.");
    }

    @Test
    void deletingEpicRemovesAllSubtasks() {
        Epic epic = taskManager.createEpic(new Epic("Epic", "Epic Desc"));
        Subtask subtask1 = taskManager.createSubtask(new Subtask("Sub 1", "Sub Desc", TaskStatus.NEW, epic.getId()));
        Subtask subtask2 = taskManager.createSubtask(new Subtask("Sub 2", "Sub Desc", TaskStatus.NEW, epic.getId()));

        taskManager.deleteEpic(epic.getId());

        assertNull(taskManager.getEpic(epic.getId()), "Эпик должен быть удалён.");
        assertNull(taskManager.getSubtask(subtask1.getId()), "Подзадача 1 должна быть удалена.");
        assertNull(taskManager.getSubtask(subtask2.getId()), "Подзадача 2 должна быть удалена.");
    }

    @Test
    void historyShouldBeEmptyInitially() {
        List<Task> history = taskManager.getHistory();
        assertTrue(history.isEmpty(), "История должна быть пустой при запуске.");
    }
}
