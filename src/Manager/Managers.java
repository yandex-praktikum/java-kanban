package Manager;


import static Manager.InMemoryHistoryManager.historyData;

public class Managers {


    public static TaskManager getDefault() {
            TaskManager manager = new InMemoryTaskManager();
            return manager;
        }
    public static HistoryManager getDefaultHistory() {

        HistoryManager historyManager = new InMemoryHistoryManager(historyData);
        return historyManager;
    }
    }

