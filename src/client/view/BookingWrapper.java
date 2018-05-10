package client.view;

import client.model.Booking;
import client.model.Room;

import java.util.Iterator;

/**
 * A wrapper class to show a Booking in the TableView of mainWindow
 */
public class BookingWrapper
{
    private Booking booking;
    
    public BookingWrapper(Booking booking)
    {
        this.booking = booking;
    }
    
    public String getFirstName()
    {
        return booking.getCustomer().getFirstName();
    }
    
    public String getLastName()
    {
        return booking.getCustomer().getLastName();
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
    
    public Booking getBooking()
    {
        return booking;
    }
}
