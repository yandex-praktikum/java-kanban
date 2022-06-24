package Manager;

import TaskManager.Epic;
import TaskManager.SubTask;
import TaskManager.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Manager {
    private int taskId = 1;
    private int epicId = 1;
    private int subTaskId = 1;
    private final HashMap<Integer, Task> taskData = new HashMap<>();
    private final HashMap<Integer, Epic> epicData = new HashMap<>();
    private final HashMap<Integer, SubTask> subTaskData = new HashMap<>();

    private int addNewTask(Task task) { // добавляет задачу в мапу
        task.setId(taskId++);
        taskData.put(task.getId(), task);
        return task.getId();
    }

    private int addNewEpic(Epic epic) { // добавляет задачу в мапу
        epic.setId(epicId++);
        epicData.put(epic.getId(), epic);
        return epic.getId();
    }

    private int addNewSubTask(SubTask subTask) { // добавляет задачу в мапу и её айди в лист эпика
        subTask.setId(subTaskId++);
        subTaskData.put(subTask.getId(), subTask);
        epicData.get(subTask.getEpicId()).addSubTaskIds(subTask.getId());
        return subTask.getId();
    }

    private Task getTaskById(int id) { // получает и возвращает задачу по id
        if (taskData.get(id) != null)
            return taskData.get(id);
        return null;
    }

    private SubTask getSubTaskById(int id) { // получает и возвращает задачу по id
        if (subTaskData.get(id) != null)
            return subTaskData.get(id);
        return null;
    }

    private Epic getEpicById(int id) { // получает и возвращает задачу по id
        if (epicData.get(id) != null)
            return epicData.get(id);
        return null;
    }

    private void deleteAllTask() { // удаляет все задачи из мапы
        taskData.clear();
    }

    private void deleteAllSubTask() { // удаляет все задачи из мапы
        subTaskData.clear();
        for(int id : epicData.keySet()){
            epicData.get(id).deleteSubTaskIds(); // чистит списки айди в эпиках
        }
    }

    private void deleteAllEpic() { // удаляет все задачи из мапы
        epicData.clear();
    }

    private void deleteTaskById(int id) { // удаляет задачу из мапы по id
        taskData.remove(id);
    }

    private void deleteSubTaskById(int id) { // удаляет сабтаск из мапы по id
        subTaskData.remove(id);
        for(int ids : epicData.keySet()){
            if(epicData.get(ids).getSubTaskId(id) == id) { // если нашел такой id в списке subTaskIds
                epicData.get(ids).deleteSubTaskFromList(id); // удалил
                findEpicStatus(ids); // пересчитал статус эпика
            }
        }
    }

    private void deleteEpicById(int id) { // удаляет задачу из мапы по id
        epicData.remove(id);
    }

    private void updateTask(Task task) { // перезаписывает task под тем же id
        if (taskData.get(task.getId()) != null)
            taskData.put(task.getId(), task);
    }

    private void updateSubTask(SubTask subTask) { // перезаписывает subTask под тем же id
        if (subTaskData.get(subTask.getId()) != null && subTaskData.get(subTask.getId())
                .getStatus().equals(subTask.getStatus())) { // если не null и статус изменился
            subTaskData.put(subTask.getId(), subTask); // заменили сабтаск
            findEpicStatus(subTaskData.get(subTask.getId()).getEpicId());// и пересчитали статус эпика
            } else {
            subTaskData.put(subTask.getId(), subTask); // если статус не изменился, только заменили сабтаск
        }
    }

    private void updateEpic(Epic epic) { // перезаписывает epic под тем же id
        if (epicData.get(epic.getId()) != null)
            epicData.put(epic.getId(), epic);
    }

    private ArrayList<Task> getTask() { // возвращает лист тасков
        return (ArrayList<Task>) taskData.values();
    }

    private ArrayList<Epic> getEpic() { // возвращает лист эпиков
        return (ArrayList<Epic>) epicData.values();
    }

    private ArrayList<SubTask> getSubTask() { // возвращает лист сабтасков
        return (ArrayList<SubTask>) subTaskData.values();
    }
    private ArrayList<SubTask> getEpicSubtasks(int epicId){
    for(Integer id : subTaskData.keySet()){
        if(subTaskData.get(id).getEpicId().equals(epicId))
            return (ArrayList<SubTask>) subTaskData.values();
        }
    return null;
    }

    private void findEpicStatus(int epicId) { // вычисляет статус эпика

        if (epicData.get(epicId).getSubTaskIds().isEmpty()) {
            epicData.get(epicId).setStatus("NEW");
        } else  {
            for (Integer id : subTaskData.keySet()) {
                if (subTaskData.get(id).getStatus().equals("DONE") && !subTaskData.get(id).getStatus().equals("NEW")) {
                    epicData.get(epicId).setStatus("DONE");
                } else {
                    epicData.get(epicId).setStatus("IN_PROGRESS");
                }
            }
        }
    }
}



