import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Testing..");
        TaskManager master = new TaskManager();
        Task task1 = new Task("1", "Поменять колёса", Status.NEW);
        Task task2 = new Task("2", "Помыть машину", Status.NEW);
        Epic epic1 = new Epic("3", "Накачать все колёса", Status.NEW);
        SubTask sub1 = new SubTask("6", "Sub 1", Status.NEW);
        SubTask sub2 = new SubTask("7", "Sub 2", Status.NEW);
        SubTask sub3 = new SubTask("8", "Sub 3", Status.NEW);
        SubTask sub4 = new SubTask("9", "Sub 4", Status.NEW);
        Epic epic2 = new Epic("4", "Полетать", Status.NEW);
        SubTask sub5 = new SubTask("10", "Заправить 98", Status.NEW);
        SubTask sub6 = new SubTask("11", "Подать питание", Status.NEW);
        SubTask sub7 = new SubTask("12", "Включить", Status.NEW);
        SubTask sub8 = new SubTask("13", "Колесо 8", Status.NEW);
        Epic epic3 = new Epic("5", "Пустой", Status.NEW);
        master.addTask(task1);//ID1
        master.addTask(task2);//ID2
        master.addEpic(epic1);//ID3
        master.addEpic(epic2);//ID4
        master.addEpic(epic3);//ID5
        master.addSub(sub1, 3);//6
        master.addSub(sub2, 3);//7
        master.addSub(sub3, 3);//8
        master.addSub(sub4, 3);//9
        master.addSub(sub5, 4);//10
        master.addSub(sub6, 4);//11
        master.addSub(sub7, 4);//12
        master.addSub(sub8, 4);//13

        master.getAllTaskList();
        master.getAllEpicList();
        master.getAllSubList();
        System.out.println("====EPIC STATUS CHECK====");
        task1.setStatus(Status.DONE);
        task1.setContent("Изменение состояния");
        master.updateTask(task1, 1);
        sub1.setStatus(Status.IN_PROGRESS);
        master.updateSubTask(sub1, 6, 3);
        sub2.setStatus(Status.DONE);
        master.updateSubTask(sub2, 7, 3);
        sub5.setStatus(Status.DONE);
        master.updateSubTask(sub5, 10, 4);
        sub6.setStatus(Status.DONE);
        master.updateSubTask(sub6, 11, 4);
        sub7.setStatus(Status.DONE);
        master.updateSubTask(sub7, 12, 4);
        sub8.setStatus(Status.DONE);
        master.updateSubTask(sub8, 13, 4);


        master.getAllTaskList();
        master.getAllEpicList();
        master.getAllSubList();
    }
}
