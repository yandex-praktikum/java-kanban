package manager.managers;

import task.Task;
import task.SubTask;
import task.Epic;

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

public interface TaskManager {
    int addNewTask(Task task) throws ManagerSaveException, IOException;

    int addNewEpic(Epic epic) throws IOException;

    int addNewSubTask(SubTask subTask) throws IOException;

    Task getTaskById(int id) throws IOException;

    SubTask getSubTaskById(int id) throws IOException;

    Epic getEpicById(int id) throws IOException;

    void deleteAllTasks();

    void deleteAllSubTasks();

    void deleteAllEpics();

    void deleteTaskById(int id);

    void deleteSubTaskById(Integer id);

    void deleteEpicById(Integer id) throws IOException;

    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

    void updateEpic(Epic epic);

    List<Task> getTasks();

    List<Epic> getEpics();

    List<SubTask> getSubTasks();

    List<SubTask> getEpicSubTasks(int epicId);

    void epicStatusCalculation(int epicId);
    void epicTimeCalculation(int epicId);
    TreeSet<Task> getPrioritizedTasks();
    void taskDateValidation(Task task) throws IllegalArgumentException;

    List<Task> getHistory();

}
