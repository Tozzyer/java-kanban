package manager;

import com.sun.net.httpserver.HttpServer;
import handlers.*;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {

    private HttpServer server;
    final private int applicationPort = 8080;
    private FileBackedTaskManager master = new FileBackedTaskManager();

    public HttpTaskServer() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(ApplicationPort), 0);
    }

    public void startServer() {
        server.createContext("/tasks", new TaskHandler(master));
        server.createContext("/epics", new EpicHandler(master));
        server.createContext("/subtasks", new SubHandler(master));
        server.createContext("/history", new HistoryHandler(master));
        server.createContext("/prioritized", new PriorityHandler(master));
        server.start();
        System.out.println("Сервер запущен на порту: " + ApplicationPort);
    }

    public void stopServer() {
        server.stop(10);
        System.out.println("Сервер остановлен");
    }

    public void test() {
        Task task1 = new Task("1", "Task1", Status.NEW);
        Task task2 = new Task("2", "Task2", Status.NEW);
        Epic epic1 = new Epic("3", "Epic1", Status.NEW);
        SubTask sub1 = new SubTask("6", "Sub 1", Status.NEW, 3);
        task1.setStartTime(LocalDateTime.of(2000, 1, 1, 0, 0));
        task1.setDuration(Duration.ofMinutes(10));
        task2.setStartTime(LocalDateTime.of(2000, 1, 1, 0, 10));
        epic1.setDuration(Duration.ofMinutes(10));
        epic1.setStartTime(LocalDateTime.of(1999, 1, 1, 0, 0));
        task2.setDuration(Duration.ofMinutes(10));
        sub1.setStartTime(LocalDateTime.of(1970, 1, 1, 0, 0));
        sub1.setDuration(Duration.ofMinutes(10));
        master.addTask(task1);
        master.addTask(task2);
        master.addEpic(epic1);
        master.addSub(sub1);
    }

    public FileBackedTaskManager getMaster() {
        return master;
    }

}
