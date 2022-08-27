
public class Main {
/*
   С чего начать:

   1. Добавить дату старта и продолжительность таскам:  ++++++++++++
   Новые поля:
   duration - продолжительность таски
   startTime - дата старта
   getEndTime() - дата окончания исходя из старта и продолжительности
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

   Для эпиков:
   все поля расчитываются как статус в отдельном методе
   duration - сумма продолжительности всех сабтасок
   startTime - время старта самой ранней сабтаски
   endTime - время окончания самой поздней
   getEndTime() - расчитывает время окончания
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

   ______________Всё это записать в csv______________

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

   2. Добавить приоритет задач по времени старта:
   Сортировка по полю startTime(если время не задано, в конец списка)
   Вынесена в метод getPrioritizedTasks и хранится в TreeSet

   Добавить в методы создания и изменения проверку, что даты не совпали

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


   3. Покрыть код тестами(все паблик методы, по 1 тесту на метод)
   1) Для расчёта статуса Epic. Граничные условия:
   a. Пустой список подзадач.
   b. Все подзадачи со статусом NEW.
   c. Все подзадачи со статусом DONE.
   d. Подзадачи со статусами NEW и DONE.
   e. Подзадачи со статусом IN_PROGRESS.

   2) Для двух менеджеров задач InMemoryTasksManager и FileBackedTasksManager.
   Чтобы избежать дублирования кода, необходим базовый класс с тестами на каждый метод из интерфейса
   abstract class TaskManagerTest<T extends TaskManager>.
   Для подзадач нужно дополнительно проверить наличие эпика, а для эпика — расчёт статуса.
   Для каждого метода нужно проверить его работу:
   a. Со стандартным поведением.
   b. С пустым списком задач.
   c. С неверным идентификатором задачи (пустой и/или несуществующий идентификатор).

   3) Для HistoryManager — тесты для всех методов интерфейса. Граничные условия:
   a. Пустая история задач.
   b. Дублирование.
   с. Удаление из истории: начало, середина, конец.
   Дополнительно для FileBackedTasksManager — проверка работы по сохранению и восстановлению состояния. Граничные условия:
   a. Пустой список задач.
   b. Эпик без подзадач.
   c. Пустой список истории.
   После написания тестов ещё раз проверьте их наличие по списку. Убедитесь, что они работают.


     Пример теста от Практикума :
     По задачам
     @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW);
        final int taskId = taskManager.addNewTask(task);

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }
    По истории
    @Test
    void add() {
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

* */
   /* public static void main(String[] args) {

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
    }*/
}