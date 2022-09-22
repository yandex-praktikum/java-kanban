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
/** Сервер, через который происходит взаимодействие TaskManager - пользователь. Принимает запросы со стороны клиента и
 *  возвращает ответ от менеджера в формате json response. **/

public class HttpTaskServer {
    public static final int PORT = 8080;
    private final String GET = "GET";
    private final String POST = "POST";
    private final String DELETE = "DELETE";
    private final HttpServer server;
    private final Gson gson;
    private final HttpTaskManager taskManager;

    public HttpTaskServer(HttpTaskManager taskManager) throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        this.gson = Managers.getGson();
        this.taskManager = taskManager;
        server.createContext("/tasks", this::handler);
    }


    private void handler(HttpExchange exchange) {
        try (exchange) {
            String path = "";
            String[] dirtPath = exchange.getRequestURI().getPath().split("/");
            if (dirtPath.length > 2)
                path = dirtPath[2];

            switch (path) {
                case "" -> {
                    if (!exchange.getRequestMethod().equals(GET)) {
                        exchange.sendResponseHeaders(405, 0);
                    }
                    System.out.println("Запрошены таски по приоритету");
                    final String response = gson.toJson(taskManager.getPrioritizedTasks());
                    sendText(exchange, response);
                }
                case "history" -> {
                    if (!exchange.getRequestMethod().equals(GET)) {
                        exchange.sendResponseHeaders(405, 0);
                    }
                    System.out.println("Запрошена история");
                    final String response = gson.toJson(taskManager.getHistory());
                    sendText(exchange, response);
                }
                case "task" -> handleTask(exchange);
                case "subtask" -> handleSubTask(exchange);
                case "epic" -> handleEpic(exchange);
                default -> {
                    System.out.println("Неизвестный запрос " + exchange.getRequestURI());
                    exchange.sendResponseHeaders(404, 0);
                }
            }
            } catch(IOException | ManagerSaveException e){
                throw new RuntimeException(e);
            }
        }

    private void handleTask(HttpExchange exchange) throws IOException, ManagerSaveException {
        int id = -1;
        if (exchange.getRequestURI().getQuery() != null) {
            String query = exchange.getRequestURI().getQuery().replaceFirst("id=", "");
            id = Integer.parseInt(query);
        }
        switch (exchange.getRequestMethod()) {
            case GET -> {
                if (id == -1) {
                    System.out.println("Запрошен список тасок");
                    final String response = gson.toJson(taskManager.getTasks());
                    sendText(exchange, response);
                    return;
                }
                Task task = taskManager.getTaskById(id);
                String response = gson.toJson(task);
                System.out.println("Запрошена таска " + id);
                sendText(exchange, response);
            }
            case DELETE -> {
                if (id == -1) {
                    taskManager.deleteAllTasks();
                    System.out.println("Все таски удалены");
                    exchange.sendResponseHeaders(200, 0);
                    return;
                }
                taskManager.deleteTaskById(id);
                System.out.println("Удалена таска " + id);
                exchange.sendResponseHeaders(200, 0);
            }
            case POST -> {
                String json = readText(exchange);
                if (json.isEmpty()) {
                    System.out.println("Ничего не пришло :(");
                    exchange.sendResponseHeaders(400, 0);
                    return;
                }
                Task task = gson.fromJson(json, Task.class);
                final Integer idd = task.getId();
                if (idd != null) {
                    taskManager.updateTask(task);
                    System.out.println("Таска " + id + " обновлена!");
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    taskManager.addNewTask(task);
                    System.out.println("Таска добавлена");
                    String response = gson.toJson(task);
                    sendText(exchange, response);
                }
            }
        }
    }

    private void handleSubTask(HttpExchange exchange) throws IOException {
        int id = -1;
        String path = exchange.getRequestURI().getPath().replaceFirst("/tasks/subtask/", "");

        if (path.equals("epic/")) {
            if (!exchange.getRequestMethod().equals(GET)) {
                exchange.sendResponseHeaders(405, 0);
            }
            final String query = exchange.getRequestURI().getQuery();
            int idd = Integer.parseInt(query.substring(3));
            System.out.println("Запрошен список сабтасок по эпику " + idd);
            final List<SubTask> subTasks = taskManager.getEpicSubTasks(idd);
            final String response = gson.toJson(subTasks);
            sendText(exchange, response);
        } else {

            if (exchange.getRequestURI().getQuery() != null) {
                String query = exchange.getRequestURI().getQuery().replaceFirst("id=", "");
                id = Integer.parseInt(query);
            }

            switch (exchange.getRequestMethod()) {
                case GET -> {
                    if (id == -1) {
                        System.out.println("Запрошен список сабтасок");
                        final String response = gson.toJson(taskManager.getSubTasks());
                        sendText(exchange, response);
                        return;
                    }
                    SubTask task = taskManager.getSubTaskById(id);
                    String response = gson.toJson(task);
                    System.out.println("Запрошена сабтаска " + id);
                    sendText(exchange, response);
                }
                case DELETE -> {
                    if (id == -1) {
                        taskManager.deleteAllSubTasks();
                        System.out.println("Все сабтаски удалены");
                        exchange.sendResponseHeaders(200, 0);
                        return;
                    }
                    taskManager.deleteSubTaskById(id);
                    System.out.println("Удалена сабтаска " + id);
                    exchange.sendResponseHeaders(200, 0);
                }
                case POST -> {
                    String json = readText(exchange);
                    if (json.isEmpty()) {
                        System.out.println("Ничего не пришло :(");
                        exchange.sendResponseHeaders(400, 0);
                        return;
                    }
                    SubTask task = gson.fromJson(json, SubTask.class);
                    final Integer idd = task.getId();
                    if (idd != null) {
                        taskManager.updateSubTask(task);
                        System.out.println("Сабтаска " + idd + " обновлена!");
                        exchange.sendResponseHeaders(200, 0);
                    } else {
                        taskManager.addNewSubTask(task);
                        System.out.println("Сабтаска добавлена");
                        String response = gson.toJson(task);
                        sendText(exchange, response);
                    }
                }
            }
        }
    }

    private void handleEpic(HttpExchange exchange) throws IOException {
        int id = -1;
        if (exchange.getRequestURI().getQuery() != null) {
            String query = exchange.getRequestURI().getQuery().replaceFirst("id=", "");
            id = Integer.parseInt(query);
        }
        switch (exchange.getRequestMethod()) {
            case GET -> {
                if (id == -1) {
                    System.out.println("Запрошен список эпиков");
                    final String response = gson.toJson(taskManager.getEpics());
                    sendText(exchange, response);
                    return;
                }
                Epic task = taskManager.getEpicById(id);
                String response = gson.toJson(task);
                System.out.println("Запрошен эпик " + id);
                sendText(exchange, response);
            }
            case DELETE -> {
                if (id == -1) {
                    taskManager.deleteAllEpics();
                    System.out.println("Все эпики удалены");
                    exchange.sendResponseHeaders(200, 0);
                    return;
                }
                taskManager.deleteEpicById(id);
                System.out.println("Удален эпик " + id);
                exchange.sendResponseHeaders(200, 0);
            }
            case POST -> {
                String json = readText(exchange);
                if (json.isEmpty()) {
                    System.out.println("Ничего не пришло :(");
                    exchange.sendResponseHeaders(400, 0);
                    return;
                }
                Epic task = gson.fromJson(json, Epic.class);
                final Integer idd = task.getId();
                if (idd != null) {
                    taskManager.updateEpic(task);
                    System.out.println("Эпик " + id + " обновлен!");
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    taskManager.addNewEpic(task);
                    System.out.println("Эпик добавлен");
                    String response = gson.toJson(task);
                    sendText(exchange, response);
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