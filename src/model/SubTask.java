package model;

import java.util.ArrayList;

public class SubTask extends Task {
    private final Integer masterId;
    ArrayList<Integer> epicAssign = new ArrayList<>();

    public SubTask(String taskName, String content, Status status, Integer masterId) {
        super(taskName, content, status);
        this.masterId = masterId;
    }

    public Integer getMasterId() {
        return masterId;
    }
}
