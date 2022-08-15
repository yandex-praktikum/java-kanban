import Manager.Managers;
import Manager.TaskManager;
import Task.Epic;
import Task.Status;
import Task.SubTask;
import Task.Task;

//public class Main {
/*

    Способ чтения : Files.readString(Path.of(path));

   4. Исключения вида IOException нужно отлавливать внутри метода save и кидать собственное
    непроверяемое исключение ManagerSaveException (класс с исключением нужно создать)

   5. Создать статический метод в FileBackedTasksManager static loadFromFile(File file),
    который будет восстанавливать данные менеджера из файла при запуске программы.


  1.  если айдишник назначается в  addTask - то это пробелма.
сложно скзаать, не видя код, но мне кажется, самое просто будет, это проверять - если айдишник есть у таски,
 то не присваивать его. Это костыль,
2.
static String historyToString(HistoryManager manager
этот метод просто выбирает все айдишники из истории и записывает их в строку.
типа, у нас были таски в истории с айдишниками 1,2,3
вот это тметод превартил этот менеджер истории в строчку
"1,2,3"
static List<Integer> historyFromString(String value)
сделает обратно.  потом по этим айдишникам надо найти таски с этими айдишниками и сохранить их в historyManager
т.е. что то вроде
... достаем из файла сначала таски и сохраняем в менеджер.
потом по сроке "1,2,3"
taskManager.historyManager.add(taskManager.findTask(taskId));
идея, я думаю понятна.
3.
"мне не понятно, как я могу восстановить данные менеджера,
 запуская static FileBackedTasksManager loadFromFile(File file)"
тут важный момент - менеджер сохраняет таски в файлы только по операции get.
Поэтому здесь делаем так -
создаем новый менеджер
public static FileBackedTaskManager loadFromFile(File file) {
   final FileBackedTaskManager taskManager = new FileBackedTaskManager(file);
далее считываем таски и
final Task task = читаем из файла строчку
taskManager.addTask(task);
считываем из файла таск.
"+не могу в нем запускать методы по добавлению тасков"
можешь, это же статический метод  и у тебя есть экземпляр вновь созданного менеджера -вот на нем и надо вызывать методы
 */




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
    }
}*/