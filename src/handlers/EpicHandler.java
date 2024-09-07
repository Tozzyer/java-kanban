package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class EpicHandler implements HttpHandler {

    Gson gson;

    public EpicHandler(Gson gson){
        this.gson=gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
