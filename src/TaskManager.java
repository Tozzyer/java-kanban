import java.util.HashMap;
//rev3
public class TaskManager {

    //Хранение всех типов
    Integer taskId = 0;
    HashMap<Integer, Task> taskList = new HashMap<>();
    HashMap<Integer, Epic> epicList = new HashMap<>();
    HashMap<Integer, SubTask> subList = new HashMap<>();

    //Тип задач Regular
    //Получение всех задач
    void getAllTaskList() {
        for (Integer key : taskList.keySet()) {
            System.out.println("ID: " + key + ". Object: " + taskList.get(key));
        }
    }

    //Получение по идентификатору
    Task getTaskList(Integer id) {
        System.out.println("Object: " + taskList.get(id));
        return taskList.get(id);
    }

    //Удаление всех задач
    void eraseTaskList() {
        taskList.clear();
    }

    //Создание задачи
    void addTask(Task task) {
        taskId++;
        taskList.put(taskId, task);
        task.setId(taskId);
    }

    //Обновление задачи
    void updateTask(Task task, Integer id) {
        taskList.put(taskId, task);
        task.setId(taskId);
    }

    //Удаление по идентификатору
    void removeTask(Integer id) {
        taskList.remove(id);
    }

    //Тип задач Epic
    //Получение всех задач
    void getAllEpicList() {
        for (Integer key : epicList.keySet()) {
            System.out.println("ID: " + key + ". Object: " + epicList.get(key));
        }
    }

    //Получение по идентификатору
    Epic getEpicList(Integer id) {

        return epicList.get(id);
    }

    //Удаление всех задач
    void eraseEpicList() {
        epicList.clear();
    }

    //Создание задачи
    void addEpic(Epic task) {
        taskId++;
        epicList.put(taskId, task);
        task.setId(taskId);
    }

    //Обновление задачи
    void updateEpic(Epic task, Integer id) {
        epicList.put(taskId, task);
        task.setId(taskId);
    }

    //Удаление по идентификатору
    void removeEpic(Integer id) {
        epicList.remove(id);
    }

    //Получение списка всех подзадач определённого эпика
    void getEpicSubList(Integer epicId) {
        getEpicList(epicId).printAllSubList();
    }
    //Тип задач SubTask:

    //Получение всех задач
    void getAllSubList() {
        for (Integer key : subList.keySet()) {
            System.out.println("ID: " + key + ". Object: " + subList.get(key));
        }
    }

    //Получение по идентификатору
    Task getSubList(Integer id) {
        System.out.println("Object: " + subList.get(id));
        return subList.get(id);
    }

    //Удаление всех подзадач
    void eraseSubList() {
        subList.clear();
        for (Epic epic : epicList.values()) {
            epic.clearSub();
            epic.statusUpdate();
        }
    }

    //Создание задачи, добавление к существующей Epic
    void addSub(SubTask task, Integer epicId) {
        taskId++;
        subList.put(taskId, task);
        task.setId(taskId);
        getEpicList(epicId).addSubTask(task);
        getEpicList(epicId).statusUpdate();
    }

    //Обновление задачи
    void updateSubTask(SubTask task, Integer id, Integer epicId) {
        subList.put(taskId, task);
        task.setId(taskId);
        epicList.get(epicId).addSubTask(task);
        getEpicList(epicId).statusUpdate();
    }

    //Удаление по идентификатору
    void removeSubTask(Integer id) {
        taskList.remove(id);
        for (Epic epic : epicList.values()) {
            if (epic.epicSubList.containsKey(id)) {
                epic.epicSubList.remove(id);
                epic.statusUpdate();
            }
        }
    }

}
