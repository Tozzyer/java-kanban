package manager;
//test
import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.Collection;

public interface TaskManager {

    public Collection<Task> getAllTasks();
    public Task getTask(Integer id);
    public void eraseTaskHashMap();
    public void addTask(Task task);
    public void updateTask(Task task);
    public void removeTask(Integer id);
    public Collection<Epic> getAllEpics();
    public Epic getEpic(Integer id);
    public void eraseEpicHashMap();
    public void addEpic(Epic task);
    public void updateEpic(Epic task);
    public void removeEpic(Integer id);
    public Collection<SubTask> getAllSubs();
    public SubTask getSubtask(Integer id);
    public void eraseSubHashMap();
    public void addSub(SubTask task);
    public void updateSubTask(SubTask task);
    public void removeSubTask(Integer id);
    public ArrayList<Task> getHistory();
    public void remove(int id);
}
