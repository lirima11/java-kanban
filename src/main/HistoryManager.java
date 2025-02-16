package main;

import java.util.List;

public interface HistoryManager {
    void add(Task var1);

    void remove(int var1);

    List<Task> getHistory();
}