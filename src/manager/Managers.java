package manager;

public class Managers {
    //SP7 - класс изменён c InMemoryTaskManager на FileBackedTaskManager
    public static TaskManager getDefault() {
        return new FileBackedTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
