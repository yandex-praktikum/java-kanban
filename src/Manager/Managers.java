package Manager;

public class Managers {

        public static TaskManager getDefault() {
            TaskManager manager = new InMemoryTaskManager();
            return manager;
        }
    }

