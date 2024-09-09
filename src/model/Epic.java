package model;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;

public class Epic extends Task {
    HashMap<Integer, SubTask> epicSubs = new HashMap<>();
    private LocalDateTime endTime;

    public Epic(String taskName, String content, Status status) {
        super(taskName, content, status);
    }

    public void addSubTask(SubTask subTask) {
        epicSubs.put(subTask.getId(), subTask);
        refreshTimeLimits();
    }

    private void refreshTimeLimits() {
        Task firstTask = epicSubs.values()
                .stream()
                .min(Comparator.comparing(Task::getStartTime))
                .orElse(null);
        Task lastTask = epicSubs.values()
                .stream()
                .max(Comparator.comparing(Task::getStartTime))
                .orElse(null);
        startTime = firstTask.getStartTime();
        endTime = lastTask.getEndTime();

    }

    void printAllEpicSubTasks() {
        for (SubTask subTask : epicSubs.values()) {
            System.out.println("Obj: " + subTask);
        }
    }

    public void statusUpdate() {
        if (epicSubs.isEmpty()) {
            setStatus(Status.NEW);
            return;
        } else {
            for (SubTask subTask : epicSubs.values()) {
                if (subTask.getStatus() == Status.IN_PROGRESS) {
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

    public void clearSub() {
        epicSubs.clear();
        setStatus(Status.NEW);
    }

    public HashMap<Integer, SubTask> getEpicSubs() {
        return epicSubs;
    }
}
