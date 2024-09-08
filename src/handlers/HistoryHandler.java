package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.FileBackedTaskManager;
import model.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class HistoryHandler implements HttpHandler {

    Gson gson;
    FileBackedTaskManager master;


    public HistoryHandler(FileBackedTaskManager master) {
        this.master = master;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (method.toString().toLowerCase().equals("get")) {
            get(exchange);
        } else {
            String response = "У нас пока нет таких методов";
            sendResponse(exchange, response, 406);
        }
    }

    public void get(HttpExchange exchange) throws IOException {
        List<Task> tasks = new ArrayList<>(master.getHistory());
        String response = tasks.toString();
        sendResponse(exchange, response, 200);
    }


    public void sendResponse(HttpExchange httpExchange, String response, int statusCode) throws IOException {
        httpExchange.getResponseHeaders().set("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream stream = httpExchange.getResponseBody();
        stream.write(response.getBytes());
        stream.close();
    }

    protected void gsonInitializator() {
        this.gson = new Gson();
    }
}
