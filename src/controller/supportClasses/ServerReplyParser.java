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
                System.out.println("bookings message: " + splittedMessage[1]);
    
                //  String abc = "[{\"bookedRooms\":[{\"roomID\":0,\"hotel\":\"VAXJO\",\"roomNumber\":201,\"view\":\"Castle\",\"noSmoking\":true,\"bedType\":\"TWIN\"},{\"roomID\":0,\"hotel\":\"VAXJO\",\"roomNumber\":202,\"view\":\"Castle\",\"noSmoking\":true,\"bedType\":\"SINGLE\"}],\"bookingID\":0,\"startDate\":{\"year\":2018,\"month\":5,\"day\":4},\"endDate\":{\"year\":2018,\"month\":5,\"day\":6},\"price\":2000.0,\"customer\":{\"customerID\":\"1\",\"firstName\":\"John\",\"lastName\":\"Doe\"},\"bookingStatus\":\"BOOKED\",\"amountPayed\":145.0},{\"bookedRooms\":[{\"roomID\":0,\"hotel\":\"VAXJO\",\"roomNumber\":404,\"view\":\"Lake\",\"noSmoking\":true,\"bedType\":\"KINGSIZE\"}],\"bookingID\":0,\"startDate\":{\"year\":2018,\"month\":4,\"day\":22},\"endDate\":{\"year\":2018,\"month\":4,\"day\":25},\"price\":6000.0,\"customer\":{\"customerID\":\"2\",\"firstName\":\"Ray\",\"lastName\":\"Kurzweil\"},\"bookingStatus\":\"CHECKED_IN\",\"amountPayed\":500.0}]";
                //  System.out.println("to be parsed" + abc);
                //  ArrayList<Booking> bookingArray = gson.fromJson(splittedMessage[1], new TypeToken<List<Booking>>(){}.getType());
                //Booking[] bookingArray = gson.fromJson(abc, Booking[].class);
    
                Type listTypeBooking = new TypeToken<List<Booking>>()
                {
                }.getType();
                ArrayList<Booking> bookingArray = gson.fromJson(splittedMessage[1], listTypeBooking);
                centralController.clearBookings();
                for(Booking booking : bookingArray)
                {
                    System.out.println(booking.getPrice());
                    System.out.println(booking.getCustomer().getFirstName());
    
                    centralController.addBooking(booking);
                }
    
                break;
    
            case "rooms":
                Type listTypeRoom = new TypeToken<List<Room>>()
                {
                }.getType();
                System.out.println("rooms received: " + splittedMessage[1]);
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
    
            case "available rooms:":
                centralController.clearAvailableRooms();
                String[] requestedRooms = splittedMessage[1].split(",");
                for(String id : requestedRooms)
                {
                    centralController.addAvailableRoom(centralController.getRoomByID(Integer.parseInt(id)));
                }
                break;
        }
    }
}
