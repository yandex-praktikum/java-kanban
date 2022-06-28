import java.util.HashMap;

public class Manager {
    int counterId = 0;
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();

    public void updateTask(Task task, Status status) {
        task.setStatus(status);
        tasks.put(task.getId(), task);
    }

    public void updateSubtask(Subtask subtask, Status status) {
        subtask.setStatus(status);
        subtasks.put(subtask.getId(), subtask);
        checkStatusEpicAfterChangeOrDeleteSubtask(subtask.epicId);
    }


    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public Task addTask(String name, String description) {
        Task task = new Task(name, description);
        task.id = ++counterId;
        tasks.put(task.id, task);
        return task;
    }

    public Subtask addSubtask(String name, String description, int epicId) {
        Subtask subtask = new Subtask(name, description, epicId);
        subtask.id = ++counterId;
        subtasks.put(subtask.id, subtask);
        getEpicById(epicId).subtasksOfEpic.add(counterId);
        if ((subtask.getStatus() == Status.NEW && getEpicById(epicId).getStatus() == Status.DONE) ||
                (subtask.getStatus() == Status.IN_PROGRESS && getEpicById(epicId).getStatus() == Status.NEW) ||
                (subtask.getStatus() == Status.IN_PROGRESS && getEpicById(epicId).getStatus() == Status.DONE) ||
                (subtask.getStatus() == Status.DONE && getEpicById(epicId).getStatus() == Status.NEW)) {
            getEpicById(epicId).setStatus(Status.IN_PROGRESS);
        }
        return subtask;
    }

    public Epic addEpic(String name, String description) {
        Epic epic = new Epic(name, description);
        epic.id = ++counterId;
        epics.put(epic.id, epic);
        return epic;
    }

    public void printTasks() {
        for (Integer key : tasks.keySet()) {
            System.out.println(tasks.get(key).toString());
        }
    }

    public void printSubtasks() {
        for (Integer key : subtasks.keySet()) {
            System.out.println(subtasks.get(key).toString());
        }
    }

    public void printEpics() {
        for (Integer key : epics.keySet()) {
            System.out.println(epics.get(key).toString());
        }
    }

    public void removeAllTasks() {
            tasks.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
    }

    public Task getTaskById(Integer id) {
        return tasks.get(id);
    }

    public Subtask getSubtaskById(Integer id) {
        return subtasks.get(id);
    }

    public Epic getEpicById(Integer id) {
        return epics.get(id);
    }

    public void removeTaskById(Integer id) {
        tasks.remove(id);
    }

    public void removeSubtaskById(Integer id) {
        int epicId = subtasks.get(id).epicId;
        subtasks.remove(id);
        getEpicById(epicId).subtasksOfEpic.remove(id);
        if (!getEpicById(epicId).subtasksOfEpic.isEmpty()) {
            checkStatusEpicAfterChangeOrDeleteSubtask(epicId);
        }
    }

    public void removeEpicById(Integer id) {
        epics.remove(id);
    }

    public void printSubtasksOfEpic(Integer epicId) {
        System.out.println(getEpicById(epicId).subtasksOfEpic);
    }

    private void checkStatusEpicAfterChangeOrDeleteSubtask(int epicId) {
        int newCount = 0;
        int newDone = 0;
        for (int i = 0; i < getEpicById(epicId).subtasksOfEpic.size(); i++) {
            if (getSubtaskById(getEpicById(epicId).subtasksOfEpic.get(i)).getStatus() == Status.NEW) {
                newCount++;
            } else if (getSubtaskById(getEpicById(epicId).subtasksOfEpic.get(i)).getStatus() == Status.DONE) {
                newDone++;
            }
        }
        if (newCount == getEpicById(epicId).subtasksOfEpic.size()) {
            getEpicById(epicId).setStatus(Status.NEW);
        } else if (newDone == getEpicById(epicId).subtasksOfEpic.size()) {
            getEpicById(epicId).setStatus(Status.DONE);
        } else {
            getEpicById(epicId).setStatus(Status.IN_PROGRESS);
        }
    }

}
