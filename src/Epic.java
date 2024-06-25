import java.util.HashMap;

public class Epic extends Task {
    HashMap<Integer, SubTask> epicSubHashMap = new HashMap<>();

    Epic(String taskName, String content, Status status) {
        super(taskName, content, status);
    }

    void addSubTask(SubTask subTask) {
        epicSubHashMap.put(subTask.getId(), subTask);
    }

    void printAllEpicSubTasks() {
        for (SubTask subTask : epicSubHashMap.values()) {
            System.out.println("Obj: " + subTask);
        }
    }

    void statusUpdate() {
        if (epicSubHashMap.isEmpty()) {
            setStatus(Status.NEW);
            return;
        } else {
            for (SubTask subTask : epicSubHashMap.values()) {
                if (subTask.getStatus() == Status.IN_PROGRESS) {//Хотя бы один In Progress
                    setStatus(Status.IN_PROGRESS);
                    return;
                } else if (subTask.getStatus() == Status.NEW) {
                    return;
                } else {
                    setStatus(Status.DONE);
                }
            }
        }

    }

    void clearSub() {
        epicSubHashMap.clear();
        setStatus(Status.NEW);
    }

    public HashMap<Integer, SubTask> getEpicSubHashMap() {
        return epicSubHashMap;
    }
}
