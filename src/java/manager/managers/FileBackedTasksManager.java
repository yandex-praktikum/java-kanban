package manager.managers;

import manager.history.HistoryManager;
import task.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
/** Реализация менеджера через хранение данных в .csv файле. **/
public class FileBackedTasksManager extends InMemoryTaskManager {
    public static File file = new File("history.csv");


    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public FileBackedTasksManager() {
    }

    @Override
    public int addNewTask(Task task) { // добавляет задачу в мапу
        taskDateValidation(task);
        task.setId(taskId++);
        taskData.put(task.getId(), task);
        save();
        return task.getId();
    }

    @Override
    public int addNewEpic(Epic epic) { // добавляет задачу в мапу
        epic.setId(epicId++);
        epicData.put(epic.getId(), epic);
        save();
        return epic.getId();
    }

    @Override
    public int addNewSubTask(SubTask subTask) { // добавляет задачу в мапу и её айди в лист эпика
        taskDateValidation(subTask);
        subTask.setId(subTaskId++);
        subTaskData.put(subTask.getId(), subTask);
        epicData.get(subTask.getEpicId()).addSubTaskIds(subTask.getId());
        epicStatusCalculation(subTask.getEpicId()); // пересчитал статус эпика
        epicTimeCalculation(subTask.getEpicId());
        save();
        return subTask.getId();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = taskData.get(id);// получает и возвращает задачу по id
        if (task != null) {
            historyManager.add(task);
        }
        save();
        return task;
    }

    @Override
    public SubTask getSubTaskById(int id) { // получает и возвращает задачу по id
        SubTask subTask = subTaskData.get(id);
        if (subTask != null) {
            historyManager.add(subTask);
        }
        save();
        return subTask;
    }

    @Override
    public Epic getEpicById(int id) { // получает и возвращает задачу по id
        Epic epic = epicData.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        save();
        return epic;
    }

    public void save() { // метод записывает таски и историю в csv
        try (FileWriter fileWriter = new FileWriter(file.getName(), false)) {
            if (file.getName().isEmpty()) {
                throw new ManagerSaveException("Имя файла пустое.");
            }
            fileWriter.write(taskToString());

        } catch (IOException | ManagerSaveException e) { // не понял что от меня хотят в тз, поэтому так
            e.printStackTrace();
        }
    }

    public String taskToString() { // метод собирает все таски и историю, записывает в стрингбилдер и передает в save()
        String type;
        List<Task> allTasks = new ArrayList<>();
        allTasks.addAll(epicData.values());
        allTasks.addAll(taskData.values());
        allTasks.addAll(subTaskData.values());
        StringBuilder sb = new StringBuilder();
        sb.append("id,type,name,status,description,epic,duration,start,end" + "\n");
        for (Task task : allTasks) {
            if (task.getId() < 20) {
                type = Types.TASK.toString();
            } else if (task.getId() < 30) {
                type = Types.EPIC.toString();
            } else {
                type = Types.SUBTASK.toString();
            }
            if (Types.SUBTASK.toString().equals(type)) {
                SubTask sub = (SubTask) task;
                sb.append(sub.getId() + "," + type + "," + sub.getName()
                        + "," + sub.getStatus() + "," + sub.getDescription() + "," + sub.getEpicId()
                        + "," + sub.getDuration() + ", " + sub.getStartTime().toString() + "\n");
            } else if (Types.EPIC.toString().equals(type)) {
                Epic epic = (Epic) task;
                sb.append(epic.getId() + "," + type + "," + epic.getName()
                        + "," + epic.getStatus() + "," + epic.getDescription() + "," + epic.getSubTaskIds()
                        + "," + epic.getDuration() + "," + epic.getStartTime().toString()
                        + "," + epic.getEndTime().toString() + "\n");
            } else {
                sb.append(task.getId() + "," + type + "," + task.getName()
                        + "," + task.getStatus() + "," + task.getDescription() + "," + task.getDuration()
                        + "," + task.getStartTime().toString() + "\n");
            }
        }
        sb.append(historyToString(historyManager));
        return sb.toString();
    }

