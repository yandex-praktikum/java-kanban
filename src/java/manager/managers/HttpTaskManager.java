package manager.managers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import manager.api.http.KVTaskClient;
import task.Epic;
import task.SubTask;
import task.Task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/* Привет, ревьюер! Реализовал сервер, клиент, менеджер, и снова сервер!
* Всё работает, тесты на все эндпоинты и на загрузку с сервера passed.
* Точно где-то есть лажа, но у меня глаз замылился, сам не найду. С нетерпением жду комментариев ! :)*/

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

        ArrayList<Task> tasks = gson.fromJson(client.load("tasks"), listType);
        addTasks(tasks);
        ArrayList<Epic> epics = gson.fromJson(client.load("epics"), listType);
        addTasks(epics);
        ArrayList<SubTask> subtasks = gson.fromJson(client.load("subtasks"), listType);
        addTasks(subtasks);
    }

    @Override
    public void save() {
        String jsonTasks = gson.toJson(getTask());
        client.put("tasks", jsonTasks);
        String jsonEpics = gson.toJson(getEpic());
        client.put("epics", jsonEpics);
        String jsonSubtasks = gson.toJson(getSubTask());
        client.put("subtasks", jsonSubtasks);
    }
}
