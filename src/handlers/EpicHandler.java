package handlers;

import adaptersForJson.EpicAdapter;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.CrossingException;
import manager.FileBackedTaskManager;
import model.Epic;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EpicHandler extends TaskHandler implements HttpHandler {


    public EpicHandler(FileBackedTaskManager master) {
        super(master);
    }

    @Override
    public void get(HttpExchange exchange) throws IOException {
        try {
            List<Epic> tasks = new ArrayList<>(master.getAllEpics());
            String response = gson.toJson(tasks);
            sendResponse(exchange, response, 200);
        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public void post(HttpExchange exchange) throws IOException {
        //Запускаем поток чтения тела запроса и создаём из него новый объект task, который отправляем в менеджер.
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        Epic task = gson.fromJson(isr, Epic.class);
        try {
            master.addEpic(task);
            String response = "Задача " + task.getId() + " успешно создана";
            sendResponse(exchange, response, 201);
        } catch (CrossingException e) {
            String response = e.getMessage();
            sendResponse(exchange, response, 406);
        }
    }

    @Override
    public void delete(HttpExchange exchange) throws IOException {
        master.eraseEpicHashMap();
        String response = "Все задачи удалены";
        sendResponse(exchange, response, 200);
    }

    @Override
    public void getId(Integer id, HttpExchange exchange) throws IOException {
        try {
            Epic task = master.getEpic(id);
            String response = gson.toJson(task);
            sendResponse(exchange, response, 200);
        } catch (Exception e) {
            String response = "Такой задачи не существует";
            sendResponse(exchange, response, 404);
        }
    }

    @Override
    public void postId(Integer id, HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        Epic task = gson.fromJson(isr, Epic.class);
        task.setId(id);
        master.updateEpic(task);
        String response = "Задача " + task.getId() + " успешно обновлена";
        sendResponse(exchange, response, 201);
    }

    @Override
    public void deleteId(Integer id, HttpExchange exchange) throws IOException {
        master.removeEpic(id);
        String response = "Задача " + id + " удалена";
        sendResponse(exchange, response, 200);
    }

    @Override
    protected void gsonInitializator() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Epic.class, new EpicAdapter());
        this.gson = gsonBuilder.create();
    }
}
