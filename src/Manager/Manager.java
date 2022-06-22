package Manager;

import TaskManager.Epic;
import TaskManager.SubTask;
import TaskManager.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Manager {
    private int taskId = 1;
    private int epicId = 1;
    private int subTaskId = 1;
    private final HashMap<Integer, Task> taskData = new HashMap<>();
    private final HashMap<Integer, Epic> epicData = new HashMap<>();
    private final HashMap<Integer, SubTask> subTaskData = new HashMap<>();

    /*Не понял коментарий по методу findEpicStatus: Можно сразу присваивать статус эпику и не возвращать ничего.
     Параметр HashMap<Integer, SubTusk> subTusk - лишний, эпик ищем в имеющейся мапе.
     Как в этом методе присвоить эпику новый статус если он final и чтобы его присвоить, нужно удалять старый
     эпик и записывать новый с другим статусом через setEpicStatus и всё это происходит при изменении статуса сабтаска
     в методе setSubTaskStatus и там же этим методам передаются нужные параметры, поэтому setEpicStatus переместить
     в findEpicStatus тоже не получается, чтобы можно было передать newStatus не возвращая ничего.
     Мапу из параметров убрал, остальное пока оставил так, не понимаю как реализовать.
     */


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

    private int addNewSubTask(SubTask subTask) { // добавляет задачу в мапу
        subTask.setId(subTaskId++);
        subTaskData.put(subTask.getId(), subTask);
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
    }

    private void deleteAllEpic() { // удаляет все задачи из мапы
        epicData.clear();
    }

    private void deleteTaskById(int id) { // удаляет задачу из мапы по id
        taskData.remove(id);
    }

    private void deleteSubTaskById(int id) { // удаляет задачу из мапы по id
        subTaskData.remove(id);
    }

    private void deleteEpicById(int id) { // удаляет задачу из мапы по id
        epicData.remove(id);
    }

    private void updateTask(Task task) { // перезаписывает tusk под тем же id
        if (taskData.get(task.getId()) != null)
            taskData.put(task.getId(), task);
    }

    private void updateSubTask(SubTask subTask) { // перезаписывает tusk под тем же id
        if (subTaskData.get(subTask.getId()) != null)
            subTaskData.put(subTask.getId(), subTask);
    }

    private void updateEpic(Epic epic) { // перезаписывает tusk под тем же id
        if (epicData.get(epic.getId()) != null)
            epicData.put(epic.getId(), epic);
    }

    private ArrayList getTask(HashMap<Integer, Task> task) { // создаёт и возвращает лист тасков
        return Collections.list(Collections.enumeration(task.values()));
    }

    private ArrayList getEpic(HashMap<Integer, Epic> epic) { // создаёт и возвращает лист эпиков
        return Collections.list(Collections.enumeration(epic.values()));
    }

    private ArrayList getSubTask(HashMap<Integer, SubTask> subTask) { // создаёт и возвращает лист сабтасков
        return Collections.list(Collections.enumeration(subTask.values()));
    }

        private void setTaskStatus (HashMap taskData, Task task, final String newStatus){ // меняет статус таска
            Task newTask = new Task(task.getName(), task.getId(), task.getDescription(), newStatus);
            deleteTaskById(task.getId());
            addNewTask(newTask);
        }

        private void setSubTaskStatus (HashMap subTaskData, SubTask subTask, final String newStatus){ //меняет статус
            SubTask newSubTask = new SubTask(subTask.getName(), subTask.getId(), subTask.getDescription(),// сабтаска
                    newStatus, subTask.getEpicId());
            deleteSubTaskById(subTask.getId());
            addNewSubTask(newSubTask);
            final String newEpicStatus = findEpicStatus(epicData.get(subTask.getEpicId())); // вычисляет статус эпика
            setEpicStatus(epicData, epicData.get(subTask.getEpicId()), newEpicStatus); // меняет статус эпика
        }

        private void setEpicStatus (HashMap epicData, Epic epic, final String newStatus){ // меняет статус эпика
            Epic newEpic = new Epic(epic.getName(), epic.getId(), epic.getDescription(), newStatus);
            deleteEpicById(epic.getId());
            addNewEpic(newEpic);
        }

        private final String findEpicStatus (Epic epic){ // вычисляет статус эпика
            List<String> statusList = new ArrayList<>();
            int statusIndex;

            for (Integer subId : subTaskData.keySet()) {
                if (subTaskData.get(subId).getEpicId().equals(epic.getId())) {
                    statusList.add(subTaskData.get(subId).getStatus());
                }
            }
            statusIndex = statusList.size();

            for (int i = 0; i < statusList.size(); i++) {
                String status = statusList.get(i);
                if (status.equals(epic.getStatus())) {
                    statusList.remove(status);
                }
            }
            if (!statusList.isEmpty() && statusIndex == statusList.size()) {
                return "DONE";
            } else if (!statusList.isEmpty()) {
                return "IN_PROGRESS";
            }
            return "NEW";
        }
    }

