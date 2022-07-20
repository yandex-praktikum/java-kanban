package Main;

import Manager.Managers;
import Manager.TaskManager;
import Task.Epic;
import Task.Status;
import Task.SubTask;
import Task.Task;

public class Main {
    /*Привет, ревьюер! Я тут реализовал CustomLinkedList в классе InMemoryHistoryManager и вот что из этого получилось:
      1. Добавил класс Node для хранения узлов.
      2. Добавил метод linkLast, получился как addLast из LinkedList(надеюсь)
      3. Добавил метод, который передаёт получившийся CustomLinkedList в обычный лист.
      4. Добавил мапу nodeData, хранящую узлы по айди(прям как LinkedHashMap)
      Вроде всё работает, но смущает момент, что перезаписаные узлы в CustomLinkedList не удаляются, хотел прописать
      условие в linkLast чтобы перезаписать ссылки на prev и next у перезаписанного узла, но не уверен что это нужно
      и как правильно делать по тз.
      С нетерпением жду комментариев! :)
     */

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
        manager.getEpicById(20);
        System.out.println(manager.getHistory());
        manager.getTaskById(10);
        manager.getEpicById(21);
        manager.getEpicById(21);
        System.out.println(manager.getHistory());
    }
}