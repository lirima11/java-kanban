package unitTests;
import static org.junit.jupiter.api.Assertions.*;
public class TaskManagerTest {

    @Test
    public void managersReturnsInitializedTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "TaskManager должен быть проинициализирован.");
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

}
