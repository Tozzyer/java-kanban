import java.util.HashMap;

public class Epic extends Task {
    HashMap<Integer, SubTask> epicSubs = new HashMap<>();

    Epic(String taskName, String content, Status status) {
        super(taskName, content, status);
    }

    void addSubTask(SubTask subTask) {
        epicSubs.put(subTask.getId(), subTask);
    }

    void printAllEpicSubTasks() {
        for (SubTask subTask : epicSubs.values()) {
            System.out.println("Obj: " + subTask);
        }
    }

    void statusUpdate() {
        if (epicSubs.isEmpty()) {
            setStatus(Status.NEW);
            return;
        } else {
            for (SubTask subTask : epicSubs.values()) {
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
        epicSubs.clear();
        setStatus(Status.NEW);
    }

    public HashMap<Integer, SubTask> getEpicSubs() {
        return epicSubs;
    }
}
