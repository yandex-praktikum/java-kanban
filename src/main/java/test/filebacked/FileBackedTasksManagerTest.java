package test.filebacked;

import manager.FileBackedTasksManager;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.SubTask;
import task.Task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class  FileBackedTasksManagerTest {

    File file = new File("historyTest.csv");
    FileBackedTasksManager manager = new FileBackedTasksManager(file);
    LocalDateTime dateTime = LocalDateTime.of(2022, 8, 26,
            12, 0, 0);

    @Test
    void addNewTask() { // проверка создания, добавления, удаления и изменения таски
        Task task = new Task("Погулять", 1, "Оделся и пошёл",Status.NEW,
                Duration.ofHours(1), dateTime);

        final int id = manager.addNewTask(task);

        final List<Task> tasks = manager.getTask();

        assertNotNull(tasks, "Задачи на возвращаются");
        assertEquals(task, tasks.get(0), "Задачи не совпадают");
        assertEquals(id, task.getId(), "Айди сохраняется некорректно");
        task.setName("Протестировать");
        manager.updateTask(task);
        assertEquals(task.getName(), "Протестировать", "Таски не обновляются");
        manager.deleteAllTasks();
        assertTrue(manager.getTask().isEmpty(), "Таски не удаляются");
    }

    @Test
    void addNewEpic() { // проверка создания, добавления и удаления эпика
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                Duration.ofHours(0), dateTime.minusHours(10), dateTime.minusHours(9));
        final int id = manager.addNewEpic(epic);

        final List<Epic> epics = manager.getEpic();

        assertNotNull(epics, "Задачи на возвращаются");
        assertEquals(epic, epics.get(0), "Задачи не совпадают");
        assertEquals(id, epic.getId(), "Айди сохраняется некорректно");
        epic.setName("Протестировать");
        manager.updateEpic(epic);
        assertEquals(epic.getName(), "Протестировать", "Эпики не обновляются");
        manager.deleteAllEpics();
        assertTrue(manager.getEpic().isEmpty(), "Эпики не удаляются");

    }

    @Test
    void addNewSubTaskAndCheckAllEpicChanges() { // все проверки на взаимодействие сабов и эпиков
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                Duration.ofHours(0), dateTime.minusHours(10), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), Duration.ofHours(1), dateTime.minusHours(1));

        final int id = manager.addNewSubTask(subtask);

        final List<SubTask> subs = manager.getSubTask();

        assertNotNull(subs, "Задачи на возвращаются");
        assertEquals(subtask, subs.get(0), "Задачи не совпадают");
        assertEquals(id, subtask.getId(), "Айди сохраняется некорректно");
        manager.findEpicTime(subtask.getEpicId());
        assertEquals(epic.getDuration(), subtask.getDuration(), "Продолжительность эпика не меняется");
        assertEquals(epic.getId(), subtask.getEpicId(), "Айди эпика не присвоен сабтаске");
        subtask.setName("Протестировать");
        manager.updateSubTask(subtask);
        assertEquals(subtask.getName(), "Протестировать", "Таски не обновляются");
        final List<SubTask> epicSubs = manager.getEpicSubTasks(subtask.getEpicId());
        assertNotNull(epicSubs, "Сабтаски не возвращаются по эпик айди");

        SubTask subtask1 = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), Duration.ofHours(1), dateTime.minusHours(2));
        manager.addNewSubTask(subtask1);
        assertEquals(epic.getStatus(), Status.NEW, "Статус эпика не пересчитывается"); //все NEW
        subtask.setStatus(Status.DONE);
        manager.findEpicStatus(subtask.getEpicId());
        assertEquals(epic.getStatus(), Status.IN_PROGRESS, "Статус эпика не пересчитывается");// 1 NEW 1 DONE
        subtask1.setStatus(Status.DONE);
        manager.findEpicStatus(subtask.getEpicId());
        assertEquals(epic.getStatus(), Status.DONE, "Статус эпика не пересчитывается");// все DONE
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask.setStatus(Status.IN_PROGRESS);
        manager.findEpicStatus(subtask.getEpicId());
        assertEquals(epic.getStatus(), Status.IN_PROGRESS, "Статус эпика не пересчитывается");//все IN_PROGRESS
        manager.deleteAllSubTasks();
        assertTrue(manager.getSubTask().isEmpty(), "Сабтаски не удаляются");

    }

    @Test
    void taskTimeAndPrioritizedTimeCheck() { // проверки времени
        Task task = new Task("Погулять", 1, "Оделся и пошёл",Status.NEW,
                Duration.ofHours(1), dateTime);
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                Duration.ofHours(0), dateTime.minusHours(1), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), Duration.ofHours(1), dateTime.minusHours(2));
        manager.addNewTask(task);
        manager.addNewSubTask(subtask);
        List<Task> prioritized = manager.getPrioritizedTasks();

        manager.getPrioritizedTasks();
        assertEquals(prioritized.get(2), task, "Таски не отсортированы по времени");
        assertEquals(prioritized.get(1), epic, "Таски не отсортированы по времени");
        assertEquals(prioritized.get(0), subtask, "Таски не отсортированы по времени");


    }
    @Test
    void taskTimeExceptionCheck() { // проверка пересечений дат
        Task task = new Task("Погулять", 1, "Оделся и пошёл",Status.NEW,
                Duration.ofHours(1), dateTime);
        Task task1 = new Task("Погулять", 1, "Оделся и пошёл",Status.NEW,
                Duration.ofHours(1), dateTime);
        manager.addNewTask(task);
        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.addNewTask(task1)
        );
        assertEquals("Задача с таким временем уже существует." + dateTime, exception.getMessage());

    }

    @Test
    void historySaveAndPrintCheck() { // проверка истории
        Task task = new Task("Погулять", 1, "Оделся и пошёл",Status.NEW,
                Duration.ofHours(1), dateTime);
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                Duration.ofHours(0), dateTime.minusHours(1), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), Duration.ofHours(1), dateTime.minusHours(2));
        manager.addNewTask(task);
        manager.addNewSubTask(subtask);

        manager.getSubTaskById(subtask.getId());
        manager.getTaskById(task.getId());
        manager.getEpicById(epic.getId());

        LinkedList<Task> tasks = new LinkedList<>();

        tasks.add(epic);
        tasks.add(task);
        tasks.add(subtask);
        assertNotNull(manager.getHistory(), "История не сохраняется");
        assertEquals(tasks, manager.getHistory(), "Порядок вызовов не сохраняется");

        manager.deleteSubTaskById(subtask.getId());
        tasks.remove(subtask); // удалил с конца
        assertEquals(tasks, manager.getHistory(), "Порядок вызовов не сохраняется");


        manager.deleteTaskById(task.getId()); // удалил из середины
        tasks.remove(task);
        assertEquals(tasks, manager.getHistory(), "Порядок вызовов не сохраняется");


    }


    @Test
    void loadFromCSVCheck() {
        Task task = new Task("Погулять", 1, "Оделся и пошёл",Status.NEW,
                Duration.ofHours(1), dateTime);
        Task task1 = new Task("Погулять", 1, "Оделся и пошёл",Status.NEW,
                Duration.ofHours(1), dateTime.minusHours(1));
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                Duration.ofHours(0), dateTime.minusHours(1), dateTime.minusHours(9));
        File testFile = new File("test.csv");


        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FileBackedTasksManager.loadFromFile(testFile)
        );

        assertEquals("Файл не найден", exception.getMessage()); // пустой файл

        manager.addNewTask(task1);
        manager.addNewTask(task);
        manager.addNewEpic(epic); // эпик без сабтасок

        FileBackedTasksManager newManager = FileBackedTasksManager.loadFromFile(file);
        assertEquals(manager.getTask(), newManager.getTask(), "Список задач не совпадает"); //файл без истории

        manager.getTaskById(task.getId());
        manager.getTaskById(task1.getId());
        newManager = FileBackedTasksManager.loadFromFile(file);
        assertNotNull(newManager.getHistory(), "История не восстановлена"); //файл с историей
    }
}