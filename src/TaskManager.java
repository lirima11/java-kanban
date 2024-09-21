import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private int idCounter = 0;

    public int generateId() {
        return ++idCounter;
    }
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }
    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        tasks.put(epic.getId(), epic);
    }
    public void addSubtask(Subtask subtask) {
        subtask.getEpic().addSubtask(subtask);
        tasks.put(subtask.getId(), subtask);
    }
    public Task getTaskById(int id) {
        return tasks.get(id);
    }
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    public void deleteAllTasks() {
        tasks.clear();
        epics.clear();
    }
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }
}
