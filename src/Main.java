import manager.FileBackedTaskManager;
import manager.HistoryManager;
import manager.Managers;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

public class Main {

    public static void main(String[] args) {
        FileBackedTaskManager master = new FileBackedTaskManager();
        HistoryManager historian = Managers.getDefaultHistory();

        Task task1 = new Task("1", "Task1", Status.NEW);
        Task task2 = new Task("2", "Task2", Status.NEW);
        Epic epic1 = new Epic("3", "Epic1", Status.NEW);
        SubTask sub1 = new SubTask("6", "Sub 1", Status.NEW, 3);
        SubTask sub2 = new SubTask("7", "Sub 2", Status.NEW, 3);
        SubTask sub3 = new SubTask("8", "Sub 3", Status.NEW, 3);
        SubTask sub4 = new SubTask("9", "Sub 4", Status.NEW, 3);
        Epic epic2 = new Epic("4", "Epic2", Status.NEW);
        SubTask sub5 = new SubTask("10", "Sub 5", Status.NEW, 4);
        SubTask sub6 = new SubTask("11", "Sub 6", Status.NEW, 4);
        SubTask sub7 = new SubTask("12", "Sub 7", Status.NEW, 4);
        SubTask sub8 = new SubTask("13", "Sub 8", Status.NEW, 4);
        Epic epic3 = new Epic("5", "model.Epic 3", Status.NEW);

    }
}
