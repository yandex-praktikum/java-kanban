package Manager;

import Task.Epic;
import Task.SubTask;
import Task.Task;
import Task.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryTaskManager implements TaskManager {
    private int taskId = 10;
    private int epicId = 20;
    private int subTaskId = 30;
    private final Map<Integer, Task> taskData = new HashMap<>();
    private final Map<Integer, Epic> epicData = new HashMap<>();
    private final Map<Integer, SubTask> subTaskData = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

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
    public Task getTaskById(int id) {
        Task task = taskData.get(id);// получает и возвращает задачу по id
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public SubTask getSubTaskById(int id) { // получает и возвращает задачу по id
        SubTask subTask = subTaskData.get(id);
        if (subTask!= null) {
            historyManager.add(subTask);
        }
        return subTask;
    }

    @Override
    public Epic getEpicById(int id) { // получает и возвращает задачу по id
        Epic epic = epicData.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public void deleteAllTasks() { // удаляет все задачи из мапы
        for (int ids : taskData.keySet()) {
            historyManager.remove(ids);
        }
        taskData.clear();
    }

    @Override
    public void deleteAllSubTasks() { // удаляет все задачи из мапы
        for (int ids : subTaskData.keySet()) {
            historyManager.remove(ids);
        }
        subTaskData.clear();
        for (int id : epicData.keySet()) {
            epicData.get(id).deleteSubTaskIds();// чистит списки айди в эпиках
            epicData.get(id).setStatus(Status.NEW);// обновляет статус эпика
        }

    }

    @Override
    public void deleteAllEpics() { // удаляет все эпики и сабтаски из мап
        for (int ids : epicData.keySet()) {
            historyManager.remove(ids);
        }
        epicData.clear();
        deleteAllSubTasks();
    }

    @Override
    public void deleteTaskById(int id) { // удаляет задачу из мапы по id
        historyManager.remove(id);
        taskData.remove(id);
    }

    @Override
    public void deleteSubTaskById(Integer id) { // удаляет сабтаск из мапы и из листа в эпике и пересчитывает статус
        Integer epicId = subTaskData.get(id).getEpicId(); // сохранил айди чтобы не было NullPointerException
        historyManager.remove(id);
        subTaskData.remove(id);
        epicData.get(epicId).deleteSubTaskFromList(id);
        findEpicStatus(epicId);
    }

    @Override
    public void deleteEpicById(Integer id) { // удаляет задачу из мапы по id
        for (Integer subId : getEpicById(id).getSubTaskIds()) { // и чистит мапу сабтасков по эпикАйди
            subTaskData.remove(subId);
            historyManager.remove(subId);
        }
        historyManager.remove(id);
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
    public List<Task> getTask() { // возвращает лист тасков
        return  List.copyOf(taskData.values());
    }

    @Override
    public List<Epic> getEpic() { // возвращает лист эпиков
        return List.copyOf(epicData.values());
    }

    @Override
    public List<SubTask> getSubTask() { // возвращает лист сабтасков
        return List.copyOf(subTaskData.values());
    }

    @Override
    public List<SubTask> getEpicSubTasks(int epicId) {
        List<SubTask> epicSubTasks = new ArrayList<>();
        for (Integer id : subTaskData.keySet()) {
            if (subTaskData.get(id).getEpicId().equals(epicId)) {
                epicSubTasks.add(subTaskData.get(id));
            }
        }
        return epicSubTasks;
    }

    @Override
    public void findEpicStatus(int epicId) { // вычисляет статус эпика
        List<Status> statusList = new ArrayList<>();
        for (Integer id : epicData.get(epicId).getSubTaskIds()) {
            statusList.add(subTaskData.get(id).getStatus());
        }
        int statusIndex = 0;
        for (int i = 0; i < statusList.size(); i++){
            if (statusList.get(i).equals(Status.DONE)) {
                statusIndex += 2;
            } else if (statusList.get(i).equals(Status.IN_PROGRESS)) {
                statusIndex++;
            }
        }
        if (statusIndex == 0) {
            epicData.get(epicId).setStatus(Status.NEW);
        } else if (statusIndex == (statusList.size() * 2)) {
            epicData.get(epicId).setStatus(Status.DONE);
        } else {
            epicData.get(epicId).setStatus(Status.IN_PROGRESS);
        }
    }
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}