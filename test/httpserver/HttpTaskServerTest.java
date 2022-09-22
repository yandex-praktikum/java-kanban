package test.httpserver;

import manager.api.http.HttpTaskServer;
import manager.api.server.KVServer;
import manager.managers.HttpTaskManager;
import manager.managers.Managers;
import org.junit.jupiter.api.*;
import task.Epic;
import task.Status;
import task.SubTask;
import task.Task;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


    class HttpTaskServerTest {

        private KVServer kvServer;
        HttpTaskManager manager;
        LocalDateTime dateTime = LocalDateTime.of(2022, 8, 26,
                12, 0, 0);

        @BeforeEach
        public void setUp() throws IOException {
            kvServer = Managers.getDefaultKVServer();
            manager = new HttpTaskManager(KVServer.PORT);
        }

        @AfterEach
        protected void tearDown() {
            kvServer.stop();
        }

    @Test
    void addNewTaskTest() throws IOException {
        HttpTaskServer serv = new HttpTaskServer(manager);
        serv.start();
        Task task = new Task("Погулять", 1, "Оделся и пошёл", Status.NEW,
                0L, dateTime);
        manager.addNewTask(task);
        final List<Task> tasks = manager.getTasks();
        assertNotNull(tasks, "Задачи на возвращаются");
        assertEquals(task, tasks.get(0), "Задачи не совпадают");
        serv.stop();
    }


    @Test
    void addNewEpicTest() throws IOException {
        HttpTaskServer serv = new HttpTaskServer(manager);
        serv.start();
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        final List<Epic> epics = manager.getEpics();
        assertNotNull(epics, "Задачи на возвращаются");
        assertEquals(epic, epics.get(0), "Задачи не совпадают");
        serv.stop();
    }

    @Test
    void addNewSubTaskTest() throws IOException {
        HttpTaskServer serv = new HttpTaskServer(manager);
        serv.start();
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), 0L, dateTime.minusHours(1));
        manager.addNewSubTask(subtask);
        final List<SubTask> subs = manager.getSubTasks();

        assertNotNull(subs, "Задачи на возвращаются");
        assertEquals(subtask, subs.get(0), "Задачи не совпадают");
        assertEquals(epic.getId(), subtask.getEpicId(), "Айди эпика не присвоен сабтаске");
        serv.stop();
    }

    @Test
    void getTaskByIdTest() throws IOException {
        HttpTaskServer serv = new HttpTaskServer(manager);
        serv.start();
        Task task = new Task("Погулять", 1, "Оделся и пошёл", Status.NEW,
                0L, dateTime);
        final int id = manager.addNewTask(task);
        assertEquals(id, task.getId(), "Айди сохраняется некорректно");
        serv.stop();
    }

   @Test
    void getSubTaskByIdTest() throws IOException {
       HttpTaskServer serv = new HttpTaskServer(manager);
       serv.start();
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), 0L, dateTime.minusHours(1));
        final int id = manager.addNewSubTask(subtask);
        assertEquals(id, subtask.getId(), "Айди сохраняется некорректно");
       serv.stop();
    }

    @Test
    void getEpicByIdTest() throws IOException {
        HttpTaskServer serv = new HttpTaskServer(manager);
        serv.start();
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        final int id = manager.addNewEpic(epic);
        assertEquals(id, epic.getId(), "Айди сохраняется некорректно");
        serv.stop();
    }

    @Test
    void deleteAllTasksTest() throws IOException {
        HttpTaskServer serv = new HttpTaskServer(manager);
        serv.start();
        Task task = new Task("Погулять", 1, "Оделся и пошёл", Status.NEW,
                0L, dateTime);
        manager.addNewTask(task);
        manager.deleteAllTasks();
        assertTrue(manager.getTasks().isEmpty(), "Таски не удаляются");
        serv.stop();
    }

    @Test
    void deleteAllSubTasksTest() throws IOException {
        HttpTaskServer serv = new HttpTaskServer(manager);
        serv.start();
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), 0L, dateTime.minusHours(1));
        manager.addNewSubTask(subtask);
        manager.deleteAllSubTasks();
        assertTrue(manager.getSubTasks().isEmpty(), "Сабтаски не удаляются");
        serv.stop();
    }

    @Test
    void deleteAllEpicsTest() throws IOException {
        HttpTaskServer serv = new HttpTaskServer(manager);
        serv.start();
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        manager.deleteAllEpics();
        assertTrue(manager.getEpics().isEmpty(), "Эпики не удаляются");
        serv.stop();
    }

    @Test
    void deleteTaskByIdTest() throws IOException {
        HttpTaskServer serv = new HttpTaskServer(manager);
        serv.start();
        Task task = new Task("Погулять", 1, "Оделся и пошёл", Status.NEW,
                0L, dateTime);
        manager.addNewTask(task);
        manager.deleteTaskById(task.getId());
        assertTrue(manager.getTasks().isEmpty(), "Таски не удаляются");
        serv.stop();
    }

    @Test
    void deleteSubTaskByIdTest() throws IOException {
        HttpTaskServer serv = new HttpTaskServer(manager);
        serv.start();
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), 0L, dateTime.minusHours(1));
        manager.addNewSubTask(subtask);
        manager.deleteSubTaskById(subtask.getId());
        assertTrue(manager.getSubTasks().isEmpty(), "Сабтаски не удаляются");
        serv.stop();
    }

    @Test
    void deleteEpicByIdTest() throws IOException {
        HttpTaskServer serv = new HttpTaskServer(manager);
        serv.start();
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        manager.deleteEpicById(epic.getId());
        assertTrue(manager.getEpics().isEmpty(), "Эпики не удаляются");
        serv.stop();
    }
}