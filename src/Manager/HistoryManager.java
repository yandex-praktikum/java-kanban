package Manager;

import Task.Task;

import java.util.List;

public interface HistoryManager {

     List<Task> getHistory();

     void add(Task task);
}
