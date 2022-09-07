package manager.history;

import task.Task;

import java.util.List;

public interface HistoryManager {

     List<Task> getHistory();

     void add(Task task);
     void remove(int id);
}
