package manager.managers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import manager.api.http.KVTaskClient;
import task.Epic;
import task.SubTask;
import task.Task;

import java.lang.reflect.Type;
import java.util.List;
/** Менеджер для взаимодействия с KVTaskClient. Сохраняет, выгружает, добавляет в менеджер  данные с сервера. **/
public class HttpTaskManager extends FileBackedTasksManager {
    private final Gson gson;
    private final KVTaskClient client;
    public HttpTaskManager(int port) {
        this(port, false);
    }
    public HttpTaskManager(int port, boolean load) {
        super();
        this.client = new KVTaskClient(port);
        gson = Managers.getGson();
        if (load) {
            load();
        }
    }

    protected void addTasks(List<? extends Task> tasks) {
        for (Task task : tasks) {
            if (task.getId() < 20) {
                addNewTask(task);
            } else if (task.getId() < 30) {
                addNewEpic((Epic) task);
            } else if (task.getId() > 29) {
                addNewSubTask((SubTask) task);
            }
        }

    }
    private void load() {
        Type listType = new TypeToken<List<? extends Task>>() {}.getType();

        List<Task> tasks = gson.fromJson(client.load("tasks"), listType);
        addTasks(tasks);
        List<Epic> epics = gson.fromJson(client.load("epics"), listType);
        addTasks(epics);
        List<SubTask> subtasks = gson.fromJson(client.load("subtasks"), listType);
        addTasks(subtasks);
        List<Task> history = gson.fromJson(client.load("history"), listType);
        for (Task task : history) {
            if (task.getId() < 20 ) {
                historyManager.add(getTaskById(task.getId()));
            } else if (task.getId() < 30) {
                historyManager.add(getEpicById(task.getId()));
            } else {
                historyManager.add(getSubTaskById(task.getId()));
            }
        }
    }

    @Override
    public void save() {
        String jsonTasks = gson.toJson(getTasks());
        client.put("tasks", jsonTasks);
        String jsonEpics = gson.toJson(getEpics());
        client.put("epics", jsonEpics);
        String jsonSubtasks = gson.toJson(getSubTasks());
        client.put("subtasks", jsonSubtasks);
        String jsonHistory = gson.toJson(getHistory());
        client.put("history", jsonHistory);
    }
}
