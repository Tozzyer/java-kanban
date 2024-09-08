package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable<Task> {

    private Integer id;//id задачи
    private String taskName; // имя задачи
    private Status status;  // статус задачи
    private String content; // содержимое задачи
    protected LocalDateTime startTime;
    protected Duration duration;


    //Конструктор

    public Task(String taskName, String content, Status status) {
        this.status = status;
        this.taskName = taskName;
        this.content = content;
    }

    public Task(String taskName, String content, Status status, Integer id, LocalDateTime startTime, Duration duration) {
        this.status = status;
        this.taskName = taskName;
        this.content = content;
        this.id = id;
        this.startTime = startTime;
        this.duration = duration;
    }


    public LocalDateTime getEndTime() {
        if (startTime != null && duration != null) {
            return startTime.plus(duration);
        } else {
            return LocalDateTime.MIN;
        }
    }

    public Duration getDuration() {
        if (duration != null) {
            return duration;
        } else {
            return Duration.ofMinutes(0);
        }
    }

    public LocalDateTime getStartTime() {
        if (startTime != null) {
            return startTime;
        } else {
            return LocalDateTime.MIN;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(taskName, task.taskName) && status == task.status && Objects.equals(content, task.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskName, status, content);
    }

    @Override
    public String toString() {
        return "model.Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", status=" + status +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public int compareTo(Task o) {
        if (this.startTime == null && o.startTime == null) {
            return 0;
        }
        if (this.startTime == null) {
            return -1;
        }
        if (o.startTime == null) {
            return 1;
        }
        return this.startTime.compareTo(o.startTime);
    }

    public boolean isTimeCrossed(Task task) {
        return task.getStartTime().isBefore(this.getEndTime()) && task.getEndTime().isAfter(this.getStartTime());
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

}
