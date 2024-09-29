import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EpicHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson = new Gson();

    public EpicHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (exchange.getRequestMethod()) {
                case "GET":
                    handleGetEpic(exchange);
                    break;
                case "POST":
                    handlePostEpic(exchange);
                    break;
                case "DELETE":
                    handleDeleteEpic(exchange);
                    break;
                default:
                    sendNotFound(exchange);
            }
        } catch (TaskNotFoundException e) {
            sendNotFound(exchange);
        } catch (InvalidTaskException e) {
            sendHasInteractions(exchange);
        } catch (Exception e) {
            exchange.sendResponseHeaders(500, -1);
        }
    }

    private void handleGetEpic(HttpExchange exchange) throws IOException {
        List<Epic> epics = taskManager.getAllEpics();
        String json = gson.toJson(epics);
        sendText(exchange, json);
    }

    private void handlePostEpic(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        Epic epic = gson.fromJson(json, Epic.class);
        taskManager.addEpic(epic);
        sendText(exchange, gson.toJson(epic));
    }

    private void handleDeleteEpic(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.startsWith("id=")) {
            int id = Integer.parseInt(query.split("=")[1]);
            taskManager.deleteEpic(id);
            exchange.sendResponseHeaders(204, -1);
            exchange.close();
        } else {
            sendNotFound(exchange);
        }
    }
}
