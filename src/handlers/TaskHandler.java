package handlers;

import AdaptersForJSON.TaskAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.CrossingException;
import manager.FileBackedTaskManager;
import model.Task;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;



public class TaskHandler implements HttpHandler {

    Gson gson;
    GsonBuilder gsonBuilder;
    FileBackedTaskManager master;


    public TaskHandler(FileBackedTaskManager master){
        this.master=master;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Task.class, new TaskAdapter());
        this.gson = gsonBuilder.create();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Обработчик вызвался");
        Integer commandId=-1;
        String path = exchange.getRequestURI().getPath();
        System.out.println(path);
        String method = exchange.getRequestMethod();
        String[] pathDeplete = path.split("/");
        try{
            commandId = Integer.parseInt(pathDeplete[2]);
            System.out.println("Сработал парсинг ID");
        } catch (Exception e){
            commandId = -1;
            System.out.println("Не сработал парсинг ID "+commandId);
        }

        if(commandId<0){
            noIdPath(method, exchange);

        } else {
            withIdPath(method, commandId, exchange);

        }


    }

    public void get(HttpExchange exchange) throws IOException {
        List<Task> tasks = new ArrayList<>(master.getAllTasks());
        System.out.println(tasks);
        String response = gson.toJson(tasks);
        System.out.println(response);
        sendResponse(exchange, response, 200);
    }

    public void post(HttpExchange exchange) throws IOException {
        //Запускаем поток чтения тела запроса и создаём из него новый объект task, который отправляем в менеджер.
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        Task task = gson.fromJson(isr, Task.class);
        System.out.println(task);
        try{
            master.addTask(task);
            String response = "Задача "+task.getId()+" успешно создана";
            sendResponse(exchange, response, 201);
        } catch (CrossingException e){
            String response = e.getMessage();
            sendResponse(exchange, response, 406);
        }
        //Высылаем ответ клиенту

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
