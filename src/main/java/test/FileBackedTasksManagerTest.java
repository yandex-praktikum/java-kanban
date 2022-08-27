package test;

import manager.ManagerSaveException;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.SubTask;
import task.Task;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

abstract class TaskManagerTests<T extends TaskManager> {
    protected T taskManager;
    protected Task task;
    protected Epic epic;
    protected SubTask subtask;
    protected LocalDateTime dateTime = LocalDateTime.of(2022, 8, 26, 12, 0, 0);

    protected void initTasks() throws ManagerSaveException, IOException {
        task = new Task("Погулять", 0, "Оделся и пошёл", Status.NEW,
                Duration.ofHours(1), dateTime);
        taskManager.addNewTask(task);
        epic = new Epic("Обед", 0, "Котлетки с пюрешкой и салатиком",
                Duration.ofHours(0), dateTime.minusHours(10), dateTime.minusHours(9));
        taskManager.addNewEpic(epic);
        subtask = new SubTask("Котлетки", 0, "Жарим", Status.NEW,
                epic.getId(), Duration.ofHours(1), dateTime.minusHours(1));
        taskManager.addNewSubTask(subtask);

    }

    @Test
    void getTask() {
        final List<Task> tasks = taskManager.getTask();

        assertNotNull(tasks, "Задачи на возвращаются");
        assertEquals(1, tasks.size(), "Не верное количество задач");
        assertEquals(task, tasks.get(0), "Задачи не совпадают");
    }
}