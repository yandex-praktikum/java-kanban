package manager;

import task.*;
import java.io.*;
import java.util.*;


public class FileBackedTasksManager extends InMemoryTaskManager {
    static File file = new File("history.csv");
    final String filename = "history.csv";
    /* Привет, ревьюер! Вернулся из отпуска и пытаюсь наверстать упущенное, поторопился и добавил исключение в try
     без условий :) Исправил, но не уверен чего от меня хотели в тз, буду рад коментариям.
     В остальном всё восстановилось, всё вызывается, csv перезаписался, единственный момент, что история в csv
     перезаписалась зеркально, но порядок вызова сохранился. Надеюсь это не проблема :)

     Еще один момент, если есть замечания, то лучше писать их как замечания в коде, а не в основной коментарий,
     в прошлый раз я его не развернул и увидел только после того, как отправил работу на вторую проверку.

    * По двум замечаниям есть вопросы:
    *
    * 1. Почему file static - потому, что если его сделать не статическим, то возникает такая проблема:
    *  "Non-static field 'file' cannot be referenced from a static context"
    *  Метод loadFromFile() должен быть статическим, мейн соответственно тоже, вопрос: как тут сделать поле file
    *  не статическим и им польоваться в статик методах? Я не придумал.
    *
    * 2. Почему типы задач у меня String, а не enum - потому, что мне удобней было присваивать тип сразу в String
    *  и записывать это в csv. Всё равно же придется делать enum.toString() чтобы записать в csv. Ок, я добавлю enum,
    *  но можно пояснение, какие преимущества это даёт по сравнению с моим решением?
    *  */

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        loadFromFile(file);
        /*FileBackedTasksManager manager = Managers.getDefaultBacked();
        Task task = new Task("Погулять", 0, "Оделся и пошёл", Status.NEW);
        Task task1 = new Task("Вернуться", 0, "Зашёл и разделся", Status.NEW);
        Epic epic = new Epic("Обед", 0, "Котлетки с пюрешкой и салатиком");
        manager.addNewTask(task);
        manager.addNewTask(task1);
        manager.addNewEpic(epic);
        SubTask subTask = new SubTask("Котлетки", 0, "Жарим", Status.NEW, epic.getId());
        SubTask subTask1 = new SubTask("Пюрешка", 0, "Мнём", Status.NEW, epic.getId());
        SubTask subTask2 = new SubTask("Салатик", 0, "Режем и заправляем", Status.NEW, epic.getId());
        manager.addNewSubTask(subTask);
        manager.addNewSubTask(subTask1);
        manager.addNewSubTask(subTask2);
        manager.getTaskById(10);
        manager.getTaskById(11);
        manager.getEpicById(20);
        manager.getSubTaskById(30);
        manager.getSubTaskById(31);
        manager.getSubTaskById(32);
        //System.out.println(manager.getEpicById(20));*/
    }

    @Override
    public int addNewTask(Task task) { // добавляет задачу в мапу
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
        subTask.setId(subTaskId++);
        subTaskData.put(subTask.getId(), subTask);
        epicData.get(subTask.getEpicId()).addSubTaskIds(subTask.getId());
        findEpicStatus(subTask.getEpicId()); // пересчитал статус эпика
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
        try (FileWriter fileWriter = new FileWriter(filename, false)) {
            if (filename.isEmpty()) {
                throw new ManagerSaveException("Имя файла пустое.");
            }
            fileWriter.write(taskToString());

        } catch (IOException | ManagerSaveException e) { // не понял что от меня хотят в тз, поэтому так
            e.printStackTrace();
        }
    }

    public String taskToString() { // метод собирает все таски и историю, записывает в стрингбилдер и передает в save()
        String type = Types.TASK.toString();
        List<Task> allTasks = new ArrayList<>();
        allTasks.addAll(epicData.values());
        allTasks.addAll(taskData.values());
        allTasks.addAll(subTaskData.values());
        StringBuilder sb = new StringBuilder();
        sb.append("id,type,name,status,description,epic" + "\n");
        for (Task task : allTasks) {
            if (task.getId() < 20) {
                type = Types.TASK.toString();
            } else if (task.getId() < 30) {
                type = Types.EPIC.toString();
            } else {
                type = Types.SUBTASK.toString();
            }
            if (type.equals("SUBTASK")) {
                SubTask sub = (SubTask) task;
                sb.append(sub.getId() + "," + type + "," + sub.getName()
                        + "," + sub.getStatus() + "," + sub.getDescription() + "," + sub.getEpicId() + "\n");
            } else if (type.equals("EPIC")) {
                Epic epic = (Epic) task;
                sb.append(epic.getId() + "," + type + "," + epic.getName()
                        + "," + epic.getStatus() + "," + epic.getDescription() + "," + epic.getSubTaskIds() + "\n");
            } else {
                sb.append(task.getId() + "," + type + "," + task.getName()
                        + "," + task.getStatus() + "," + task.getDescription() + "," + "\n");
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
        int epicId = 0;

        if (stringTask.size() == 6) {
            epicId = Integer.parseInt(stringTask.get(5));
        }

        if (stringTask.get(1).equals("TASK")) {
            Task task = new Task(name, id, description, Status.NEW);
            if (stringTask.get(3).equals("DONE")) {
                task.setStatus(Status.DONE);
            } else if (stringTask.get(3).equals("IN_PROGRESS")) {
                task.setStatus(Status.IN_PROGRESS);
            }
            return task;

        } else if (stringTask.get(1).equals("EPIC")) {
            Epic task = new Epic(name, id, description);
            if (stringTask.get(3).equals("DONE")) {
                task.setStatus(Status.DONE);
            } else if (stringTask.get(3).equals("IN_PROGRESS")) {
                task.setStatus(Status.IN_PROGRESS);
            }
            return task;

        } else {
            SubTask task = new SubTask(name, id, description, Status.NEW, epicId);
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

    static void loadFromFile(File file) { // метод создаёт новый менеджер из файла
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
        } catch(IOException e){
            e.printStackTrace();
        }

            for (int i = 1; i < allLines.size() - 2; i++) {
                tasksFromString.add(manager.fromString(allLines.get(i)));
            }

            for (Task task : tasksFromString) {
                if (task.getId() < 20) {
                    manager.addNewTask(task);
                } else if (task.getId() < 30) {
                    manager.addNewEpic((Epic) task);
                } else if (task.getId() > 29){
                    manager.addNewSubTask((SubTask) task);
                }
            }

        String history = allLines.get(allLines.size() - 1);

        for (Integer id : historyFromString(history)) {
            for (Task task : tasksFromString) {
                    if (task.getId() == id) {
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
        System.out.println(manager.getHistory());
        System.out.println(manager.getTaskById(10));
    }
}