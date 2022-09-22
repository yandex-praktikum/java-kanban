package manager.api.http;

import manager.managers.ManagerSaveException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
/** Клиент для взаимодействия с сервером, хранящим данные менеджера. Регистрирует пользователя на сервере, выгружает
 *  данные для создания менеджера и сохраняет изменения.**/

public class KVTaskClient {
    private final String url;
    private final String apiToken;

    public KVTaskClient(int port) {
        url = "http://localhost:" + port + "/";
        apiToken = register(url);
    }

    private String register(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "register"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() != 200) {
                throw new ManagerSaveException("Не удалось :(" + response.statusCode());
            }
            return response.body();
        } catch (ManagerSaveException | InterruptedException | IOException e) {
            throw new RuntimeException("Не удалось и непонятно почему");
        }
    }

    public String load(String key) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "load/" + key + "?API_TOKEN=" + apiToken))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, handler);

            if (response.statusCode() != 200) {
                throw new ManagerSaveException("Не удалось :(" + response.statusCode());
            }
            return response.body();
        } catch (ManagerSaveException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void put(String key, String jsonTasks) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "save/" + key + "?API_TOKEN=" + apiToken))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonTasks))
                    .build();
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() != 200) {
                throw new ManagerSaveException("Не удалось :(" + response.statusCode());
            }
        } catch (ManagerSaveException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
