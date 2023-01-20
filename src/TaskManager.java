import models.Task;
import models.Epic;
import models.Subtask;
import models.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int idGenerator = 0;

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteTasks() {
        tasks.clear();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public void createTask(Task task) {
        task.setId(idGenerator++);
        tasks.put(task.getId(), task);
    }

    public void refreshTask(Task task) {
        if(tasks.containsKey(task.getId())) {
            return;
        }
        tasks.put(task.getId(),task);
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteEpic(int id) {
        epics.remove(id);
        for(Map.Entry<Integer, Subtask> subtask : subtasks.entrySet()) {
            if (subtask.getValue().getEpicId() == id) {
                subtasks.remove(subtask);
            }
        }
    }

    public Epic getEpics(int id) {
        return epics.get(id);
    }

    public void createEpic(Epic epic) {
        epic.setId(idGenerator++);
        epic.setStatus(Status.fresh);
        epics.put(epic.getId(), epic);
    }

    public void createSubtask(Epic epic, Subtask subtask) {
        subtask.setId(idGenerator++);
        subtask.setEpicId(epic.getId());
        subtasks.put(subtask.getId(), subtask);
        epic.append(subtask);
        refreshStatusEpic(subtask.getEpicId());
    }

    public void refreshSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            refreshStatusEpic(subtask.getEpicId());
        }
    }

    public void deleteSubtask(int id) {
        final Subtask subtask = subtasks.remove(id);
        if (subtask == null) {
            return;
        }
        for(Map.Entry<Integer, Epic> epic : epics.entrySet()) {
            epic.getValue().getSubtasks().remove(subtask);
        }
        refreshStatusEpic(subtask.getEpicId());
    }

    public Subtask getSubtask(int id) {
        final Subtask subtask = subtasks.get(id);
        return subtask;
    }

    public void deleteSubtasks() {
        for(Map.Entry<Integer, Epic> epic : epics.entrySet()) {
            epic.getValue().getSubtasks().clear();
        }
        subtasks.clear();
    }

    public List<Subtask> getSubtasksEpic(int id) {
        return epics.get(id).getSubtasks();
    }

    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void refreshEpic(Epic epic) {
        final Epic epicSave = epics.get(epic.getId());
        if(epicSave == null) {
            return;
        }
        epicSave.setName(epic.getName());
        epicSave.setDescription(epic.getDescription());
    }

    public void finishTask(int id) {
        for(Map.Entry<Integer, Subtask> subtask : subtasks.entrySet()) {
            if (subtask.getValue().getId() == id) {
                subtask.getValue().setStatus(Status.done);
                refreshStatusEpic(subtask.getValue().getEpicId());
            }
        }
        for(Map.Entry<Integer, Task> task : tasks.entrySet()) {
            if (task.getValue().getId() == id) {
                task.getValue().setStatus(Status.done);
            }
        }
    }

    private void refreshStatusEpic(int id) {
        for(Map.Entry<Integer, Epic> epic : epics.entrySet()) {
            if (epic.getValue().getId() == id) {
                epic.getValue().setStatus(getStatus(epic.getValue().getSubtasks()));
            }
        }
    }

    public String getStatus(ArrayList<Subtask> subtasks) {
        int newStatus = 0, doneStatus = 0, all = 0;
        for(Subtask subtask : subtasks) {
            all++;
            if ("NEW".equals(subtask.getStatus())) {
                newStatus++;
                continue;
            }
            if ("DONE".equals(subtask.getStatus())) {
                doneStatus++;
            }
        }
        if(all == newStatus) {
            return Status.fresh;
        } else if (all == doneStatus) {
            return Status.done;
        } else {
            return Status.inProgress;
        }
    }
}
