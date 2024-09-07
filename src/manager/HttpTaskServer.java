package manager;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import handlers.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    private HttpServer server;
    Gson gson;


    public HttpTaskServer() throws IOException {
        this.server= HttpServer.create(new InetSocketAddress(8080), 0);
        this.gson = new Gson();
    }

    public void startServer(){
        server.createContext("/tasks", new TaskHandler(gson));
        server.createContext("/epics", new EpicHandler(gson));
        server.createContext("/subtasks", new SubHandler(gson));
        server.createContext("/subtasks", new SubHandler(gson));
        server.createContext("/history", new HistoryHandler(gson));
        server.createContext("/prioritized", new PriorityHandler(gson));
        server.start();
    }
    public void stopServer(){
        server.stop(5);
    }



}
