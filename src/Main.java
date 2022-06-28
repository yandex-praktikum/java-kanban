public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task1 = manager.addTask("Задача1", "Описание задачи1, id = 1");
        Task task2 = manager.addTask("Задача2", "Описание задачи2, id = 2");
        Epic epic1 = manager.addEpic("Эпик1", "Описание эпика1, id = 3");
        Subtask subtask1 = manager.addSubtask("Подзадача1 эпика 1", "Описание подзадачи1, id = 4", 3);
        Subtask subtask2 = manager.addSubtask("Подзадача2 эпика 1", "Описание подзадачи2, id = 5",  3);
        Epic epic2 = manager.addEpic("Эпик2", "Описание эпика2, id = 6");
        Subtask subtask3 = manager.addSubtask("Подзадача1 эпика 2", "Описание подзадачи3, id = 7",  6);
        manager.updateTask(task1, Status.IN_PROGRESS);
        manager.updateTask(task2, Status.DONE);
        manager.updateEpic(epic1);
        manager.updateEpic(epic2);
        manager.updateSubtask(subtask1, Status.IN_PROGRESS);
        manager.updateSubtask(subtask2, Status.DONE);
        manager.updateSubtask(subtask3, Status.DONE);
        manager.printTasks();
        manager.printEpics();
        manager.printSubtasks();
        manager.printSubtasksOfEpic(3);
        manager.removeTaskById(1);
        manager.printTasks();
        manager.removeSubtaskById(4);
        manager.printEpics();
    }
}
