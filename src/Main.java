import manager.HistoryManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager master;
        HistoryManager historian;
        Task task1;
        Task task2;
        Epic epic1;
        SubTask sub1;
        SubTask sub2;
        SubTask sub3;
        SubTask sub4;
        Epic epic2;
        SubTask sub5;
        SubTask sub6;
        SubTask sub7;
        SubTask sub8;
        Epic epic3;

        master = (InMemoryTaskManager) Managers.getDefault();
        historian = Managers.getDefaultHistory();
        task1 = new Task("1", "Task1", Status.NEW);
        task2 = new Task("2", "Task2", Status.NEW);
        epic1 = new Epic("3", "Epic1", Status.NEW);
        sub1 = new SubTask("6", "Sub 1", Status.NEW, 3);
        sub2 = new SubTask("7", "Sub 2", Status.NEW, 3);
        sub3 = new SubTask("8", "Sub 3", Status.NEW, 3);
        sub4 = new SubTask("9", "Sub 4", Status.NEW, 3);
        epic2 = new Epic("4", "Epic2", Status.NEW);
        sub5 = new SubTask("10", "Sub 5", Status.NEW, 4);
        sub6 = new SubTask("11", "Sub 6", Status.NEW, 4);
        sub7 = new SubTask("12", "Sub 7", Status.NEW, 4);
        sub8 = new SubTask("13", "Sub 8", Status.NEW, 4);
        epic3 = new Epic("5", "model.Epic 3", Status.NEW);

        LinkedHashSet<Task> historyTest = new LinkedHashSet<>();
        master.addTask(task1);
        master.addTask(task2);
        master.addEpic(epic1);
        for (int i = 0; i < 3; i++) { //добавили 9 раз
            master.getTask(2);
            historyTest.add(task2);
            master.getTask(1);
            historyTest.add(task1);
            master.getEpic(3);
            historyTest.add(epic1);
        }
        master.getTask(2);
        historyTest.add(task1); //добавили 10 раз
        master.getTask(1);
        historyTest.add(task2);//добавили 11 раз
        System.out.println(master.getHistory());

    }
}
