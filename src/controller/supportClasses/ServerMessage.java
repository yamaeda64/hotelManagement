package controller.supportClasses;

import com.google.gson.Gson;

public class ServerMessage
{
    private Gson gsonParser;
    public ServerMessage()
    {
        gsonParser = new Gson();
    }
    public String getRoomsFromSearch(RoomSearch search)
    {
        StringBuilder stringBuilder = new StringBuilder("GetRooms ");
        {
            stringBuilder.append(gsonParser.toJson(search));
        }
        return stringBuilder.toString();
    }
}
