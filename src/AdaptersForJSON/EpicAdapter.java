package AdaptersForJSON;

import com.google.gson.*;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class EpicAdapter implements JsonSerializer<Epic>, JsonDeserializer<Epic> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public JsonElement serialize(Epic task, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        JsonObject subHashJson = new JsonObject();
        jsonObject.addProperty("id", task.getId());
        jsonObject.addProperty("taskName", task.getTaskName());
        jsonObject.addProperty("status", task.getStatus().name());
        jsonObject.addProperty("content", task.getContent());
        jsonObject.addProperty("startTime", task.getStartTime().format(formatter));
        jsonObject.addProperty("duration", task.getDuration().getSeconds());

        for (Integer key : task.getEpicSubs().keySet()) {
            SubTask subTask = task.getEpicSubs().get(key);
            subHashJson.add(key.toString(), context.serialize(subTask)); // Используем контекст для сериализации SubTask
        }
        jsonObject.add("epicSubs", subHashJson);
        return jsonObject;
    }

    @Override
    public Epic deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        Integer id = jsonObject.get("id").getAsInt();
        String taskName = jsonObject.get("taskName").getAsString();
        Status status = Status.valueOf(jsonObject.get("status").getAsString());
        String content = jsonObject.get("content").getAsString();
        LocalDateTime startTime = jsonObject.has("startTime") && !jsonObject.get("startTime").isJsonNull()
                ? LocalDateTime.parse(jsonObject.get("startTime").getAsString(), formatter)
                : null;
        Duration duration = jsonObject.has("duration") ? Duration.ofSeconds(jsonObject.get("duration").getAsLong()) : Duration.ZERO;

        Epic epic = new Epic(taskName, content, status, id, startTime, duration);

        JsonObject epicSubsJson = jsonObject.getAsJsonObject("epicSubs");
        HashMap<Integer, SubTask> epicSubs = new HashMap<>();
        for (String key : epicSubsJson.keySet()) {
            SubTask subTask = context.deserialize(epicSubsJson.get(key), SubTask.class);
            epicSubs.put(Integer.parseInt(key), subTask);
        }

        for (SubTask subTask : epicSubs.values()) {
            epic.addSubTask(subTask);
        }

        return epic;
    }
}
