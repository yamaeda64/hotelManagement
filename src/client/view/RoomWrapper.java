package client.view;

import client.model.Booking;
import client.model.Room;

import java.util.Iterator;

/**
 * A wrapper class to show a Booking in the TableView of mainWindow
 */
public class RoomWrapper
{
    private Booking booking;
    
    public RoomWrapper(Booking booking)
    {
        this.booking = booking;
    }
    
    public String getFirstName()
    {
        // TODO, remove if else
        if(booking.getCustomer() != null)
        {
            return booking.getCustomer().getFirstName();
        }
        else
            return "";
    }
    
    public String getLastName()
    {
        if(booking.getCustomer() != null)
        {
            return booking.getCustomer().getLastName();
        }
        else
        {
            return "";
        }
    }
    public String getRoomNumbers()
    {
    
        String roomNumbers = "";
        Iterator<Room> roomIterator = booking.getAllRooms();
      
        while(roomIterator.hasNext())
        {
            roomNumbers += roomIterator.next().getRoomNumber();
            if(roomIterator.hasNext() == true)
            {
                roomNumbers += ", ";
            }
        }
        return roomNumbers;
    }
    
    public String getBookingStatus()
    {
       return booking.getStatus().toString();
    }
}
