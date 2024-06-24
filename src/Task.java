import java.util.Objects;
//rev3
public class Task {

    private Integer id;//id задачи
    private String taskName; // имя задачи
    private Status status;  // статус задачи
    private String content; // содержимое задачи

    //Конструктор

    Task(String taskName, String content, Status status) {
        this.status = status;
        this.taskName = taskName;
        this.content = content;

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
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", status=" + status +
                ", content='" + content + '\'' +
                '}';
    }
}
