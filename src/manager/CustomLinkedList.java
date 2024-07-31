package manager;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class CustomLinkedList<T> {
    public Node<Task> headStore;
    public Node<Task> tailStore;
    public static HashMap<Integer, Node<Task>> store = new HashMap<>();

    public void add(Task task) {
        if (store.containsKey(task.getId())) {
            remove(store.get(task.getId()));
        }
        Node<Task> oldTail = tailStore;
        Node<Task> newNode = new Node<>(oldTail, task, null);
        if (oldTail == null) {
            headStore = newNode;
        } else {
            oldTail.next = newNode;
        }

        tailStore = newNode;
        store.put(task.getId(), newNode);
    }

    public List<Task> toList() {
        List<Task> list = new ArrayList<>();
        Node<Task> current = headStore;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    public void remove(Node<Task> taskNode) {

        if (taskNode.prev == null && taskNode.next == null) {
            headStore = null;
            tailStore = null;
        } else if (taskNode.prev != null && taskNode.next == null) {
            taskNode.prev.next = null;
            tailStore = taskNode.prev;
            store.remove(taskNode.data.getId());
            taskNode.prev = null;
            taskNode.next = null;
            taskNode.data = null;
        } else if (taskNode.prev == null && taskNode.next != null) {
            taskNode.next.prev = null;
            headStore = taskNode.next;
            store.remove(taskNode.data.getId());
            taskNode.prev = null;
            taskNode.next = null;
            taskNode.data = null;
        } else {
            taskNode.prev.next = taskNode.next;
            taskNode.next.prev = taskNode.prev;
            store.remove(taskNode.data.getId());
            taskNode.prev = null;
            taskNode.next = null;
            taskNode.data = null;
        }
    }

}