package manager;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    //Хранение всех типов
    private Integer taskId = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subs = new HashMap<>();
    public HistoryManager historian = new InMemoryHistoryManager();


    //Получение всех задач
    @Override
    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    //Получение по идентификатору
    @Override
    public Task getTask(Integer id) {
        historian.add(tasks.get(id));
        return tasks.get(id);
    }

    //Удаление всех задач
    @Override
    public void eraseTaskHashMap() {
        tasks.clear();
    }

    //Создание задачи
    @Override
    public void addTask(Task task) {
        taskId++;
        tasks.put(taskId, task);
        task.setId(taskId);
    }

    //Обновление задачи
    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    //Удаление по идентификатору
    @Override
    public void removeTask(Integer id) {
        tasks.remove(id);
    }

    //Тип задач model.Epic

    //Получение всех эпик задач
    @Override
    public Collection<Epic> getAllEpics() {
        return epics.values();
    }

    //Получение по идентификатору
    @Override
    public Epic getEpic(Integer id) {
        historian.add(epics.get(id));
        return epics.get(id);
    }

    //Удаление всех задач
    @Override
    public void eraseEpicHashMap() {
        eraseSubHashMap();
        epics.clear();
    }

    //Создание задачи
    @Override
    public void addEpic(Epic task) {
        taskId++;
        epics.put(taskId, task);
        task.setId(taskId);
    }

    //Обновление задачи
    @Override
    public void updateEpic(Epic task) {
        epics.put(task.getId(), task);
    }

    //Удаление по идентификатору
    @Override
    public void removeEpic(Integer id) {
        for (Integer key : epics.get(id).getEpicSubs().keySet()) {
            subs.remove(key);
        }
        epics.remove(id);
    }


    @Override
    public Collection<SubTask> getAllSubs() {
        return subs.values();
    }

    @Override
    //Получение по идентификатору
    public SubTask getSubtask(Integer id) {
        historian.add(subs.get(id));
        return subs.get(id);
    }

    //Удаление всех подзадач (без удаления эпиков. Эпики выставляются в статус NEW, так как подзадач нет.)
    @Override
    public void eraseSubHashMap() {
        subs.clear();
        for (Epic epic : epics.values()) {
            epic.clearSub();
            epic.statusUpdate();
        }
    }


    //Создание задачи, добавление к существующей model.Epic
    @Override
    public void addSub(SubTask task) {
        taskId++;
        subs.put(taskId, task);
        task.setId(taskId);
        epics.get(task.getMasterId()).addSubTask(task);
        epics.get(task.getMasterId()).statusUpdate();
    }

    //Обновление задачи
    @Override
    public void updateSubTask(SubTask task) {
        subs.put(task.getId(), task);
        epics.get(task.getMasterId()).addSubTask(task);
        epics.get(task.getMasterId()).statusUpdate();
    }

    //Удаление по идентификатору
    @Override
    public void removeSubTask(Integer id) {
        subs.remove(id);
        for (Epic epic : epics.values()) {
            if (epic.getEpicSubs().containsKey(id)) {
                epic.getEpicSubs().remove(id);
                epic.statusUpdate();
            }
        }
    }

    //Вызов истории
    @Override
    public ArrayList<Task> getHistory() {
        return historian.getHistory();
    }


}
