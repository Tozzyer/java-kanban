package adaptersForJson;

import com.google.gson.*;
import model.Epic;
import model.Status;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EpicAdapter implements JsonSerializer<Epic>, JsonDeserializer<Epic> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public JsonElement serialize(Epic task, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", task.getId());
        jsonObject.addProperty("taskName", task.getTaskName());
        jsonObject.addProperty("status", task.getStatus().name());
        jsonObject.addProperty("content", task.getContent());
        jsonObject.addProperty("startTime", task.getStartTime().format(formatter));
        jsonObject.addProperty("duration", task.getDuration().toMinutes());
        jsonObject.addProperty("epicSubs", task.getEpicSubs().toString());

        return jsonObject;
    }

    @Override
    public Epic deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        Integer id = jsonObject.get("id").getAsInt();
        String taskName = jsonObject.get("taskName").getAsString();
        Status status = Status.valueOf(jsonObject.get("status").getAsString());
        String content = jsonObject.get("content").getAsString();

        LocalDateTime startTime = LocalDateTime.parse(jsonObject.get("startTime").getAsString(), formatter);

        Duration duration = Duration.ofMinutes(jsonObject.get("duration").getAsLong());

        return new Epic(taskName, content, status, id, startTime, duration);
    }
}
