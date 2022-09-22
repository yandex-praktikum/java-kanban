package manager.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.api.adapter.LocalDateAdapter;

import manager.api.adapter.LocalDateTimeDeserializer;
import manager.api.adapter.LocalDateTimeSerializer;
import manager.api.server.KVServer;
import manager.history.HistoryManager;
import manager.history.InMemoryHistoryManager;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static manager.api.server.KVServer.PORT;

/** Фабрика менеджеров. **/
public class Managers {

    private Managers () {
    }

    public static HttpTaskManager getDefault() {
        return new HttpTaskManager(PORT);
        }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager getDefaultBacked() {
        return new FileBackedTasksManager(new File("history.csv"));
    }

    public static KVServer getDefaultKVServer() throws IOException {
        final KVServer kvServer = new KVServer();
        kvServer.start();
        return kvServer;
    }


    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        return gsonBuilder.create();
    }
}