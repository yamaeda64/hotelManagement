package client.controller.supportClasses.parsing;

import client.model.Room;
import com.google.gson.*;
import client.controller.FacadeController;

import java.lang.reflect.Type;

/**
 * An adapter class for deserialize a JSON to a Room by just the room ID.
 */
public class RoomFromIdAdapter implements JsonDeserializer<Room>
{
    private FacadeController facadeController;
   
    public RoomFromIdAdapter(FacadeController facadeController)
    {
        this.facadeController = facadeController;
    }
    
    public Room deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException
    {
        return facadeController.getRoomByID(json.getAsInt());
    }
}
