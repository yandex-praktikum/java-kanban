package test.history;

import manager.managers.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.SubTask;
import task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    InMemoryTaskManager manager = new InMemoryTaskManager();
    LocalDateTime dateTime = LocalDateTime.of(2022, 8, 26,
            12, 0, 0);


    @Test
    void historySaveAndPrintCheck() { // проверка истории
        Task task = new Task("Погулять", 1, "Оделся и пошёл", Status.NEW,
                0L, dateTime);
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(1), dateTime.minusHours(9));
        manager.addNewEpic(epic);
        SubTask subtask = new SubTask("Котлетки", 100, "Жарим", Status.NEW,
                epic.getId(), 0L, dateTime.minusHours(2));
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
}