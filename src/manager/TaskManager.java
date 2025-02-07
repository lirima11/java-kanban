package manager;
import main.Task;
import main.Epic;
import main.Subtask;

import java.util.List;
import java.util.concurrent.StructuredTaskScope;

public interface TaskManager {
    Task createTask(Task task);
    Epic createEpic(Epic epic);
    StructuredTaskScope.Subtask createSubtask(StructuredTaskScope.Subtask subtask);

    List<Task> getAllTasks();
    List<Epic> getAllEpics();
    List<Subtask> getAllSubtasks();

    Subtask createSubtask(Subtask subtask);

    Task getTask(int id);
    Epic getEpic(int id);
    Subtask getSubtask(int id);

    void removeAllTasks();
    void removeAllEpics();
    void removeAllSubtasks();

    boolean removeTaskById(int id);
    boolean removeEpicById(int id);
    boolean removeSubtaskById(int id);

    boolean updateTask(Task task);
    boolean updateEpic(Epic epic);
    boolean updateSubtask(Subtask subtask);

    List<Task> getHistory(); // Метод для получения истории

    void deleteTask(int id);

    void deleteEpic(int id);
}