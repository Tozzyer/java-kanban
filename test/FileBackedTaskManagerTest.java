import manager.FileBackedTaskManager;
import manager.HistoryManager;
import manager.Managers;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileBackedTaskManagerTest {

    private static FileBackedTaskManager master;
    private static HistoryManager historian;
    private static Task task1;
    private static Task task2;
    private static Epic epic1;
    private static SubTask sub1;

    @BeforeEach
    void managersCreationUtilityTest() {
        master = new FileBackedTaskManager("true");
        historian = Managers.getDefaultHistory();
        task1 = new Task("1", "Task1", Status.NEW);
        task2 = new Task("2", "Task2", Status.NEW);
        epic1 = new Epic("3", "Epic1", Status.NEW);
        sub1 = new SubTask("6", "Sub 1", Status.NEW, 3);
    }

    @Test
    void checkFileReadAndWriteProcess() {
        master.addTask(task1);
        Assertions.assertEquals("1", master.getTask(1).getTaskName());
        Assertions.assertEquals("Task1", master.getTask(1).getContent());
        Assertions.assertEquals(Status.NEW, master.getTask(1).getStatus());
        ArrayList<String> data = new ArrayList<>();

        try {
            data = (ArrayList<String>) Files.readAllLines(master.taskFile);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении из файла");
        }
        Assertions.assertEquals("1,Task,1,NEW,Task1", data.get(1));
        Assertions.assertNotEquals("Task,1,NEW,Task1", data.get(1));
    }

    @Test
    void checkFileReadAndWriteProcessMore() {
        master.addTask(task1);
        master.addTask(task2);
        master.addEpic(epic1);
        master.addSub(sub1);
        Assertions.assertEquals("1", master.getTask(1).getTaskName());
        Assertions.assertEquals("Task1", master.getTask(1).getContent());
        Assertions.assertEquals(Status.NEW, master.getTask(1).getStatus());
        ArrayList<String> data = new ArrayList<>();
        master.removeTask(1);
        task2 = new Task("2", "Task2", Status.DONE);
        task2.setId(2);
        master.updateTask(task2);

        try {
            data = (ArrayList<String>) Files.readAllLines(master.taskFile);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении из файла");
        }
        Assertions.assertEquals("2,Task,2,DONE,Task2", data.get(1));
        Assertions.assertNotEquals("Task,1,NEW,Task1", data.get(1));
    }

}
