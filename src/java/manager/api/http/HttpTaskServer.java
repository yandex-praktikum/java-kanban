package manager.api.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import manager.managers.HttpTaskManager;
import manager.managers.ManagerSaveException;
import manager.managers.Managers;
import task.Epic;
import task.SubTask;
import task.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    public static final int PORT = 8080;
    private final HttpServer server;
    private final Gson gson;
    private final HttpTaskManager taskManager;

    public HttpTaskServer(HttpTaskManager taskManager) throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        this.gson = Managers.getGson();
        this.taskManager = taskManager;
        server.createContext("/tasks", this::handler);
    }


    private void handler(HttpExchange h) {
        try (h) {
            String path = "";
            String[] dirtPath = h.getRequestURI().getPath().split("/");
            if (dirtPath.length > 2)
                path = dirtPath[2];

            switch (path) {
                case "" -> {
                    if (!h.getRequestMethod().equals("GET")) {
                        h.sendResponseHeaders(405, 0);
                    }
                    System.out.println("Запрошены таски по приоритету");
                    final String response = gson.toJson(taskManager.getPrioritizedTasks());
                    sendText(h, response);
                }
                case "history" -> {
                    if (!h.getRequestMethod().equals("GET")) {
                        h.sendResponseHeaders(405, 0);
                    }
                    System.out.println("Запрошена история");
                    final String response = gson.toJson(taskManager.getHistory());
                    sendText(h, response);
                }
                case "task" -> handleTask(h);
                case "subtask" -> handleSubTask(h);
                case "epic" -> handleEpic(h);
                default -> {
                    System.out.println("Неизвестный запрос " + h.getRequestURI());
                    h.sendResponseHeaders(404, 0);
                }
            }
            } catch(IOException | ManagerSaveException e){
                throw new RuntimeException(e);
            }
        }

    private void handleTask(HttpExchange h) throws IOException, ManagerSaveException {
        int id = -1;
        if (h.getRequestURI().getQuery() != null) {
            String query = h.getRequestURI().getQuery().replaceFirst("id=", "");
            id = Integer.parseInt(query);
        }
        switch (h.getRequestMethod()) {
            case "GET" -> {
                if (id == -1) {
                    System.out.println("Запрошен список тасок");
                    final String response = gson.toJson(taskManager.getTask());
                    sendText(h, response);
                    return;
                }
                Task task = taskManager.getTaskById(id);
                String response = gson.toJson(task);
                System.out.println("Запрошена таска " + id);
                sendText(h, response);
            }
            case "DELETE" -> {
                if (id == -1) {
                    taskManager.deleteAllTasks();
                    System.out.println("Все таски удалены");
                    h.sendResponseHeaders(200, 0);
                    return;
                }
                taskManager.deleteTaskById(id);
                System.out.println("Удалена таска " + id);
                h.sendResponseHeaders(200, 0);
            }
            case "POST" -> {
                String json = readText(h);
                if (json.isEmpty()) {
                    System.out.println("Ничего не пришло :(");
                    h.sendResponseHeaders(400, 0);
                    return;
                }
                Task task = gson.fromJson(json, Task.class);
                final Integer idd = task.getId();
                if (idd != null) {
                    taskManager.updateTask(task);
                    System.out.println("Таска " + id + " обновлена!");
                    h.sendResponseHeaders(200, 0);
                } else {
                    taskManager.addNewTask(task);
                    System.out.println("Таска добавлена");
                    String response = gson.toJson(task);
                    sendText(h, response);
                }
            }
        }
    }

    private void handleSubTask(HttpExchange h) throws IOException {
        int id = -1;
        String path = h.getRequestURI().getPath().replaceFirst("/tasks/subtask/", "");

        if (path.equals("epic/")) {
            if (!h.getRequestMethod().equals("GET")) {
                h.sendResponseHeaders(405, 0);
            }
            final String query = h.getRequestURI().getQuery();
            int idd = Integer.parseInt(query.substring(3));
            System.out.println("Запрошен список сабтасок по эпику " + idd);
            final List<SubTask> subTasks = taskManager.getEpicSubTasks(idd);
            final String response = gson.toJson(subTasks);
            sendText(h, response);
        } else {

            if (h.getRequestURI().getQuery() != null) {
                String query = h.getRequestURI().getQuery().replaceFirst("id=", "");
                id = Integer.parseInt(query);
            }

            switch (h.getRequestMethod()) {
                case "GET" -> {
                    if (id == -1) {
                        System.out.println("Запрошен список сабтасок");
                        final String response = gson.toJson(taskManager.getSubTask());
                        sendText(h, response);
                        return;
                    }
                    SubTask task = taskManager.getSubTaskById(id);
                    String response = gson.toJson(task);
                    System.out.println("Запрошена сабтаска " + id);
                    sendText(h, response);
                }
                case "DELETE" -> {
                    if (id == -1) {
                        taskManager.deleteAllSubTasks();
                        System.out.println("Все сабтаски удалены");
                        h.sendResponseHeaders(200, 0);
                        return;
                    }
                    taskManager.deleteSubTaskById(id);
                    System.out.println("Удалена сабтаска " + id);
                    h.sendResponseHeaders(200, 0);
                }
                case "POST" -> {
                    String json = readText(h);
                    if (json.isEmpty()) {
                        System.out.println("Ничего не пришло :(");
                        h.sendResponseHeaders(400, 0);
                        return;
                    }
                    SubTask task = gson.fromJson(json, SubTask.class);
                    final Integer idd = task.getId();
                    if (idd != null) {
                        taskManager.updateSubTask(task);
                        System.out.println("Сабтаска " + idd + " обновлена!");
                        h.sendResponseHeaders(200, 0);
                    } else {
                        taskManager.addNewSubTask(task);
                        System.out.println("Сабтаска добавлена");
                        String response = gson.toJson(task);
                        sendText(h, response);
                    }
                }
            }
        }
    }

    private void handleEpic(HttpExchange h) throws IOException {
        int id = -1;
        if (h.getRequestURI().getQuery() != null) {
            String query = h.getRequestURI().getQuery().replaceFirst("id=", "");
            id = Integer.parseInt(query);
        }
        switch (h.getRequestMethod()) {
            case "GET" -> {
                if (id == -1) {
                    System.out.println("Запрошен список эпиков");
                    final String response = gson.toJson(taskManager.getEpic());
                    sendText(h, response);
                    return;
                }
                Epic task = taskManager.getEpicById(id);
                String response = gson.toJson(task);
                System.out.println("Запрошен эпик " + id);
                sendText(h, response);
            }
            case "DELETE" -> {
                if (id == -1) {
                    taskManager.deleteAllEpics();
                    System.out.println("Все эпики удалены");
                    h.sendResponseHeaders(200, 0);
                    return;
                }
                taskManager.deleteEpicById(id);
                System.out.println("Удален эпик " + id);
                h.sendResponseHeaders(200, 0);
            }
            case "POST" -> {
                String json = readText(h);
                if (json.isEmpty()) {
                    System.out.println("Ничего не пришло :(");
                    h.sendResponseHeaders(400, 0);
                    return;
                }
                Epic task = gson.fromJson(json, Epic.class);
                final Integer idd = task.getId();
                if (idd != null) {
                    taskManager.updateEpic(task);
                    System.out.println("Эпик " + id + " обновлен!");
                    h.sendResponseHeaders(200, 0);
                } else {
                    taskManager.addNewEpic(task);
                    System.out.println("Эпик добавлен");
                    String response = gson.toJson(task);
                    sendText(h, response);
                }
            }
        }
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        server.start();
    }

    public void stop() {
        server.stop(0);
    }


    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }
}