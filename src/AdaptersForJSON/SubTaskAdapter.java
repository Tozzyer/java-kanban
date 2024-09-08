package AdaptersForJSON;

import com.google.gson.*;
import model.Status;
import model.Task;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SubTaskAdapter implements JsonSerializer<Task>, JsonDeserializer<Task> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public JsonElement serialize(Task task, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", task.getId());
        jsonObject.addProperty("taskName", task.getTaskName());
        jsonObject.addProperty("status", task.getStatus().name());
        jsonObject.addProperty("content", task.getContent());

        jsonObject.addProperty("startTime", task.getStartTime().format(formatter));

        jsonObject.addProperty("duration", task.getDuration().getSeconds());

        return jsonObject;
    }

    @Override
    public Task deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        Integer id = jsonObject.get("id").getAsInt();
        String taskName = jsonObject.get("taskName").getAsString();
        Status status = Status.valueOf(jsonObject.get("status").getAsString());
        String content = jsonObject.get("content").getAsString();

        LocalDateTime startTime = LocalDateTime.parse(jsonObject.get("startTime").getAsString(), formatter);

        Duration duration = Duration.ofSeconds(jsonObject.get("duration").getAsLong());

        return new Task(taskName, content, status);
    }
}
