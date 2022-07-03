package Manager;

import Task.Epic;
import Task.SubTask;
import Task.Task;
import Task.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager<T extends Task> implements TaskManager {
    private int taskId = 1;
    private int epicId = 1;
    private int subTaskId = 1;
    private final HashMap<Integer, Task> taskData = new HashMap<>();
    private final HashMap<Integer, Epic> epicData = new HashMap<>();
    private final HashMap<Integer, SubTask> subTaskData = new HashMap<>();
    private final List<T> historyData = new ArrayList<>();
    Epic epic = new Epic("Обед", 0, "Котлетки с пюрешкой", Status.NEW);

    public void test() {
        addNewEpic(epic);
        SubTask subTask = new SubTask("Макарошки", 0, "Варим", Status.DONE, epic.getId());
        SubTask subTask1 = new SubTask("Пюрешка", 0, "Мнём", Status.DONE, epic.getId());
        addNewSubTask(subTask);
        addNewSubTask(subTask1);
        System.out.println(epicData);
        System.out.println(subTaskData);
        SubTask subTask2 = new SubTask("Пюрешка", 0, "Мнём", Status.NEW, epic.getId());
        addNewSubTask(subTask2);
        System.out.println(epicData);
        getEpicById(1);
        getSubTaskById(1);
        System.out.println(getHistory());
    }

    @Override
    public int addNewTask(Task task) { // добавляет задачу в мапу
        task.setId(taskId++);
        taskData.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public int addNewEpic(Epic epic) { // добавляет задачу в мапу
        epic.setId(epicId++);
        epicData.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public int addNewSubTask(SubTask subTask) { // добавляет задачу в мапу и её айди в лист эпика
        subTask.setId(subTaskId++);
        subTaskData.put(subTask.getId(), subTask);
        epicData.get(subTask.getEpicId()).addSubTaskIds(subTask.getId());
        findEpicStatus(subTask.getEpicId()); // пересчитал статус эпика
        return subTask.getId();
    }

    @Override
    public Task getTaskById(int id) { // получает и возвращает задачу по id
        if (taskData.get(id) != null) {
            addHistory((T) taskData.get(id));
            return taskData.get(id);
        }
        return null;
    }

    @Override
    public SubTask getSubTaskById(int id) { // получает и возвращает задачу по id
        if (subTaskData.get(id) != null) {
            addHistory((T) subTaskData.get(id));
            return subTaskData.get(id);
        }
        return null;
    }

    @Override
    public Epic getEpicById(int id) { // получает и возвращает задачу по id
        if (epicData.get(id) != null) {
            addHistory((T) epicData.get(id));
            return epicData.get(id);
        }
        return null;
    }

    @Override
    public void deleteAllTask() { // удаляет все задачи из мапы
        taskData.clear();
    }

    @Override
    public void deleteAllSubTask() { // удаляет все задачи из мапы
        subTaskData.clear();
        for (int id : epicData.keySet()) {
            epicData.get(id).deleteSubTaskIds(); // чистит списки айди в эпиках
        }
    }

    @Override
    public void deleteAllEpic() { // удаляет все эпики и сабтаски из мап
        epicData.clear();
        subTaskData.clear();
    }

    @Override
    public void deleteTaskById(int id) { // удаляет задачу из мапы по id
        taskData.remove(id);
    }

    @Override
    public void deleteSubTaskById(Integer id) { // удаляет сабтаск из мапы и из листа в эпике и пересчитывает статус
        Integer epicId = subTaskData.get(id).getEpicId(); // сохранил айди чтобы не было NullPointerException
        subTaskData.remove(id);
        epicData.get(epicId).deleteSubTaskFromList(id);
        findEpicStatus(epicId);
    }

    @Override
    public void deleteEpicById(Integer id) { // удаляет задачу из мапы по id
        for (Integer subId : getEpicById(id).getSubTaskIds()) { // и чистит мапу сабтасков по эпикАйди
                subTaskData.remove(subId);
        }
        epicData.remove(id);
    }

    @Override
    public void updateTask(Task task) { // перезаписывает task под тем же id
        if (taskData.get(task.getId()) != null)
            taskData.put(task.getId(), task);
    }

    @Override
    public void updateSubTask(SubTask subTask) { // перезаписывает subTask под тем же id
        if (subTaskData.get(subTask.getId()) != null && !subTaskData.get(subTask.getId())
                .getStatus().equals(subTask.getStatus())) { // если не null и статус изменился
            subTaskData.put(subTask.getId(), subTask); // заменили сабтаск
            findEpicStatus(subTaskData.get(subTask.getId()).getEpicId()); // и пересчитали статус эпика
            return;
        }
            subTaskData.put(subTask.getId(), subTask); // если статус не изменился, только заменили сабтаск
    }

    @Override
    public void updateEpic(Epic epic) { // перезаписывает epic под тем же id
        if (epicData.get(epic.getId()) != null)
            epicData.put(epic.getId(), epic);
    }

    @Override
    public ArrayList<Task> getTask() { // возвращает лист тасков
        return (ArrayList<Task>) taskData.values();
    }

    @Override
    public ArrayList<Epic> getEpic() { // возвращает лист эпиков
        return (ArrayList<Epic>) epicData.values();
    }

    @Override
    public ArrayList<SubTask> getSubTask() { // возвращает лист сабтасков
        return (ArrayList<SubTask>) subTaskData.values();
    }

    @Override
    public ArrayList<SubTask> getEpicSubTasks(int epicId) {
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
        for (Integer id : subTaskData.keySet()) {
            if (subTaskData.get(id).getEpicId().equals(epicId)) {
                epicSubTasks.add(subTaskData.get(id));
            }
        }
        return epicSubTasks;
    }

    @Override
    public void findEpicStatus(int epicId) { // вычисляет статус эпика
        ArrayList<Status> statusList = new ArrayList<>();
        for (Integer id : epicData.get(epicId).getSubTaskIds()) {
            statusList.add(subTaskData.get(id).getStatus());
        }
        int statusIndex = 0;
        for (int i = 0; i < statusList.size(); i++){
            if (statusList.get(i).equals(Status.DONE)) {
                statusIndex++;
            }
        }
        if (statusIndex == 0) {
            epicData.get(epicId).setStatus(Status.NEW);
        } else if (statusIndex == statusList.size()) {
            epicData.get(epicId).setStatus(Status.DONE);
        } else {
            epicData.get(epicId).setStatus(Status.IN_PROGRESS);
        }
    }
    @Override
    public List<T> getHistory() {
    return historyData;
    }

    public void addHistory(T task) {
        historyData.add(task);
        if (historyData.size() > 10) {
            historyData.remove(0);
        }
    }
}



