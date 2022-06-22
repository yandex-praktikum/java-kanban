package Manager;

import TuskManager.Epic;
import TuskManager.SubTusk;
import TuskManager.Tusk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Manager {
    private int tuskId = 1;
    private int epicId = 1;
    private int subTuskId = 1;
    private final HashMap<Integer, Tusk> tuskData = new HashMap<>();
    private final HashMap<Integer, Epic> epicData = new HashMap<>();
    private final HashMap<Integer, SubTusk> subTuskData = new HashMap<>();

    /*Не понял коментарий по методу findEpicStatus: Можно сразу присваивать статус эпику и не возвращать ничего.
     Параметр HashMap<Integer, SubTusk> subTusk - лишний, эпик ищем в имеющейся мапе.
     Как в этом методе присвоить эпику новый статус если он final и чтобы его присвоить, нужно удалять старый
     эпик и записывать новый с другим статусом через setEpicStatus и всё это происходит при изменении статуса сабтаска
     в методе setSubTaskStatus и там же этим методам передаются нужные параметры, поэтому setEpicStatus переместить
     в findEpicStatus тоже не получается, чтобы можно было передать newStatus не возвращая ничего.
     Мапу из параметров убрал, остальное пока оставил так, не понимаю как реализовать.
     */


    private int addNewTask(Tusk task) { // добавляет задачу в мапу
        task.setId(tuskId++);
        tuskData.put(task.getId(), task);
        return task.getId();
    }

    private int addNewEpic(Epic epic) { // добавляет задачу в мапу
        epic.setId(epicId++);
        epicData.put(epic.getId(), epic);
        return epic.getId();
    }

    private int addNewSubTask(SubTusk subTusk) { // добавляет задачу в мапу
        subTusk.setId(subTuskId++);
        subTuskData.put(subTusk.getId(), subTusk);
        return subTusk.getId();
    }

    private Tusk getTaskById(int id) { // получает и возвращает задачу по id
        if (tuskData.get(id) != null)
            return tuskData.get(id);
        return null;
    }

    private SubTusk getSubTaskById(int id) { // получает и возвращает задачу по id
        if (subTuskData.get(id) != null)
            return subTuskData.get(id);
        return null;
    }

    private Epic getEpicById(int id) { // получает и возвращает задачу по id
        if (epicData.get(id) != null)
            return epicData.get(id);
        return null;
    }

    private void deleteAllTask() { // удаляет все задачи из мапы
        tuskData.clear();
    }

    private void deleteAllSubTask() { // удаляет все задачи из мапы
        subTuskData.clear();
    }

    private void deleteAllEpic() { // удаляет все задачи из мапы
        epicData.clear();
    }

    private void deleteTaskById(int id) { // удаляет задачу из мапы по id
        tuskData.remove(id);
    }

    private void deleteSubTaskById(int id) { // удаляет задачу из мапы по id
        subTuskData.remove(id);
    }

    private void deleteEpicById(int id) { // удаляет задачу из мапы по id
        epicData.remove(id);
    }

    private void updateTask(Tusk tusk) { // перезаписывает tusk под тем же id
        if (tuskData.get(tusk.getId()) != null)
            tuskData.put(tusk.getId(), tusk);
    }

    private void updateSubTask(SubTusk subTusk) { // перезаписывает tusk под тем же id
        if (subTuskData.get(subTusk.getId()) != null)
            tuskData.put(subTusk.getId(), subTusk);
    }

    private void updateEpic(Epic epic) { // перезаписывает tusk под тем же id
        if (epicData.get(epic.getId()) != null)
            epicData.put(epic.getId(), epic);
    }

    private ArrayList getTask(HashMap<Integer, Tusk> task) { // создаёт и возвращает лист тасков
        return Collections.list(Collections.enumeration(task.values()));
    }

    private ArrayList getEpic(HashMap<Integer, Epic> epic) { // создаёт и возвращает лист эпиков
        return Collections.list(Collections.enumeration(epic.values()));
    }

    private ArrayList getSubTask(HashMap<Integer, SubTusk> subTask) { // создаёт и возвращает лист сабтасков
        return Collections.list(Collections.enumeration(subTask.values()));
    }

        private void setTuskStatus (HashMap taskData, Tusk tusk, final String newStatus){ // меняет статус таска
            Tusk newTusk = new Tusk(tusk.getName(), tusk.getId(), tusk.getDescription(), newStatus);
            deleteTaskById(tusk.getId());
            addNewTask(newTusk);
        }

        private void setSubTuskStatus (HashMap subTuskData, SubTusk subTusk, final String newStatus){ //меняет статус
            SubTusk newSubTusk = new SubTusk(subTusk.getName(), subTusk.getId(), subTusk.getDescription(),// сабтаска
                    newStatus, subTusk.getEpicId());
            deleteSubTaskById(subTusk.getId());
            addNewSubTask(newSubTusk);
            final String newEpicStatus = findEpicStatus(epicData.get(subTusk.getEpicId())); // вычисляет статус эпика
            setEpicStatus(epicData, epicData.get(subTusk.getEpicId()), newEpicStatus); // меняет статус эпика
        }

        private void setEpicStatus (HashMap epicData, Epic epic, final String newStatus){ // меняет статус эпика
            Epic newEpic = new Epic(epic.getName(), epic.getId(), epic.getDescription(), newStatus);
            deleteEpicById(epic.getId());
            addNewEpic(newEpic);
        }

        private final String findEpicStatus (Epic epic){ // вычисляет статус эпика
            List<String> statusList = new ArrayList<>();
            int statusIndex;

            for (Integer subId : subTuskData.keySet()) {
                if (subTuskData.get(subId).getEpicId().equals(epic.getId())) {
                    statusList.add(subTuskData.get(subId).getStatus());
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

