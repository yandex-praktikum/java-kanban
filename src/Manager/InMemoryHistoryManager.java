package Manager;

import Task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    protected static List<Task> historyData = new ArrayList<>();
    public InMemoryHistoryManager(List<Task> historyData) {
        this.historyData = historyData;
    }
    @Override
    public String toString() {
        return "Task{" +
                "name='" + historyData;
    }
    @Override
    public List<Task> getHistory() {
        return historyData;
    }

    @Override
    public void addHistory(Task task) {
        historyData.add(task);
        if (historyData.size() > 10) {
            historyData.remove(0);
        }
    }
}
