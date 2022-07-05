package Main;

import Manager.Managers;
import Manager.TaskManager;
import Task.Epic;
import Task.Status;
import Task.SubTask;

public class Main {
    /*Привет, это снова я! :) Наконец-то выяснил, что все типы задач могут быть IN_PROGRESS, предыдущий ревьюер это
    * видимо упустил.
    * 1. Логику подсчёта статуса эпика исправил, проверил, вроде работает корректно.
    * 2. Менеджер создал через Managers, вернул тесты в main.
    * 3. Модификаторы доступа поправил.
    * 4. Геттеры по id поправил.
    * 5. С отступами разобрался.
    *
    *  Есть вопросы по этому комментарию: "Получения значения эпика из мапы "epicData.get(epicId)" можно присвоить
    *  какой-то переменной и не дублировать код)"
    *  Не понимаю зачем мне там переменная, если я меняю статус конкретному эпику, который вот - epicData.get(epicId)
    *  Или имеется ввиду что метод создаёт новый эпик, присваивает ему новый статус, удаляет старый эпик из мапы
    *  и возвращает на его место то, что создал? Или я упустил какую-то идею? Нужны подробности :)
    *  Ещё  сбивает с толку комментарий предыдущего ревьюера по этому методу: "Метод должен обновлять статус у Эпика,
    *  в качестве параметра примем id Эпика. Из мапы получим эпик по id, у этого эпика нам нужен список subtaskIds
    *  (тут мы должны получить сабтаски именно для этого эпика, а не все, лежащие в мапе subTaskData) Если он пустой -
    *  - то статус new Дальше итерируемся по списку SubtaskIds, смотрим, если статус у всех Done - то и эпик Done,
    *  если статус у всех new - то и эпик new, во всех остальных случаях in_progress. И сразу в методе через сеттер
    *  устанавливаем статус эпику, в таком случае нет необходимости возвращать что-либо." */

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