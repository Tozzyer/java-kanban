import java.util.Objects;

public class Task {

    private Status status;          // статус задачи
    private final TaskType type; // тип задачи
    private String content; // содержимое задачи

    //Конструктор

    Task(String content, TaskType type){
        status=Status.NEW;
        this.content = content;
        this.type=type;
        System.out.println("Создана задача:"+"\""+content+"\".");
    }

    //Геттеры-сеттеры

    public Status getStatus() {
        return status;
    }

    public TaskType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        int hash = 28;
        hash+=content.hashCode();
        hash*=3;
        hash+=(status.hashCode()*type.hashCode());
        return hash;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return status == task.status && type == task.type && Objects.equals(content, task.content);
    }

    @Override
    public String toString() {
        return "Task{" +
                "status=" + status +
                ", type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
