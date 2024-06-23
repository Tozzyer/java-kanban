import java.util.ArrayList;
import java.util.Objects;

public class Task {

    private Status status;          // статус задачи
    private final TaskType type; // тип задачи
    private String content; // содержимое задачи
    private ArrayList<Integer> subList;

    //Конструктор

    Task(String content, TaskType type) {
        status = Status.NEW;
        this.content = content;
        this.type = type;

        if (type == TaskType.EPIC) {
            this.subList = new ArrayList<>();
            System.out.println("Создана ЭПИЧЕСКАЯ задача:" + "\"" + content + "\".");
        } else if (type == TaskType.SUBTASK) {
            System.out.println("Создана подзадача:" + "\"" + content + "\".");
        } else {
            System.out.println("Создана задача:" + "\"" + content + "\".");
        }
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


    @Override
    public int hashCode() {
        int hash = 11;
        hash += content.hashCode();
        hash *= 3;
        hash += (type.hashCode());
        if (!(subList == null) && subList.isEmpty()) {//Проверка эквивалентности для одинаковых саб-задач в разных эпиках
            hash += subList.hashCode();
        }
        return hash;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;

        return type == task.type && Objects.equals(content, task.content) && Objects.equals(subList, task.subList);

    }


    void addSubTask(String content, TaskType type, TaskManager obj) {
        TaskManager master = new TaskManager();
        master = obj;
        Task task = new Task(content, type);
        boolean checkTask = false;
        boolean hasKey = master.dataBase.containsKey(task.hashCode());//проверяем есть ли такая задача
        if (hasKey) {
            checkTask = task.equals(master.dataBase.get(task.hashCode()));//тута сравниваем
        }
        if (checkTask) {
            System.out.println("Такая подзадача уже существует..");
            return;
        }
        if (this.status == Status.DONE) { //откат эпика если добавлена новая задача
            this.status = Status.IN_PROGRESS;
        }
        master.taskID++;
        master.dataBase.put(task.hashCode(), task);
        master.keyList.put(master.taskID, task.hashCode());
        subList.add(master.taskID);
    }

    public ArrayList<Integer> getSubList() {
        return subList;
    }


    @Override
    public String toString() {
        return "Task{" +
                "status=" + status +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", subList=" + subList +
                '}';
    }
}
