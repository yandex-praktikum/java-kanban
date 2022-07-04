package Manager;

import Task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> historyData = new ArrayList<>();
    static final int HISTORY_MAX_SIZE = 10;

    @Override
    public List<Task> getHistory() {
        return List.copyOf(historyData);
    }

    @Override
    public void add(Task task){
        historyData.add(task);
        if (historyData.size() > HISTORY_MAX_SIZE) {
            historyData.remove(0);
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + historyData;
    }
}
