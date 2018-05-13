package client.controller.supportClasses;


import client.model.Booking;
import client.model.Room;
import client.model.customer.Customer;
import client.model.customer.RealCustomer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import client.controller.FacadeController;
import client.controller.supportClasses.parsing.CustomerJsonAdapter;
import client.controller.supportClasses.parsing.LocalDateJsonAdapter;
import client.controller.supportClasses.parsing.RoomFromIdAdapter;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServerReplyParser
{
    Gson gson;
    FacadeController facadeController;
    
   public ServerReplyParser(FacadeController facadeController)
   {
       gson = new GsonBuilder()
           .registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter())
           .registerTypeAdapter(Customer.class, new CustomerJsonAdapter(facadeController))
           .create();
       
       this.facadeController = facadeController;
   }
    
    
    public void handleMessage(String message)
    {
        String[] splittedMessage = message.split(":",2);
        
        switch(splittedMessage[0])
        {
            case "ok":
    
                // TODO, handle OK
                break;
            case "bookings":
               
                Type listTypeBooking = new TypeToken<List<Booking>>() {}.getType();
                
               Gson roomFromIDGSON = new GsonBuilder().registerTypeAdapter(Room.class, new RoomFromIdAdapter(facadeController))
                       .registerTypeAdapter(Customer.class, new CustomerJsonAdapter(facadeController))
                       .registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter())
                       .create();
                ArrayList<Booking> bookingArray = roomFromIDGSON.fromJson(splittedMessage[1], listTypeBooking);
                facadeController.clearBookings();
                for(Booking booking : bookingArray)
                {
                    facadeController.addBooking(booking);
                }
    
                break;
    
            case "rooms":
                Type listTypeRoom = new TypeToken<List<Room>>()
                {
                }.getType();
                ArrayList<Room> roomArray = gson.fromJson(splittedMessage[1], listTypeRoom);
                for(Room room : roomArray)
                {
                    facadeController.addRoom(room);
                }
                break;
                
            case "customer":
                
                RealCustomer fullDetailCustomer = gson.fromJson(splittedMessage[1], RealCustomer.class);
                facadeController.setCustomerToProxy(fullDetailCustomer);
                
                break;
                
    
            case "error":
                facadeController.showError("An error occured", splittedMessage[1]);
                break;
    
            case "ERROR no such bookings available!":
                throw new IllegalArgumentException("There was no booking matching the search");
                
            case "ERROR bad parameters":
                throw new IllegalArgumentException("At least one search criteria has to be used");
                
            case "available rooms":
                
                facadeController.clearAvailableRooms();
                
                String roomsString = splittedMessage[1].substring(1,splittedMessage[1].length()-1);
                String[] requestedRooms = roomsString.split(",");
                
                if(requestedRooms[0].isEmpty())
                {
                    throw new IllegalArgumentException("There was no available rooms");
                }
                for(String id : requestedRooms)
                {
                    facadeController.addAvailableRoom(facadeController.getRoomByID(Integer.parseInt(id)));
                }
                break;
            
            case "new booking":
                
                int newID = Integer.parseInt(splittedMessage[1]);
                facadeController.setInProgressBookingID(newID);
                break;
                
            case "booking price":
                double bookedPrice = Double.parseDouble(splittedMessage[1]);
                facadeController.setInProgressBookingPrice(bookedPrice);
                break;
        }
    }
}
