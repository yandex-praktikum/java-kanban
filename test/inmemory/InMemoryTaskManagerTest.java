package test.inmemory;

import manager.managers.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.SubTask;
import task.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
class InMemoryTaskManagerTest {

    InMemoryTaskManager manager = new InMemoryTaskManager();
    LocalDateTime dateTime = LocalDateTime.of(2022, 8, 26,
            12, 0, 0);

    @Test
    void addNewTaskTest() {
        Task task = new Task("Погулять", 1, "Оделся и пошёл", Status.NEW,
                0L, dateTime);
        manager.addNewTask(task);
        final List<Task> tasks = manager.getTasks();
        assertNotNull(tasks, "Задачи на возвращаются");
        assertEquals(task, tasks.get(0), "Задачи не совпадают");
    }


    @Test
    void addNewEpicTest() {
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        final List<Epic> epics = manager.getEpics();
        assertNotNull(epics, "Задачи на возвращаются");
        assertEquals(epic, epics.get(0), "Задачи не совпадают");
    }

    @Test
    void addNewSubTaskTest() {
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
    }

    @Test
    void getTaskByIdTest() {
        Task task = new Task("Погулять", 1, "Оделся и пошёл", Status.NEW,
                0L, dateTime);
        final int id = manager.addNewTask(task);
        assertEquals(id, task.getId(), "Айди сохраняется некорректно");
    }

    @Test
    void getSubTaskByIdTest() {
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), 0L, dateTime.minusHours(1));
        final int id = manager.addNewSubTask(subtask);
        assertEquals(id, subtask.getId(), "Айди сохраняется некорректно");
    }

    @Test
    void getEpicByIdTest() {
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        final int id = manager.addNewEpic(epic);
        assertEquals(id, epic.getId(), "Айди сохраняется некорректно");
    }

    @Test
    void deleteAllTasksTest() {
        Task task = new Task("Погулять", 1, "Оделся и пошёл", Status.NEW,
                0L, dateTime);
        manager.addNewTask(task);
        manager.deleteAllTasks();
        assertTrue(manager.getTasks().isEmpty(), "Таски не удаляются");
    }

    @Test
    void deleteAllSubTasksTest() {
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), 0L, dateTime.minusHours(1));
        manager.addNewSubTask(subtask);
        manager.deleteAllSubTasks();
        assertTrue(manager.getSubTasks().isEmpty(), "Сабтаски не удаляются");
    }

    @Test
    void deleteAllEpicsTest() {
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        manager.deleteAllEpics();
        assertTrue(manager.getEpics().isEmpty(), "Эпики не удаляются");
    }

    @Test
    void deleteTaskByIdTest() {
        Task task = new Task("Погулять", 1, "Оделся и пошёл", Status.NEW,
                0L, dateTime);
        manager.addNewTask(task);
        manager.deleteTaskById(task.getId());
        assertTrue(manager.getTasks().isEmpty(), "Таски не удаляются");
    }

    @Test
    void deleteSubTaskByIdTest() {
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), 0L, dateTime.minusHours(1));
        manager.addNewSubTask(subtask);
        manager.deleteSubTaskById(subtask.getId());
        assertTrue(manager.getSubTasks().isEmpty(), "Сабтаски не удаляются");
    }

    @Test
    void deleteEpicByIdTest() {
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        manager.deleteEpicById(epic.getId());
        assertTrue(manager.getEpics().isEmpty(), "Эпики не удаляются");
    }

    @Test
    void updateTaskTest() {
        Task task = new Task("Погулять", 1, "Оделся и пошёл", Status.NEW,
                0L, dateTime);
        manager.addNewTask(task);
        task.setName("Протестировать");
        manager.updateTask(task);
        assertEquals(task.getName(), "Протестировать", "Таски не обновляются");
    }

    @Test
    void updateSubTaskTest() {
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), 0L, dateTime.minusHours(1));
        manager.addNewSubTask(subtask);
        subtask.setName("Протестировать");
        manager.updateSubTask(subtask);
        assertEquals(subtask.getName(), "Протестировать", "Таски не обновляются");
    }

    @Test
    void updateEpicTest() {
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        epic.setName("Протестировать");
        manager.updateEpic(epic);
        assertEquals(epic.getName(), "Протестировать", "Эпики не обновляются");
    }

    @Test
    void getEpicSubTasksTest() {
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), 0L, dateTime.minusHours(1));
        manager.addNewSubTask(subtask);
        final List<SubTask> epicSubs = manager.getEpicSubTasks(subtask.getEpicId());
        assertNotNull(epicSubs, "Сабтаски не возвращаются по эпик айди");
    }

    @Test
    void getPrioritizedTasksTest() {
        Task task = new Task("Погулять", 1, "Оделся и пошёл",Status.NEW,
                0L, dateTime);
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(1), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), 0L, dateTime.minusHours(2));
        manager.addNewTask(task);
        manager.addNewSubTask(subtask);
        TreeSet<Task> prioritized = manager.getPrioritizedTasks();
        Task firstTask = prioritized.first();
        Task lastTask = prioritized.last();

        assertEquals(lastTask, task, "Таски не отсортированы по времени");
        assertEquals(3, prioritized.size(), "Таски не отсортированы по времени"); // в середине есть
        assertEquals(firstTask, subtask, "Таски не отсортированы по времени");
    }

    @Test
    void taskDateValidationTest() {
        Task task = new Task("Погулять", 1, "Оделся и пошёл",Status.NEW,
                0L, dateTime);
        Task task1 = new Task("Погулять", 1, "Оделся и пошёл",Status.NEW,
                0L, dateTime);
        manager.addNewTask(task);
        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.addNewTask(task1)
        );
        assertEquals("Задача с таким временем уже существует." + dateTime, exception.getMessage());
    }

    @Test
    void epicTimeCalculationTest() {
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), 0L, dateTime.minusHours(1));
        manager.addNewSubTask(subtask);
        manager.epicTimeCalculation(subtask.getEpicId());
        assertEquals(epic.getDuration(), subtask.getDuration(), "Продолжительность эпика не меняется");
    }

    @Test
    void epicStatusCalculationTest() {
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), 0L, dateTime.minusHours(1));
        manager.addNewSubTask(subtask);
        SubTask subtask1 = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), 0L, dateTime.minusHours(3));
        manager.addNewSubTask(subtask1);
        assertEquals(epic.getStatus(), Status.NEW, "Статус эпика не пересчитывается"); //все NEW
        subtask.setStatus(Status.DONE);
        manager.epicStatusCalculation(subtask.getEpicId());
        assertEquals(epic.getStatus(), Status.IN_PROGRESS, "Статус эпика не пересчитывается");// 1 NEW 1 DONE
        subtask1.setStatus(Status.DONE);
        manager.epicStatusCalculation(subtask.getEpicId());
        assertEquals(epic.getStatus(), Status.DONE, "Статус эпика не пересчитывается");// все DONE
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask.setStatus(Status.IN_PROGRESS);
        manager.epicStatusCalculation(subtask.getEpicId());
        assertEquals(epic.getStatus(), Status.IN_PROGRESS, "Статус эпика не пересчитывается");//все IN_PROGRESS
    }

}