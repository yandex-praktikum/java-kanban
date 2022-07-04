package Manager;

import Task.Task;
import Task.SubTask;
import Task.Epic;

import java.util.List;

public interface TaskManager {
    int addNewTask(Task task);

    int addNewEpic(Epic epic);

    int addNewSubTask(SubTask subTask);

    Task getTaskById(int id);

    SubTask getSubTaskById(int id);

    Epic getEpicById(int id);

    void deleteAllTasks();

    void deleteAllSubTasks();

    void deleteAllEpics();

    void deleteTaskById(int id);

    void deleteSubTaskById(Integer id);

    void deleteEpicById(Integer id);

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
