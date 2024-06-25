import java.util.Collection;
import java.util.HashMap;
public class TaskManager {

    //Хранение всех типов
    private Integer taskId = 0;
    //все переменные хранилищ private final и не используй в названии List. Это тип коллекций нельзя использовать в названии переменных
    private final HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    private final HashMap<Integer, SubTask> subHashMap = new HashMap<>();

    //Тип задач Regular
    //Метод печати для отладки
    public void printAllTask() {
        for (Integer key : taskHashMap.keySet()) {
            System.out.println("ID: " + key + ". Object: " + taskHashMap.get(key));
        }
    }

    //Получение всех задач. В виде коллекции, ArrayList или иного массива?
    public Collection<Task> getAllTasks() {
        return taskHashMap.values();
    }

    //Получение по идентификатору
    public Task getTaskValue(Integer id) {
        return taskHashMap.get(id);
    }

    //Удаление всех задач
    public void eraseTaskHashMap() {
        taskHashMap.clear();
    }

    //Создание задачи
    public void addTask(Task task) {
        taskId++;
        taskHashMap.put(taskId, task);
        task.setId(taskId);
    }

    //Обновление задачи
    public void updateTask(Task task, Integer id) {
        taskHashMap.put(taskId, task);
        task.setId(taskId);
    }

    //Удаление по идентификатору
    public void removeTask(Integer id) {
        taskHashMap.remove(id);
    }

    //Тип задач Epic
    //Печать для отладки
    public void printAllEpic() {
        for (Integer key : epicHashMap.keySet()) {
            System.out.println("ID: " + key + ". Object: " + epicHashMap.get(key));
        }
    }

    //Получение всех эпик задач
    public Collection<Epic> getAllEpics() {
        return epicHashMap.values();
    }

    //Получение по идентификатору
    public Epic getEpicValue(Integer id) {

        return epicHashMap.get(id);
    }

    //Удаление всех задач
    public void eraseEpicHashMap() {
        eraseSubHashMap();
        epicHashMap.clear();
    }

    //Создание задачи
    public void addEpic(Epic task) {
        taskId++;
        epicHashMap.put(taskId, task);
        task.setId(taskId);
    }

    //Обновление задачи
    public void updateEpic(Epic task, Integer id) {
        epicHashMap.put(taskId, task);
        task.setId(taskId);
    }

    //Удаление по идентификатору
    public void removeEpic(Integer id) {
        for (Integer key : getEpicValue(id).getEpicSubHashMap().keySet()) {
            subHashMap.remove(key);
        }
        epicHashMap.remove(id);
    }

    //Получение списка всех подзадач определённого эпика
    public void printEpicSlaves(Integer epicId) {
        getEpicValue(epicId).printAllEpicSubTasks();
    }
    //Тип задач SubTask:

    //Печать
    public void printAllSubs() {
        for (Integer key : subHashMap.keySet()) {
            System.out.println("ID: " + key + ". Object: " + subHashMap.get(key));
        }
    }

    public Collection<SubTask> getAllSubs() {
        return subHashMap.values();
    }

    //Получение по идентификатору
    public SubTask getSubValue(Integer id) {
        return subHashMap.get(id);
    }

    //Удаление всех подзадач (без удаления эпиков. Эпики выставляются в статус NEW, так как подзадач нет.)
    public void eraseSubHashMap() {
        subHashMap.clear();
        for (Epic epic : epicHashMap.values()) {
            epic.clearSub();
            epic.statusUpdate();
        }
    }


    //Создание задачи, добавление к существующей Epic
    public void addSub(SubTask task) {
        taskId++;
        subHashMap.put(taskId, task);
        task.setId(taskId);
        getEpicValue(task.getMasterId()).addSubTask(task);
        getEpicValue(task.getMasterId()).statusUpdate();
    }

    //Обновление задачи
    public void updateSubTask(SubTask task, Integer id, Integer epicId) {
        subHashMap.put(taskId, task);
        task.setId(taskId);
        epicHashMap.get(epicId).addSubTask(task);
        getEpicValue(epicId).statusUpdate();
    }

    //Удаление по идентификатору
    public void removeSubTask(Integer id) {
        subHashMap.remove(id);
        for (Epic epic : epicHashMap.values()) {
            if (epic.epicSubHashMap.containsKey(id)) {
                epic.epicSubHashMap.remove(id);
                epic.statusUpdate();
            }
        }
    }

}
