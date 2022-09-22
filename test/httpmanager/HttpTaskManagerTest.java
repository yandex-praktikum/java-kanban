package test.httpmanager;

import manager.api.http.HttpTaskServer;
import manager.api.server.KVServer;
import manager.managers.HttpTaskManager;
import manager.managers.Managers;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.SubTask;
import task.Task;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskManagerTest {
    private KVServer kvServer;
    HttpTaskManager manager;
    LocalDateTime dateTime = LocalDateTime.of(2022, 8, 26,
            12, 0, 0);

    @Test
    void loadFromServerTest() throws IOException {
        kvServer = Managers.getDefaultKVServer();
        manager = new HttpTaskManager(KVServer.PORT);
        HttpTaskServer serv = new HttpTaskServer(manager);
        serv.start();

        Task task = new Task("Погулять", 1, "Оделся и пошёл",Status.NEW,
                0L, dateTime);
        Task task1 = new Task("Погулять", 1, "Оделся и пошёл",Status.NEW,
                0L, dateTime.minusHours(2));
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(1), dateTime.minusHours(9));

        manager.addNewEpic(epic);

        SubTask subTask = new SubTask("Котлетки", 0, "Жарим", Status.NEW,
                epic.getId(), 1L, LocalDateTime.now().plusHours(2));

        manager.addNewTask(task1);
        manager.addNewTask(task);
        manager.addNewSubTask(subTask);


        HttpTaskManager newManager = new HttpTaskManager(KVServer.PORT, true);
        assertEquals(manager.getTasks(), newManager.getTasks(), "Список задач не совпадает"); //файл без истории

        manager.getTaskById(task.getId());
        manager.getTaskById(task1.getId());

        assertNotNull(newManager.getHistory(), "История не восстановлена"); //файл с историей
        kvServer.stop();
        serv.stop();
    }
}