import java.util.Collection;

public interface TaskManager {
    public Collection<Task> getAllTasks();
    public Task getTaskValue(Integer id);
    public void eraseTaskHashMap();
    public void addTask(Task task);
    public void updateTask(Task task, Integer id);
    public void removeTask(Integer id);
    public Collection<Epic> getAllEpics();
    public Epic getEpicValue(Integer id);
    public void eraseEpicHashMap();
    public void addEpic(Epic task);
    public void updateEpic(Epic task);
    public void removeEpic(Integer id);
    public Collection<SubTask> getAllSubs();
    public SubTask getSubValue(Integer id);
    public void eraseSubHashMap();
    public void addSub(SubTask task);
    public void updateSubTask(SubTask task);
    public void removeSubTask(Integer id);
}
