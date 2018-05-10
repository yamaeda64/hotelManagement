package controller.supportClasses;


import client.model.Booking;
import client.model.Room;
import client.model.customer.Customer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.CentralController;
import controller.supportClasses.parsing.CustomerJsonAdapter;
import controller.supportClasses.parsing.LocalDateJsonAdapter;
import controller.supportClasses.parsing.RoomFromIdAdapter;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServerReplyParser
{
    Gson gson;
    CentralController centralController;
    
   public ServerReplyParser(CentralController centralController)
   {
       gson = new GsonBuilder()
           .registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter())
           .registerTypeAdapter(Customer.class, new CustomerJsonAdapter())
           .create();
       
       this.centralController = centralController;
   }
    
    
    public void handleMessage(String message)
    {
        System.out.println("receivedFromParser: " + message);
        String[] splittedMessage = message.split(":",2);
        
        switch(splittedMessage[0])
        {
            case "ok":
    
                // TODO, handle OK
                break;
            case "bookings":
               
                Type listTypeBooking = new TypeToken<List<Booking>>() {}.getType();
                
               Gson roomFromIDGSON = new GsonBuilder().registerTypeAdapter(Room.class, new RoomFromIdAdapter(centralController))
                       .registerTypeAdapter(Customer.class, new CustomerJsonAdapter())
                       .registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter())
                       .create();
                ArrayList<Booking> bookingArray = roomFromIDGSON.fromJson(splittedMessage[1], listTypeBooking);
                centralController.clearBookings();
                for(Booking booking : bookingArray)
                {
                    centralController.addBooking(booking);
                }
    
                break;
    
            case "rooms":
                Type listTypeRoom = new TypeToken<List<Room>>()
                {
                }.getType();
                ArrayList<Room> roomArray = gson.fromJson(splittedMessage[1], listTypeRoom);
                for(Room room : roomArray)
                {
                    centralController.addRoom(room);
                }
    
            case "customer":
        
                break;
    
            case "error":
                centralController.showError(splittedMessage[1]);
                break;
    
            case "available rooms":
                
                centralController.clearAvailableRooms();
                
                String roomsString = splittedMessage[1].substring(1,splittedMessage[1].length()-1);
                String[] requestedRooms = roomsString.split(",");
                
                for(String id : requestedRooms)
                {
                    centralController.addAvailableRoom(centralController.getRoomByID(Integer.parseInt(id)));
                }
                break;
                
                
            case "new booking":
                
                int newID = Integer.parseInt(splittedMessage[1]);
                centralController.setInProgressBookingID(newID);
        }
    }
}
