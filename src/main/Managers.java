package main;

import manager.InMemoryTaskManager;
import manager.TaskManager;

import java.util.concurrent.StructuredTaskScope;

public class Managers {
    private Managers() {
    }

    public static TaskManager getDefault() {
        return (TaskManager) new InMemoryTaskManager() {
            @Override
            public StructuredTaskScope.Subtask createSubtask(StructuredTaskScope.Subtask subtask) {
                return null;
            }

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
