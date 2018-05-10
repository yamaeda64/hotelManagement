package client.model;


import client.model.customer.Customer;
import controller.supportClasses.RoomSearch;
import controller.supportClasses.ServerMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 *  This class is responsible for keeping access to the model and is a point of communication between view and controllers.
 */
public class ModelAccess
{
    private ServerMessage serverMessage;
    private ArrayList<Booking> bookings;
    private HashMap<Integer, Room> rooms;
    
    // TODO, Maybe customer only needs to be saved in bookings in client??
    private ArrayList<Customer> customer;
    
    
    public ModelAccess()
    {
        bookings = new ArrayList<>();
        rooms = new HashMap<>();
        //addDummyRoomData();
       // addDummyBookingData();
       
       /* Gson gson = new Gson();
        String jsonString = gson.toJson(bookings);
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
        rooms.put(room.getId(),room);
    }
    
    public void addBooking(Booking booking)
    {
        bookings.add(booking);
    }
    
    
  /*  public Iterator<Room> getAllRooms()
    {
        return rooms.iterator();
    } */  // todo, don't think this is needed
    
    public Iterator<Booking> getAllBookings()
    {
        return bookings.iterator();
    }
    
    
    // TODO, adding dummy data is temp
    
    /*private void addDummyRoomData()
    {
        System.out.println("Add dummy Data was called");
        rooms = new ArrayList<>();
        Room room = new Room();
        room.setRoomNumber(201);
        room.setBedType(Room.BedType.TWIN);
        room.setHotel(Hotel.VAXJO);
        room.setNoSmoking(true);
        room.setView("Castle");
        rooms.add(room);
        Room room2 = new Room();
        room2.setRoomNumber(202);
        room2.setBedType(Room.BedType.SINGLE);
        room2.setHotel(Hotel.VAXJO);
        room2.setNoSmoking(true);
        room2.setView("Castle");
        rooms.add(room2);
    
        Room room3 = new Room();
        room3.setRoomNumber(404);
        room3.setBedType(Room.BedType.KING);
        room3.setHotel(Hotel.VAXJO);
        room3.setNoSmoking(true);
        room3.setView("Lake");
       
        rooms.add(room3);
        
    }
    // TODO, only temp adding dummy data
    private void addDummyBookingData()
    {
       
        bookings = new ArrayList<>();
        
        Booking booking = new Booking();
        Customer customer = new ProxyCustomer("1","John","Doe");
        booking.setAmountPayed(145);
        booking.addRoom(rooms.get(0));
        booking.addRoom(rooms.get(1));
       // booking.setCustomer(customer);
        booking.setStartDate(LocalDate.of(2018,05,04));
        booking.setEndDate(LocalDate.of(2018,05,06));
        booking.setPrice(2000);
        booking.setStatus(Booking.BookingStatus.BOOKED);
        bookings.add(booking);
        
        Booking booking2 = new Booking();
        Customer customer2 = new ProxyCustomer("2","Ray","Kurzweil");
        booking2.addRoom(rooms.get(2));
        booking2.setAmountPayed(500);
       // booking2.setCustomer(customer2);
        booking2.setStartDate(LocalDate.of(2018,04,22));
        booking2.setEndDate(LocalDate.of(2018,04,25));
        booking2.setPrice(6000);
        booking2.setStatus(Booking.BookingStatus.CHECKED_IN);
        bookings.add(booking2);
    }
    */
   
    
    public void updateBookings(RoomSearch currentSearch)
    {
       String messageToServer =  serverMessage.getRoomsFromSearch(currentSearch);
       System.out.println(messageToServer); // TODO, add send to server here
    }
    
    public void clearBookings()
    {
        bookings.clear();
    }
    
    public Room getRoomByID(int id)
    {
        return rooms.get(id);
    }
}
