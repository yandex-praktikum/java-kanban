package Manager;

import Task.Task;
import Task.SubTask;
import Task.Epic;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    int addNewTask(Task task);

    int addNewEpic(Epic epic);

    int addNewSubTask(SubTask subTask);

    Task getTaskById(int id);

    SubTask getSubTaskById(int id);

    Epic getEpicById(int id);

    void deleteAllTask();

    void deleteAllSubTask();

    void deleteAllEpic();

    void deleteTaskById(int id);

    void deleteSubTaskById(Integer id);

    void deleteEpicById(Integer id);

    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

    void updateEpic(Epic epic);

    ArrayList<Task> getTask();

    ArrayList<Epic> getEpic();

    ArrayList<SubTask> getSubTask();

    ArrayList<SubTask> getEpicSubTasks(int epicId);

    void findEpicStatus(int epicId);
}
