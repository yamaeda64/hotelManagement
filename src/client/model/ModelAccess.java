package client.model;


import client.model.customer.Customer;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *  This class is responsible for keeping access to the model and is a point of communication between view and controllers.
 */
public class ModelAccess
{
    private ArrayList<Booking> bookings;
    private ArrayList<Room> rooms;
    
    // TODO, Maybe customer only needs to be saved in bookings in client??
    private ArrayList<Customer> custormer;
    
    
    public void addRoom(Room room)
    {
        rooms.add(room);
    }
    
    public void addBooking(Booking booking)
    {
        bookings.add(booking);
    }
    
    
    public Iterator<Room> getAllRooms()
    {
        return rooms.iterator();
    }
    
    public Iterator<Booking> getAllBookings()
    {
        return bookings.iterator();
    }
}
