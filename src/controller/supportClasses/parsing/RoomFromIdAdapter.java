package controller.supportClasses.parsing;

import client.model.Room;
import com.google.gson.*;
import controller.FacadeController;

import java.lang.reflect.Type;

/**
 * Created by joakimbergqvist on 2018-05-10.
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
