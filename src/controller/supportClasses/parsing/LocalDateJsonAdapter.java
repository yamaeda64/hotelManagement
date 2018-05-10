package controller.supportClasses.parsing;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateJsonAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate>
{
    @Override
    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        LocalDate myDate = LocalDate.parse(jsonElement.getAsString());
        return myDate;
    }
    
    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
        LocalDateTime dateTime = date.atTime(12,00);
        String message = "";
        message+= (dateTime.toEpochSecond(ZoneOffset.UTC)*1000);
         
        
        return new JsonPrimitive(message);
    }
}