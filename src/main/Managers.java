package main;
import manager.InMemoryTaskManager;
import manager.TaskManager;

public class Managers {
    private Managers() {
    }

    public static TaskManager getDefault() {
        return (TaskManager) new InMemoryTaskManager() {
            @Override
            public void deleteTask(int id) {

            }

            @Override
            public void deleteEpic(int id) {

            }
        };
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
