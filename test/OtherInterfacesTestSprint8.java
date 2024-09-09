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
import java.time.Duration;
import java.time.LocalDateTime;

public class OtherInterfacesTestSprint8 {

    private static FileBackedTaskManager master;
    private static HistoryManager historian;
    private static Task task1;
    private static Task task2;
    private static Epic epic1;
    private static SubTask sub1;
    private static SubTask sub2;
    private static SubTask sub3;

    @BeforeEach
    void managersCreationUtilityTest() {
        master = new FileBackedTaskManager("true");
        historian = Managers.getDefaultHistory();
        task1 = new Task("1", "Task1", Status.NEW);
        task2 = new Task("2", "Task2", Status.NEW);
        epic1 = new Epic("3", "Epic1", Status.NEW);
        sub1 = new SubTask("6", "Sub1", Status.NEW, 3);
        sub2 = new SubTask("7", "Sub2", Status.NEW, 3);
        sub3 = new SubTask("8", "Sub3", Status.NEW, 3);
    }

    @Test
    void checkEpicStatusChanging() {
        master.addTask(task1);
        master.addTask(task2);
        master.addEpic(epic1);
        master.addSub(sub1);
        Assertions.assertEquals(Status.NEW, master.getEpic(3).getStatus());
        sub2.setStatus(Status.IN_PROGRESS);
        master.addSub(sub2);
        master.updateSubTask(new SubTask("7", "Sub2", Status.IN_PROGRESS, 3));
        Assertions.assertEquals(Status.IN_PROGRESS, master.getEpic(3).getStatus());
        master.addSub(sub3);
        master.updateSubTask(new SubTask("6", "Sub1", Status.DONE, 3));
        master.updateSubTask(new SubTask("7", "Sub2", Status.DONE, 3));
        master.updateSubTask(new SubTask("8", "Sub3", Status.DONE, 3));
        Assertions.assertEquals(Status.DONE, master.getEpic(3).getStatus());
    }

    @Test
    void checkTimeManagerPriority() {
        task1.setStartTime(LocalDateTime.of(2002, 1, 1, 0, 10));
        task1.setDuration(Duration.ofMinutes(10));
        task2.setStartTime(LocalDateTime.of(2000, 1, 1, 0, 0));
        task2.setDuration(Duration.ofMinutes(10));
        epic1.setDuration(Duration.ofMinutes(10));
        epic1.setStartTime(LocalDateTime.of(1990, 1, 1, 0, 0));
        master.addTask(task2);
        master.addTask(task1);
        master.addEpic(epic1);
        Assertions.assertEquals(master.getEpic(3), master.getPrioritizedTasks().get(0));
        Assertions.assertEquals(master.getTask(2), master.getPrioritizedTasks().get(2));
        Assertions.assertEquals(master.getTask(1), master.getPrioritizedTasks().get(1));
    }

    @Test
    void checkCrossTimeTest() {
        task1.setStartTime(LocalDateTime.of(2000, 1, 1, 0, 10));
        task1.setDuration(Duration.ofMinutes(10));
        task2.setStartTime(LocalDateTime.of(2000, 1, 1, 0, 10));
        task2.setDuration(Duration.ofMinutes(10));
        master.addTask(task1);
        master.addTask(task2);
        task2.setStartTime(LocalDateTime.of(2000, 1, 1, 0, 1));
        task2.setDuration(Duration.ofMinutes(10));
        master.addTask(task2);
        task2.setStartTime(LocalDateTime.of(2000, 1, 1, 0, 19));
        task2.setDuration(Duration.ofMinutes(10));
        master.addTask(task2);
        Assertions.assertEquals(1, master.getAllTasks().size());
        task2.setStartTime(LocalDateTime.of(2000, 1, 1, 0, 20));
        task2.setDuration(Duration.ofMinutes(10));
        master.addTask(task2);
        Assertions.assertEquals(2, master.getAllTasks().size());
    }

    void checkEpicTimeUpdate() {
        master.addTask(task1);
        master.addTask(task2);
        epic1.setStartTime(LocalDateTime.of(2000, 1, 1, 0, 10));
        epic1.setDuration(Duration.ofMinutes(10));
        master.addEpic(epic1);
        Assertions.assertEquals(LocalDateTime.of(2000, 1, 1, 0, 10), epic1.getStartTime());
        sub1.setStartTime(LocalDateTime.of(2000, 1, 1, 0, 0));
        sub1.setDuration(Duration.ofMinutes(60));
        Assertions.assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), epic1.getStartTime());
        Assertions.assertEquals(LocalDateTime.of(2000, 1, 1, 0, 60), epic1.getEndTime());
    }
}
