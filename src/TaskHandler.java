import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson = HttpTaskServer.getGson();

    public TaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (exchange.getRequestMethod()) {
                case "GET":
                    handleGetTask(exchange);
                    break;
                case "POST":
                    handlePostTask(exchange);
                    break;
                case "DELETE":
                    handleDeleteTask(exchange);
                    break;
                default:
                    sendNotFound(exchange);
            }
        } catch (TaskNotFoundException e) {
            sendNotFound(exchange);
        } catch (InvalidTaskException e) {
            sendHasInteractions(exchange);
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1);
        }
    }

    private void handleGetTask(HttpExchange exchange) throws IOException {
        List<Task> tasks = taskManager.getAllTasks();
        String json = gson.toJson(tasks);
        sendText(exchange, json);
    }

    private void handlePostTask(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        Task task = HttpTaskServer.getGson().fromJson(json, Task.class);
        taskManager.createTask(task);
        sendText(exchange, HttpTaskServer.getGson().toJson(task));
    }

    private void handleDeleteTask(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.startsWith("id=")) {
            int id = Integer.parseInt(query.split("=")[1]);
            taskManager.deleteTask(id);
            exchange.sendResponseHeaders(204, -1);
            exchange.close();
        } else {
            sendNotFound(exchange);
        }
    }
}
