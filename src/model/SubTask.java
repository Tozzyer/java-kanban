package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.Duration;

public class SubTask extends Task {
    private final Integer masterId;
    public ArrayList<Integer> epicAssign = new ArrayList<>();

    public SubTask(String taskName, String content, Status status, Integer masterId) {
        super(taskName, content, status);
        this.masterId = masterId;
    }

    public Integer getMasterId() {
        return masterId;
    }
}
