import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskManager {
    private int idCounter = 1;
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();

    // Создание новой задачи
    public Task createTask(String title, String description) {
        Task task = new Task(title, description, idCounter++);
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic createEpic(String title, String description) {
        Epic epic = new Epic(title, description, idCounter++);
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Subtask createSubtask(String title, String description, int epicId) {
        if (!epics.containsKey(epicId)) {
            throw new IllegalArgumentException("Epic with id " + epicId + " does not exist.");
        }
        Subtask subtask = new Subtask(title, description, idCounter++, epicId);
        subtasks.put(subtask.getId(), subtask);
        epics.get(epicId).addSubtask(subtask.getId());
        return subtask;
    }

    // Получение всех задач
    public List<Task> getAllTasks() {
        return List.copyOf(tasks.values());
    }

    public List<Epic> getAllEpics() {
        return List.copyOf(epics.values());
    }

    public List<Subtask> getAllSubtasks() {
        return List.copyOf(subtasks.values());
    }

    // Удаление всех задач
    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
        epics.forEach((id, epic) -> epic.getSubtaskIds().clear());
    }

    // Удаление по ID
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public void removeEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
    }

    public void removeSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            epics.get(subtask.getEpicId()).removeSubtask(id);
        }
    }

    // Обновление задач
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        updateEpicStatus(epic);
    }

    // Обновление статуса эпика
    private void updateEpicStatus(Epic epic) {
        List<Subtask> relatedSubtasks = epic.getSubtaskIds().stream()
                .map(subtasks::get)
                .collect(Collectors.toList());
        epic.updateStatus(relatedSubtasks);
    }
}
