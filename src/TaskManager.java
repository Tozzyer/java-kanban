import java.util.Collection;
import java.util.HashMap;
public class TaskManager {

    //Хранение всех типов
    private Integer taskId = 0;
    //все переменные хранилищ private final и не используй в названии List. Это тип коллекций нельзя использовать в названии переменных
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subs = new HashMap<>();

    //Тип задач Regular


    //Получение всех задач
    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    //Получение по идентификатору
    public Task getTaskValue(Integer id) {
        return tasks.get(id);
    }

    //Удаление всех задач
    public void eraseTaskHashMap() {
        tasks.clear();
    }

    //Создание задачи
    public void addTask(Task task) {
        taskId++;
        tasks.put(taskId, task);
        task.setId(taskId);
    }

    //Обновление задачи
    public void updateTask(Task task, Integer id) {
        tasks.put(taskId, task);
        task.setId(taskId);
    }

    //Удаление по идентификатору
    public void removeTask(Integer id) {
        tasks.remove(id);
    }

    //Тип задач Epic

    //Получение всех эпик задач
    public Collection<Epic> getAllEpics() {
        return epics.values();
    }

    //Получение по идентификатору
    public Epic getEpicValue(Integer id) {

        return epics.get(id);
    }

    //Удаление всех задач
    public void eraseEpicHashMap() {
        eraseSubHashMap();
        epics.clear();
    }

    //Создание задачи
    public void addEpic(Epic task) {
        taskId++;
        epics.put(taskId, task);
        task.setId(taskId);
    }

    //Обновление задачи
    public void updateEpic(Epic task) {
        epics.put(taskId, task);
        task.setId(taskId);
    }

    //Удаление по идентификатору
    public void removeEpic(Integer id) {
        for (Integer key : getEpicValue(id).getEpicSubs().keySet()) {
            subs.remove(key);
        }
        epics.remove(id);
    }





    public Collection<SubTask> getAllSubs() {
        return subs.values();
    }

    //Получение по идентификатору
    public SubTask getSubValue(Integer id) {
        return subs.get(id);
    }

    //Удаление всех подзадач (без удаления эпиков. Эпики выставляются в статус NEW, так как подзадач нет.)
    public void eraseSubHashMap() {
        subs.clear();
        for (Epic epic : epics.values()) {
            epic.clearSub();
            epic.statusUpdate();
        }
    }


    //Создание задачи, добавление к существующей Epic
    public void addSub(SubTask task) {
        taskId++;
        subs.put(taskId, task);
        task.setId(taskId);
        getEpicValue(task.getMasterId()).addSubTask(task);
        getEpicValue(task.getMasterId()).statusUpdate();
    }

    //Обновление задачи
    public void updateSubTask(SubTask task) {
        subs.put(task.getId(), task);
        epics.get(task.getMasterId()).addSubTask(task);
        getEpicValue(task.getMasterId()).statusUpdate();
    }

    //Удаление по идентификатору
    public void removeSubTask(Integer id) {
        subs.remove(id);
        for (Epic epic : epics.values()) {
            if (epic.epicSubs.containsKey(id)) {
                epic.epicSubs.remove(id);
                epic.statusUpdate();
            }
        }
    }

}