    public Task fromString(String value) { // метод собирает таски и историю из строк
        List<String> stringTask = new ArrayList<>(List.of(value.split(",")));

        int id = Integer.parseInt(stringTask.get(0));
        String name = stringTask.get(2);
        String description = stringTask.get(4);
        long duration;
        LocalDateTime startTime;
        LocalDateTime endTime = null;
        int epicId = 0;

        if (stringTask.size() == 7) {
            duration = Long.parseLong(stringTask.get(5));
            startTime = LocalDateTime.parse(stringTask.get(6));
        } else if (stringTask.size() == 8) {
            epicId = Integer.parseInt(stringTask.get(5));
            duration = Long.parseLong(stringTask.get(6));
            startTime = LocalDateTime.parse(stringTask.get(7).trim());
        } else {
            int size = stringTask.size();
            duration = Long.parseLong(stringTask.get(size - 3));
            startTime = LocalDateTime.parse(stringTask.get(size - 2));
            endTime = LocalDateTime.parse(stringTask.get(size - 1));
        }

        if (stringTask.get(1).equals("TASK")) {
            Task task = new Task(name, id, description, Status.NEW, duration, startTime);
            if (stringTask.get(3).equals("DONE")) {
                task.setStatus(Status.DONE);
            } else if (stringTask.get(3).equals("IN_PROGRESS")) {
                task.setStatus(Status.IN_PROGRESS);
            }
            return task;

        } else if (stringTask.get(1).equals("EPIC")) {
            Epic task = new Epic(name, id, description, duration, startTime, endTime);
            if (stringTask.get(3).equals("DONE")) {
                task.setStatus(Status.DONE);
            } else if (stringTask.get(3).equals("IN_PROGRESS")) {
                task.setStatus(Status.IN_PROGRESS);
            }
            return task;

        } else {
            SubTask task = new SubTask(name, id, description, Status.NEW, epicId, duration, startTime);
            if (stringTask.get(3).equals("DONE")) {
                task.setStatus(Status.DONE);
            } else if (stringTask.get(3).equals("IN_PROGRESS")) {
                task.setStatus(Status.IN_PROGRESS);
            }
            return task;
        }
    }

    static String historyToString(HistoryManager manager) { // метод переводит историю в троку
        StringBuilder sb = new StringBuilder();
        sb.append("                                                                                " + "\n");
        for (Task task : manager.getHistory()) {
            sb.append(task.getId() + ",");
        }
        return sb.toString();
    }

    static List<Integer> historyFromString(String value) { // метод разбивает строку с историей на инты

        List<String> values = List.of(value.split(","));
        List<Integer> history = new ArrayList<>();
        for (String val : values) {
            history.add(Integer.parseInt(val));
        }
        return history;
    }

    public static FileBackedTasksManager loadFromFile(File file) { // метод создаёт новый менеджер из файла
        InMemoryTaskManager memoManager = new InMemoryTaskManager();
        memoManager.allTasks.clear();
            if (!file.isFile())
                throw new IllegalArgumentException("Файл не найден");

            List<Task> tasksFromString = new ArrayList<>();
            List<String> allLines = new ArrayList<>();

            FileBackedTasksManager manager = Managers.getDefaultBacked();
            HistoryManager historyManager = Managers.getDefaultHistory();

            try (FileReader reader = new FileReader(file.getName())) {
                BufferedReader br = new BufferedReader(reader);
                while (br.ready()) {
                    String line = br.readLine();
                    allLines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (allLines.get(allLines.size() - 1).isBlank()) {
                for (int i = 1; i < allLines.size() - 1; i++) {
                    tasksFromString.add(manager.fromString(allLines.get(i)));
                }
            } else {
                for (int i = 1; i < allLines.size() - 2; i++) {
                    tasksFromString.add(manager.fromString(allLines.get(i)));
                }
            }

            for (Task task : tasksFromString) {
                if (task.getId() < 20) {
                    manager.addNewTask(task);
                } else if (task.getId() < 30) {
                    manager.addNewEpic((Epic) task);
                } else if (task.getId() > 29) {
                    manager.addNewSubTask((SubTask) task);
                }
            }

            String history = allLines.get(allLines.size() - 1);
            if (!history.isBlank()) {
                for (Integer id : historyFromString(history)) {
                    for (Task task : tasksFromString) {
                        if (task.getId().equals(id)) {
                            if (id > 29) {
                                historyManager.add(manager.getSubTaskById(id));
                            } else if (id < 20) {
                                historyManager.add(manager.getTaskById(id));
                            } else {
                                historyManager.add(manager.getEpicById(id));
                            }
                        }
                    }
                }
            }
            return manager;
    }
}