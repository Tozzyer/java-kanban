import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {

    static Integer taskID = 0;
    static HashMap<Integer, Task> dataBase = new HashMap<>();
    static HashMap<Integer, Integer> keyList = new HashMap<>();

    static void addTask(String content, TaskType type) {
        Task task = new Task(content, type); //запускаем конструктор таск
        boolean checkTask = false;
        boolean hasKey = dataBase.containsKey(task.hashCode());//проверяем есть ли такая задача
        if (hasKey) {
            checkTask = task.equals(dataBase.get(task.hashCode()));//тута сравниваем
        }
        if (checkTask) {
            System.out.println("Такая задача уже существует..");
            return;
        }
        taskID++;
        dataBase.put(task.hashCode(), task);
        keyList.put(taskID, task.hashCode());
    }

    static void printDebug() {
        for (Integer key : keyList.keySet()) {
            System.out.println("ID: " + key + ". Hash: " + keyList.get(key) + ". Obj: " + dataBase.get(keyList.get(key)));
        }
    }

    static void printAll() {
        for (Integer key : keyList.keySet()) {
            System.out.println("ID: " + key + ". Тип: " + dataBase.get(keyList.get(key)).getType() + ". Задача: " + dataBase.get(keyList.get(key)).getContent() + ". Статус задачи: " + dataBase.get(keyList.get(key)).getStatus());
        }
    }

}
