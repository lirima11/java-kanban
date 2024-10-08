package managerTask;

import java.util.List;

public interface TaskManager {
    Task createTask(Task task);
    Epic createEpic(Epic epic);
    Subtask createSubtask(Subtask subtask);

    List<Task> getAllTasks();
    List<Epic> getAllEpics();
    List<Subtask> getAllSubtasks();

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
}
