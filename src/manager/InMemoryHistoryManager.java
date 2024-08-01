package manager;

import model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList<Task> taskHistory = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        taskHistory.add(task);
    }

    @Override
    public void remove(int id) {
        taskHistory.remove(CustomLinkedList.store.get(id));
        CustomLinkedList.store.remove(id);
    }

    @Override
    public ArrayList getHistory() {

        return (ArrayList<Task>) taskHistory.toList();
    }
}


