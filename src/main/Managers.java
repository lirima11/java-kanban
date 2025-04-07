package main;

import manager.*;

public class Managers {
    private Managers() {
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager(new InMemoryHistoryManager()) {
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
