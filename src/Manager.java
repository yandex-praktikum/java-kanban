import java.util.HashMap;

public class Manager {
    public int counterId = 0;
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void addTask(String name, String description, Status status) {
        counterId++;
        tasks.put(counterId, new Task(name, description, status));

    }

    public void addSubtask(String name, String description, Status status) {
        counterId++;
        subtasks.put(counterId, new Subtask(name, description, status));

    }

    public void addEpic(String name, String description, Status status) {
        counterId++;
        epics.put(counterId, new Epic(name, description, status));

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

    public Task getTaskByID(Integer id) {
        return tasks.get(id);
    }

    public Subtask getSubtaskByID(Integer id) {
        return subtasks.get(id);
    }

    public Epic getEpicByID(Integer id) {
        return epics.get(id);
    }

    public void removeTaskByID(Integer id) {
        tasks.remove(id);
    }

    public void removeSubtaskByID(Integer id) {
        subtasks.remove(id);
    }

    public void removeEpicByID(Integer id) {
        epics.remove(id);
    }
}
