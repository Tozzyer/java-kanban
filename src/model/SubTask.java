package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SubTask extends Task {
    private final Integer masterId;
    public ArrayList<Integer> epicAssign = new ArrayList<>();

    public SubTask(String taskName, String content, Status status, Integer masterId) {
        super(taskName, content, status);
        this.masterId = masterId;
    }

    public SubTask(String taskName, String content, Status status, Integer masterId, Integer id, LocalDateTime startTime, Duration duration) {
        super(taskName, content, status);
        this.masterId = masterId;
        setId(id);
        setStartTime(startTime);
        setDuration(duration);
    }

    public Integer getMasterId() {
        return masterId;
    }
}
