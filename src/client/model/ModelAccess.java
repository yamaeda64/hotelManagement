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
    
    
    public ModelAccess()
    {
        //addDummyData();
        /*
        // TO JSON example
        
        Gson gson = new Gson();
        String jsonString = gson.toJson(rooms);
        System.out.println(jsonString);
        */
        
       
        // from json array example
        /*
        Gson gson = new Gson();
        String jsonString = "[{\"hotel\":\"VAXJO\",\"roomNumber\":201,\"view\":\"Castle\",\"noSmoking\":true,\"bedType\":\"SINGLE\"},{\"roomNumber\":202,\"noSmoking\":false}]";
        rooms = gson.fromJson(jsonString, new TypeToken<List<Room>>(){}.getType());
        System.out.println(rooms.get(0).getRoomNumber());
        System.out.println(rooms.get(1).getRoomNumber());
        */
    }
    
    
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
    
    
    // TODO, adding dummy data is temp
    
    public void addDummyData()
    {
        System.out.println("Add dummy Data was called");
        rooms = new ArrayList<>();
        Room room = new Room();
        room.setRoomNumber(201);
        room.setBedType(Room.BedType.TWIN);
        room.setHotel(Room.Hotel.VAXJO);
        room.setNoSmoking(true);
        room.setView("Castle");
        rooms.add(room);
        Room room2 = new Room();
        room2.setRoomNumber(202);
        room.setBedType(Room.BedType.SINGLE);
        room.setHotel(Room.Hotel.VAXJO);
        room.setNoSmoking(true);
        room.setView("Castle");
        rooms.add(room2);
        
        
    }
}
