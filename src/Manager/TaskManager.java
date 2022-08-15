package Manager;

import Task.Task;
import Task.SubTask;
import Task.Epic;

import java.io.IOException;
import java.util.List;

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

    List<Task> getTask();

    List<Epic> getEpic();

    List<SubTask> getSubTask();

    List<SubTask> getEpicSubTasks(int epicId);

    void findEpicStatus(int epicId);

    List<Task> getHistory();

}
