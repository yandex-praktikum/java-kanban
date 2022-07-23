package Main;

import Manager.Managers;
import Manager.TaskManager;
import Task.Epic;
import Task.Status;
import Task.SubTask;
import Task.Task;

public class Main {

    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();
        Task task = new Task("Погулять", 0, "Оделся, пошёл", Status.NEW);
        Task task1 = new Task("Вернуться", 0, "Зашёл, разделся", Status.NEW);
        manager.addNewTask(task);
        manager.addNewTask(task1);
        Epic epic = new Epic("Обед", 0, "Котлетки с пюрешкой и салатиком");
        manager.addNewEpic(epic);
        SubTask subTask = new SubTask("Котлетки", 0, "Жарим", Status.NEW, epic.getId());
        SubTask subTask1 = new SubTask("Пюрешка", 0, "Мнём", Status.NEW, epic.getId());
        SubTask subTask2 = new SubTask("Салатик", 0, "Режем, заправляем", Status.NEW, epic.getId());
        manager.addNewSubTask(subTask);
        manager.addNewSubTask(subTask1);
        manager.addNewSubTask(subTask2);
        Epic epic1 = new Epic("Создать кастомлинкедлист", 0, "import java.util.LinkedHashMap");
        manager.addNewEpic(epic1);
        System.out.println(manager.getEpic());
        System.out.println(manager.getTask());
        System.out.println(manager.getSubTask());
        manager.getEpicById(20);
        System.out.println(manager.getHistory());
        manager.getEpicById(21);
        System.out.println(manager.getHistory());
        manager.getEpicById(20);
        manager.getEpicById(21);
        System.out.println(manager.getHistory());
        manager.getSubTaskById(30);
        manager.getSubTaskById(31);
        manager.getSubTaskById(32);
        System.out.println(manager.getHistory());
        manager.deleteEpicById(20);
        System.out.println(manager.getHistory());
        manager.deleteEpicById(21);
        System.out.println(manager.getHistory());
        manager.getTaskById(10);
        System.out.println(manager.getHistory());
        manager.deleteTaskById(10);
        System.out.println(manager.getHistory());
    }
}