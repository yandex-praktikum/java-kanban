public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task1 = new Task("taskName1", "subscribe1", "NEW");
        Task task2 = new Task("taskName2", "subscribe2", "NEW");
        manager.taskMaker(task1);
        manager.taskMaker(task2);

        Epic epic1 = new Epic("epic1", "epicsubscribe1");
        Epic epic2 = new Epic("epic2", "epicsubscribe2");
        Epic epic3 = new Epic("epic3", "epicsubscribe3");
        manager.epicMaker(epic1);
        manager.epicMaker(epic2);
        manager.epicMaker(epic3);

        SubTask sub1 = new SubTask("subname1", "subDescribe1", "DONE"); // ID эпика сначала формируется в эпике, потом передается в саб
        SubTask sub2 = new SubTask("subname2", "subDescribe2", "DONE");
        SubTask sub3 = new SubTask("subname3", "subDescribe3", "DONE");
        SubTask sub4 = new SubTask("subname4", "subDescribe4", "NEW");
        SubTask sub5 = new SubTask("subname5", "subDescribe5", "DONE");
        SubTask sub6 = new SubTask("subname6", "subDescribe6", "NEW");
        SubTask sub7 = new SubTask("subname7", "subDescribe7", "NEW");
        manager.subMaker(sub1, epic1.getId());
        manager.subMaker(sub2, epic1.getId());
        manager.subMaker(sub3, epic3.getId());

        System.out.println("getEpicId from sub: " + sub1.getEpicId() + " " + sub2.epicId);
        System.out.println("Эпик ID: " + epic1.getId());
        System.out.println("Статус эпика1: " + manager.epicStatusMaker(manager.getSubTaskHashMap(), epic1));
        System.out.println("Размер ХМ саб: " + manager.getSubTaskHashMap().size());
        System.out.println("Статус саба1: " + sub2.getStatus());
        manager.subMaker(sub4, epic3.getId());
        manager.subMaker(sub5, epic2.getId());
        manager.subMaker(sub6, epic2.getId());
        manager.subMaker(sub7, epic2.getId());
        System.out.println("Статус эпика1: " + manager.epicStatusMaker(manager.getSubTaskHashMap(), epic1));

        manager.toStringAll(manager.getTaskHashMap());
//        manager.toStringAll(manager.getEpicHashMap());
//        manager.toStringAll(manager.getSubTaskHashMap());

//        System.out.println(epic1.getIdSubtask());
//        System.out.println(manager.getEpicsSubtasks(epic2));

    }
}

