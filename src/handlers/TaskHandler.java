package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.FileBackedTaskManager;
import model.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import java.io.IOException;

public class TaskHandler implements HttpHandler {

    Gson gson;
    FileBackedTaskManager master;

    public TaskHandler(Gson gson,FileBackedTaskManager master){
        this.gson=gson;
        this.master=master;
        System.out.println("Конструктор отработал");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Обработчик вызвался");
        String command;
        Integer commandId=-1;
        String path = exchange.getRequestURI().getPath();
        System.out.println(path);
        String method = exchange.getRequestMethod();
        String[] pathDeplete = path.split("/");
        System.out.println("Путь разобран на "+pathDeplete[0]);
        try{
            commandId = Integer.parseInt(pathDeplete[1]);
            System.out.println("Сработал парсинг ID");
        } catch (NumberFormatException e){
            commandId = -1;
            System.out.println("Не сработал парсинг ID "+commandId);
        }

        if(commandId<0){
            System.out.println("Сработал метод без ID");
            noIdPath(method, exchange);

        } else {
            System.out.println("Сработал метод c ID");
            withIdPath(method, commandId, exchange);

        }


    }

    public void get(HttpExchange exchange) throws IOException {
        List<Task> tasks = (List<Task>) master.getAllTasks();
        String response = gson.toJson(tasks);
        sendResponse(exchange, response, 200);
    }

    public void post(HttpExchange exchange) throws IOException {
        try(InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8)){
            Task task = gson.fromJson(isr, Task.class);

            // Выводим объект Task
            System.out.println("Task ID: " + task.getId());
            System.out.println("Task Name: " + task.getTaskName());
            System.out.println("Status: " + task.getStatus());
            System.out.println("Content: " + task.getContent());
            System.out.println("Start Time: " + task.getStartTime());
            System.out.println("Duration: " + task.getDuration());
        }catch (Exception e) {
            System.err.println("Failed to parse JSON: " + e.getMessage());
            e.printStackTrace();
        }

//        BufferedReader reader = new BufferedReader(isr);
//
//        StringBuilder content = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            content.append(line);
//        }
//
//        System.out.println("Request body:");
//        System.out.println(content.toString());
//
//        // Не забудьте закрыть BufferedReader
//        reader.close();

        //Task task = gson.fromJson(isr, Task.class);



        //System.out.println(task);
        //master.addTask(task);
        //String response = "Задача "+task.getId()+" успешно создана";
        //sendResponse(exchange, response, 201);
    }

    public void delete(HttpExchange exchange) throws IOException {
        master.eraseTaskHashMap();
        String response = "Все задачи удалены";
        sendResponse(exchange,response,200);
    }
    public void getId(Integer id, HttpExchange exchange) throws IOException {
        Task task = master.getTask(id);
        String response = gson.toJson(task);
        sendResponse(exchange,response,200);
    }

    public void postId(Integer id, HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        Task task = gson.fromJson(isr, Task.class);
        task.setId(id);
        master.updateTask(task);
        String response = "Задача "+task.getId()+" успешно обновлена";
        sendResponse(exchange, response, 201);
    }

    public void deleteId(Integer id, HttpExchange exchange) throws IOException {
        master.removeTask(id);
        String response = "Задача "+id+" удалена";
        sendResponse(exchange,response,200);
    }
    public void dfM(){

    }

    public void noIdPath(String method, HttpExchange exchange) throws IOException {
        switch (method.toLowerCase()){
            case "get":
                System.out.println("get");
                get(exchange);
                break;
            case "post":
                System.out.println("post");
                post(exchange);
                break;
            case "delete":
                System.out.println("delete");
                delete(exchange);
                break;
            default:
                System.out.println("default");
                dfM();
                break;
        }
    }
    public void withIdPath(String method, Integer id, HttpExchange exchange) throws IOException {
        switch (method.toLowerCase()){
            case "get":
                getId(id, exchange);
                break;
            case "post":
                postId(id, exchange);
                break;
            case "delete":
                deleteId(id, exchange);
                break;
            default:
                dfM();
                break;
        }
    }

    public void sendResponse (HttpExchange httpExchange, String response, int statusCode) throws IOException {
        httpExchange.getResponseHeaders().set("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(statusCode,response.getBytes().length);
        OutputStream stream = httpExchange.getResponseBody();
        stream.write(response.getBytes());
        stream.close();
    }
}
