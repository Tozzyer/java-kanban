import com.sun.net.httpserver.HttpServer;
import manager.*;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

class HttpServerTest {

    private HttpTaskServer server;
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
    void managersCreationUtilityTest() throws IOException {
        server = new HttpTaskServer();
        server.startServer();
        task1 = new Task("1", "Task1", Status.NEW);
        task2 = new Task("2", "Task2", Status.NEW);
        epic1 = new Epic("3", "Epic1", Status.NEW);
        sub1 = new SubTask("6", "Sub 1", Status.NEW, 3);
        task1.setStartTime(LocalDateTime.of(2000, 1, 1, 0, 0));
        task1.setDuration(Duration.ofMinutes(10));
        task2.setStartTime(LocalDateTime.of(2000, 1, 1, 0, 10));
        epic1.setDuration(Duration.ofMinutes(10));
        epic1.setStartTime(LocalDateTime.of(1999, 1, 1, 0, 0));
        task2.setDuration(Duration.ofMinutes(10));
        sub1.setStartTime(LocalDateTime.of(1970, 1, 1, 0, 0));
        sub1.setDuration(Duration.ofMinutes(10));
        server.getMaster().addTask(task1);
        server.getMaster().addTask(task2);
        server.getMaster().addEpic(epic1);
        server.getMaster().addSub(sub1);
    }

    @AfterEach
    public void shutDown() {
        server.stopServer();
    }

    @Test
    void testGetAllTasksEpicsSubs() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Assertions.assertEquals("[{\"id\":1,\"taskName\":\"1\",\"status\":\"NEW\",\"content\":\"Task1\",\"startTime\":\"2000-01-01T00:00:00\",\"duration\":10},{\"id\":2,\"taskName\":\"2\",\"status\":\"NEW\",\"content\":\"Task2\",\"startTime\":\"2000-01-01T00:10:00\",\"duration\":10}]", response.body());
        url = URI.create("http://localhost:8080/epics");
        request = HttpRequest.newBuilder().uri(url).GET().build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        url = URI.create("http://localhost:8080/subtasks");
        request = HttpRequest.newBuilder().uri(url).GET().build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void deletingTestForAllTypes() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Assertions.assertEquals("Задача 1 удалена", response.body());
        Assertions.assertEquals(1, server.getMaster().getAllTasks().size());
        url = URI.create("http://localhost:8080/tasks");
        request = HttpRequest.newBuilder().uri(url).DELETE().build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Assertions.assertEquals("Все задачи удалены", response.body());
        url = URI.create("http://localhost:8080/epics");
        request = HttpRequest.newBuilder().uri(url).DELETE().build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        url = URI.create("http://localhost:8080/subtasks");
        request = HttpRequest.newBuilder().uri(url).DELETE().build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Assertions.assertEquals(0, server.getMaster().getAllTasks().size());
        Assertions.assertEquals(0, server.getMaster().getAllEpics().size());
        Assertions.assertEquals(0, server.getMaster().getAllSubs().size());
    }

}