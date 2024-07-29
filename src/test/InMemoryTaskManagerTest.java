package test;

import manager.HistoryManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;

class InMemoryTaskManagerTest {

    private static InMemoryTaskManager master;
    private static HistoryManager historian;
    private static Task task1;
    private static Task task2;
    private static Epic epic1;
    private static SubTask sub1;
    private static SubTask sub2;
    private static SubTask sub3;
    private static SubTask sub4;
    private static Epic epic2;
    private static SubTask sub5;
    private static SubTask sub6;
    private static SubTask sub7;
    private static SubTask sub8;
    private static Epic epic3;

    @BeforeEach
    void managersCreationUtilityTest() {
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
    }

    @Test
        //Тест проверяет, что manager.InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id
    void addTasksAndCheckIdTest() {

        master.addTask(task1);
        master.addTask(task2);
        master.addEpic(epic1);
        master.addSub(sub1);
        Assertions.assertEquals(task1, master.getTask(1));
        Assertions.assertEquals(task2, master.getTask(2));
        Assertions.assertEquals(epic1, master.getEpic(3));
        Assertions.assertEquals(sub1, master.getSubtask(4));
    }

    @Test
    void checkTasksFieldsInTaskManager() {
        master.addTask(task1);
        Assertions.assertEquals("1", master.getTask(1).getTaskName());
        Assertions.assertEquals("Task1", master.getTask(1).getContent());
        Assertions.assertEquals(Status.NEW, master.getTask(1).getStatus());
    }

    @Test
    void taskIdCheckRequest() {
        master.addTask(task1);
        Assertions.assertEquals(task1, master.getTask(1));
    }

    @Test
    void historyCheck() {
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
        Assertions.assertEquals(new ArrayList<>(historyTest), master.getHistory());//вернули и сравнили
    }

    @Test
    void historyDeletingProcessCheck() {
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
        historyTest.add(task2); //добавили 10 раз
        master.getTask(1);
        historyTest.add(task1);//добавили 11 раз
        historyTest.remove(task2);
        master.remove(2);
        Assertions.assertEquals(new ArrayList<>(historyTest), master.getHistory());//вернули и сравнили
    }
}