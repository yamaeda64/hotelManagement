package controller.supportClasses.parsing;

import client.model.Room;
import com.google.gson.*;
import controller.CentralController;

import java.lang.reflect.Type;

/**
 * Created by joakimbergqvist on 2018-05-10.
 */
public class RoomFromIdAdapter implements JsonDeserializer<Room>
{
    private CentralController centralController;
   
    public RoomFromIdAdapter(CentralController centralController)
    {
        this.centralController = centralController;
    }
    
    public Room deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException
    {
        return centralController.getRoomByID(json.getAsInt());
    }
}
