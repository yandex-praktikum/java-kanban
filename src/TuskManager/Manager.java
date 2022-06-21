package TuskManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Manager {
    private int tuskId = 1;
    private int EpicId = 1;
    private int subTuskId = 1;
    private final HashMap<Integer, Tusk> tuskData = new HashMap<>();
    private final HashMap<Integer, Epic> epicData = new HashMap<>();
    private final HashMap<Integer, SubTusk> subTuskData = new HashMap<>();

    private void add(HashMap tusk, Object tuskObject, int id) { // добавляет задачу в мапу
        tusk.put(id, tuskObject);
    }// корректно работает со всеми видами тасков

    private Object getById(Object tusk) { // получает и возвращает задачу по id
        Object receivedTusk = tusk; //в качестве аргумента передаётся таск из мапы по айди
        return receivedTusk;
    }

    private HashMap deleteAll(HashMap tusks) { // удаляет все задачи из мапы
        tusks.clear();
        return tusks;
    }
    private HashMap deleteById(HashMap tusk, Integer id) { // удаляет задачу из мапы по id
        tusk.remove(id);
        return tusk;
    }

    private void setExistingTusk(Integer id, Object tusk) { // перезаписывает tusk под тем же id
        tuskData.put(id, (Tusk) tusk);
        tuskData.get(id).id = id;
    }

    private void setExistingSubTusk(Integer id, Object tusk) { // перезаписывает subTusk под тем же id
        subTuskData.put(id, (SubTusk) tusk);
        subTuskData.get(id).id = id;
    }

    private void setExistingEpic(Integer id, Object tusk) { // перезаписывает epic под тем же id
        epicData.put(id, (Epic) tusk);
        epicData.get(id).id = id;
    }

    private List getEpic(HashMap<Integer, Epic> tusks) { // создаёт и возвращает лист эпиков
        List tuskList = new ArrayList();
        for (Integer id : tusks.keySet()) {
            tuskList.add(tusks.get(id));
        }
        return tuskList;
    }

    private List<String> getTusk(HashMap<Integer, Tusk> tusks) { // создаёт и возвращает лист тасков
        List tuskList = new ArrayList();
        for (Integer id : tusks.keySet()) {
            tuskList.add(tusks.get(id));
        }
        return tuskList;
    }

    private List<String> getEpicSubTusk(HashMap<Integer, SubTusk> tusks, Integer epicId) { // создаёт и возвращает
        List tuskList = new ArrayList();// лист сабтасков по определённому эпику
        for (Integer id : tusks.keySet()) {
            if (epicId.equals(tusks.get(id).epicId)) {
                tuskList.add(tusks.get(id));
            }
        }
        return tuskList;
    }

    private Tusk setNewTusk(String name, Integer id, String description, final String status) { // создаёт новый таск
        return new Tusk(name, id, description, status);
    }

    private Epic setNewEpic(String name, Integer id, String description, final String status) { // создаёт новый эпик
        return new Epic(name, id, description, status);
    }

    private SubTusk setNewSubTusk(String name, Integer id, String description, final String status, Integer epicId) {
        return new SubTusk(name, id, description, status, epicId);// создаёт новый сабтаск
    }

    private void setTuskStatus(HashMap tusk, Tusk tusks, final String newStatus) { // меняет статус таска
        Tusk newTusk = setNewTusk(tusks.name, tusks.id, tusks.description, newStatus);
        deleteById(tusk, tusks.id);
        add(tusk, newTusk, newTusk.id);
    }

    private void setSubTuskStatus(HashMap tusk, SubTusk tusks, final String newStatus) { //меняет статус сабтаска
        SubTusk newSubTusk = setNewSubTusk(tusks.name, tusks.id, tusks.description, newStatus, tusks.epicId);
        deleteById(tusk, tusks.id);
        add(tusk, newSubTusk, newSubTusk.id);
        final String newEpicStatus = findEpicStatus(epicData.get(tusks.epicId), tusk); // вычисляет статус эпика
        setEpicStatus(epicData, epicData.get(tusks.epicId), newEpicStatus); // меняет статус эпика
    }

    private void setEpicStatus(HashMap tusk, Epic epics, final String newStatus) { // меняет статус эпика
        Epic newEpic = setNewEpic(epics.name, epics.id, epics.description, newStatus);
        deleteById(tusk, epics.id);
        add(tusk, newEpic, newEpic.id);
    }

    private final String findEpicStatus(Epic epic, HashMap<Integer, SubTusk> subTusk) { // вычисляет статус эпика
        List<String> statusList = new ArrayList<>();
        int statusIndex;

        for (Integer subId : subTusk.keySet()) {
            if (subTusk.get(subId).epicId.equals(epic.id)) {
                statusList.add(subTusk.get(subId).status);
            }
        }
        statusIndex = statusList.size();

        for (int i = 0; i < statusIndex; i++) {
            String status = statusList.get(i);
            if (status.equals(epic.status)) {
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
