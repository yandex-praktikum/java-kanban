package Main;

import Manager.Managers;
import Manager.TaskManager;
import Task.Epic;
import Task.Status;
import Task.SubTask;

public class Main {
    /*1. Избавиться от повторных просмотров в ней и ограничения на размер истории.
    * (Перенести историю в LinkedHashSet)
    *
    * 2. Добавить в HistoryManager метод void remove(int id), который будет чистить историю при удалении тасок.
    * (Реализовать в InMemoryHistoryManager)
    *
    * 3. Добавить список, хранящий порядок вызова метода add.
    * (Храниться история должна по порядку и в единственном экземпляре(LinkedList)
    *
    * 4. Сначала напишите свою реализацию двусвязного списка задач с методами linkLast и getTasks.
    *  linkLast будет добавлять задачу в конец этого списка, а getTasks собирать все задачи из него в обычный ArrayList.
    *  Убедитесь, что решение работает. Отдельный класс для списка создавать не нужно — реализуйте его прямо в классе
    *  InMemoryHistoryManager. А вот отдельный класс Node для узла списка необходимо добавить.
    *
    * 5. CustomLinkedList это: 1) Сам LinkedList с методами linkLast и getTasks
    *                          2) HashMap для хранения id и Объектов - узлов
    *                          3) Класс Node для хранения узлов
    *                          4) ArrayList для формирования ответа на запрос по истории */

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        Epic epic = new Epic("Обед", 0, "Котлетки с пюрешкой");
        manager.addNewEpic(epic);
        System.out.println(manager.getEpic());
        SubTask subTask = new SubTask("Котлетки", 0, "Жарим", Status.DONE, epic.getId());
        SubTask subTask1 = new SubTask("Пюрешка", 0, "Мнём", Status.DONE, epic.getId());
        manager.addNewSubTask(subTask);
        manager.addNewSubTask(subTask1);
        System.out.println(manager.getSubTask());
        SubTask subTask2 = new SubTask("Пюрешка", 0, "Мнём", Status.IN_PROGRESS, epic.getId());
        manager.addNewSubTask(subTask2);
        manager.getEpicById(1);
        System.out.println(manager.getHistory());
        manager.getSubTaskById(1);
        System.out.println(manager.getHistory());
    }
}