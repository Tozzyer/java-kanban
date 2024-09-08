package AdaptersForJson;

import com.google.gson.*;
import model.SubTask;
import model.Status;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SubTaskAdapter implements JsonSerializer<SubTask>, JsonDeserializer<SubTask> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public JsonElement serialize(SubTask task, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", task.getId());
        jsonObject.addProperty("taskName", task.getTaskName());
        jsonObject.addProperty("status", task.getStatus().name());
        jsonObject.addProperty("content", task.getContent());
        jsonObject.addProperty("startTime", task.getStartTime().format(formatter));
        jsonObject.addProperty("duration", task.getDuration().toMinutes());
        jsonObject.addProperty("masterId", task.getMasterId());

        return jsonObject;
    }

    @Override
    public SubTask deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        Integer id = jsonObject.get("id").getAsInt();
        String taskName = jsonObject.get("taskName").getAsString();
        Status status = Status.valueOf(jsonObject.get("status").getAsString());
        String content = jsonObject.get("content").getAsString();
        Integer masterId = jsonObject.get("masterId").getAsInt();

        LocalDateTime startTime = LocalDateTime.parse(jsonObject.get("startTime").getAsString(), formatter);

        Duration duration = Duration.ofMinutes(jsonObject.get("duration").getAsLong());

        return new SubTask(taskName, content, status, masterId, id, startTime, duration);
    }
}
