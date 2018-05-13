package client.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 *  This class is responsible for keeping access to the model and is a point of communication between view and controllers.
 */
public class ModelAccess
{
    private ArrayList<Booking> bookings;
    private HashMap<Integer, Room> rooms;
    
    
    public ModelAccess()
    {
        bookings = new ArrayList<>();
        rooms = new HashMap<>();
    }
    
    public void addRoom(Room room)
    {
        rooms.put(room.getId(),room);
    }
    
    public void addBooking(Booking booking)
    {
        bookings.add(booking);
    }
    
    public Iterator<Booking> getAllBookings()
    {
        return bookings.iterator();
    }
    
    public void clearBookings()
    {
        bookings.clear();
    }
    
    /**
     * A method to get the room by it's id.
     * @param id id as integer
     * @return an object of Room class
     */
    public Room getRoomByID(int id)
    {
        return rooms.get(id);
    }
}
