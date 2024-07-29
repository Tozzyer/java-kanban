package manager;

import model.Task;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class InMemoryHistoryManager implements HistoryManager {

    private final LinkedHashSet<Task> taskHistory = new LinkedHashSet<>();

    @Override
    public void add(Task task) {
  taskHistory.add(task);
    }

    @Override
    public void remove(int id) {
        taskHistory.removeIf(iter -> id == iter.getId());
    }

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(taskHistory);
    }
}
