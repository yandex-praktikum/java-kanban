package test.filebacked;

import manager.managers.FileBackedTasksManager;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Task;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class  FileBackedTasksManagerTest {

    File file = new File("historyTest.csv");
    FileBackedTasksManager manager = new FileBackedTasksManager(file);
    LocalDateTime dateTime = LocalDateTime.of(2022, 8, 26,
            12, 0, 0);

    @Test
    void loadFromCSVCheck() {
        Task task = new Task("Погулять", 1, "Оделся и пошёл",Status.NEW,
                0L, dateTime);
        Task task1 = new Task("Погулять", 1, "Оделся и пошёл",Status.NEW,
                0L, dateTime.minusHours(2));
        Epic epic = new Epic("Обед", 10, "Котлетки с пюрешкой и салатиком",
                0L, dateTime.minusHours(1), dateTime.minusHours(9));
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
        assertEquals(manager.getTasks(), newManager.getTasks(), "Список задач не совпадает"); //файл без истории

        manager.getTaskById(task.getId());
        manager.getTaskById(task1.getId());
        newManager = FileBackedTasksManager.loadFromFile(file);
        assertNotNull(newManager.getHistory(), "История не восстановлена"); //файл с историей
    }
}