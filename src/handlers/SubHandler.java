package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class SubHandler implements HttpHandler {

    Gson gson;

    public SubHandler(Gson gson){
        this.gson=gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
