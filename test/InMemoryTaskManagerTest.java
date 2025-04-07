package test;

import main.*;
import manager.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {
    public TaskManager taskManager;
    public HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager() {
            @Override
            public void deleteTask(int id) {
                if (tasks.containsKey(id)) {
                    tasks.remove(id);
                    historyManager.remove(id); // Удаляем задачу из истории
                }
            }


            public void deleteEpic(int id) {
                Epic epic = epics.get(id);
                if (epic != null) {
                    List<Integer> subtaskIds = new ArrayList<>(epic.getSubtaskIds());

                    for (Integer subtaskId : subtaskIds) {
                        deleteSubtask(subtaskId);
                    }
                    epic.getSubtaskIds().clear();
                    epics.remove(id);
                    historyManager.remove(id);
                }
            }

            public void deleteSubtask(int id) {
                Subtask subtask = subtasks.remove(id); // Удаляем из `subtasks`
                if (subtask != null) {
                    Epic epic = epics.get(subtask.getEpicId());
                    if (epic != null) {
                        epic.getSubtaskIds().remove((Integer) id); // Удаляем ID подзадачи из эпика
                    }
                    historyManager.remove(id); // Удаляем из истории
                }
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
    void historyShouldBeEmptyInitially() {
        List<Task> history = taskManager.getHistory();
        assertTrue(history.isEmpty(), "История должна быть пустой при запуске.");
    }
}
