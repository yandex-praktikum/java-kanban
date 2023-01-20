import models.Status;
import models.Subtask;
import models.Epic;
import models.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task = new Task("Задачка для удаления", "удалим чуть позже", Status.fresh);
        taskManager.createTask(task);


        Epic epic = new Epic("Япрактикум", "Сделать тз так, чтобы ревьюер посмотрел и сказал:" +
                " (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧ это щиииикааарно!");
        Subtask subtask = new Subtask("Прочитать тз", "Закрыть тз");
        Subtask subtask1 = new Subtask("Мозговой штурм", "Ищем пути и методы");

        taskManager.createEpic(epic);

        taskManager.createSubtask(epic, subtask);
        taskManager.createSubtask(epic, subtask1);

        Epic epic1 = new Epic("Готовим блинчики...", "вкусные");
        Subtask subtask2 = new Subtask("Заказать в ЯндексЕде", "СРОЧНО");

        taskManager.createEpic(epic1);

        taskManager.createSubtask(epic1, subtask2);


        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getTask(0));
        taskManager.finishTask(0);
        System.out.println(taskManager.getTask(0));
        taskManager.deleteTask(0);
        System.out.println(taskManager.getTasks());

        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());
        taskManager.finishTask(3);
        System.out.println(taskManager.getEpics());
        taskManager.finishTask(2);
        System.out.println(taskManager.getEpics(1));
        taskManager.deleteSubtask(3);
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());
        System.out.println(taskManager.getSubtasksEpic(1));
        System.out.println(taskManager.getEpics());
        taskManager.deleteEpic(1);
        System.out.println(taskManager.getEpics());
        taskManager.finishTask(5);
        System.out.println(taskManager.getEpics());

        taskManager.deleteSubtasks();
        taskManager.deleteEpics();
    }
}