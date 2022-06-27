public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.addTask("Задача1", "Описание задачи1", Status.NEW);
        manager.addTask("Задача2", "Описание задачи2", Status.DONE);
        manager.printTasks();
        manager.removeAllTasks();
        manager.printTasks();
    }
}
