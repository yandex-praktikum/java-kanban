import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Manager {

    private static int id = 0;

    private HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    private HashMap<Integer, SubTask> subTaskHashMap = new HashMap<>();

    public HashMap<Integer, SubTask> getSubTaskHashMap() {
        return subTaskHashMap;
    }

    public HashMap<Integer, Task> getTaskHashMap() {
        return taskHashMap;
    }

    public HashMap<Integer, Epic> getEpicHashMap() {
        return epicHashMap;
    }


    public void taskMaker(Task task) {  // при создании статус "NEW"
        id++;
        task.setId(id);
        task.setStatus("NEW");
        taskHashMap.put(id, task);
    }

    public void epicMaker(Epic epic) {
        id++;
        epic.setId(id);
        epic.setIdSubtask(epic.getIdSubtask());
        epicHashMap.put(id, epic);
        epic.setStatus(epicStatusMaker(subTaskHashMap, epic));
    }

    public void subMaker(SubTask subTask, Integer epicId) {
        id++;
        subTask.setId(id);
        subTask.setEpicId(epicId);
        epicHashMap.get(subTask.getEpicId()).getIdSubtask().add(id); // добавление в
        subTaskHashMap.put(id, subTask);
    }


    String epicStatusMaker(HashMap<Integer, SubTask> subTaskHashMap, Epic epic) {
        int countNew = 0;
        int countDone = 0;
        String epicStatus = "";

        ArrayList<String> epicStatAL = new ArrayList<>();

        for (SubTask subTask : subTaskHashMap.values()) {
            if (subTask.getEpicId() == epic.getId()) {
                epicStatAL.add(subTask.getStatus());
            }
        }
        for (String substat : epicStatAL) {
            if (substat.equals("NEW")) {
                countNew++;
            }
            if (substat.equals("DONE")) {
                countDone++;
            }
        }
        if (epicStatAL.size() == countNew) {
            epicStatus = "NEW";
        } else if (epicStatAL.size() == countDone) {
            epicStatus = "DONE";
        } else {
            epicStatus = "IN_PROGRESS";
        }
        return epicStatus;
    }

    // Получение списка всех элементов
    public <E> void toStringAll(HashMap<Integer, E> allTask) {
        allTask.forEach((key, value) -> System.out.println(value.toString()));
    }

    // Удаление всех задач
    public <E> void removeTasks(HashMap<Integer, E> allTask) {
        allTask.clear();
    }

    // Получение по идентификатору
    public <E> E getTask(HashMap<Integer, E> allTasks, Integer id) {
        return allTasks.get(id);
    }

    //    Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    public <E> void update(HashMap<Integer, E> task, E e, Integer id) {
        if (task.containsKey(id)) {
            task.replace(id, e);
        } else {
            System.out.println("Предложен объект с несуществующим id");
        }
    }

    // Удаление по идентификатору
    public <E> void removeTask(HashMap<Integer, E> allTasks, Integer id) {
        if (allTasks.containsKey(id)) {
            allTasks.remove(id);
        } else {
            System.out.println("Предложен объект с несуществующим id");
        }
    }

    // Получение списка всех подзадач определённого эпика
    public ArrayList<Integer> getEpicsSubtasks(Epic epic) {
//        ArrayList<SubTask> epicsSubtasks = new ArrayList<>();
//        for (Integer id : epic.getIdSubtask()) {
//            epicsSubtasks.add(subTaskHashMap.get(id));
//        }
        // в стриме
        List<Integer> epicsSubtasksStream = epic.getIdSubtask().stream().toList();
        return new ArrayList<>(epicsSubtasksStream);
    }


}
