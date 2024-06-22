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

    static void printRegular() {
        System.out.println("Список обычных задач:");
        for (Integer key : keyList.keySet()) {
            if (dataBase.get(keyList.get(key)).getType() == TaskType.REGULAR) {
                System.out.println("ID: " + key + ". Задача: " + dataBase.get(keyList.get(key)).getContent() + ". Статус задачи: " + dataBase.get(keyList.get(key)).getStatus());
            }
        }
    }

    static void printEpic() {
        System.out.println("Список ЭПИЧЕСКИХ задач:");
        for (Integer key : keyList.keySet()) {
            if (dataBase.get(keyList.get(key)).getType() == TaskType.EPIC) {
                System.out.println("ID: " + key + ". Задача: " + dataBase.get(keyList.get(key)).getContent() + ". Статус задачи: " + dataBase.get(keyList.get(key)).getStatus());
            }
        }
    }

    static void complete(int id) {
        Task support = dataBase.get(keyList.get(id));
        if (support.getType() == TaskType.SUBTASK) {//поиск эпика для сабтаска
            for (Integer key : keyList.keySet()) {
                if (dataBase.get(keyList.get(key)).getType() == TaskType.EPIC) {
                    var subList = dataBase.get(keyList.get(key)).getSubList();
                    if (subList.contains(id)) {
                        dataBase.get(keyList.get(id)).setStatus(Status.DONE);//установка эпика в DONE при изменении саба.
                        boolean allDone = true;
                        for (int i = 0; i < subList.size(); i++) {
                            Integer value = subList.get(i);
                            if (!(dataBase.get(keyList.get(id)).getStatus() == Status.DONE)) {
                                allDone = false;
                            }
                            if (allDone) {
                                dataBase.get(keyList.get(key)).setStatus(Status.DONE);
                            }
                        }
                    }
                }
            }
        }
        dataBase.get(keyList.get(id)).setStatus(Status.DONE);
        System.out.println("Задача выполнена..");
    }

    static void inProgress(int id) {
        Task support = dataBase.get(keyList.get(id));
        if (support.getType() == TaskType.SUBTASK) {//поиск эпика для сабтаска
            for (Integer key : keyList.keySet()) {
                if (dataBase.get(keyList.get(key)).getType() == TaskType.EPIC) {
                    var subList = dataBase.get(keyList.get(key)).getSubList();
                    if (subList.contains(id)) { //установка эпика в IN_Progress при изменении саба.
                        dataBase.get(keyList.get(key)).setStatus(Status.IN_PROGRESS);
                    }
                }
            }
        }
        dataBase.get(keyList.get(id)).setStatus(Status.IN_PROGRESS);
        System.out.println("Задача в процессе выполнения..");
    }

    static void deletePos(int id) {
        dataBase.remove(keyList.get(id));
        keyList.remove(id);
        System.out.println("Задача удалена");
    }

    static void eraseAll() {

        dataBase.clear();
        keyList.clear();

        System.out.println("Данные удалены");
    }
}
