package controller.supportClasses;

import client.model.Booking;
import client.model.Hotel;
import client.model.customer.Customer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.CentralController;
import controller.supportClasses.parsing.CustomerJsonAdapter;
import controller.supportClasses.parsing.LocalDateJsonAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ServerMessage
{
    private Gson gsonParser;
    
    public ServerMessage(CentralController centralController)
    {
        gsonParser = new GsonBuilder()
                .registerTypeAdapter(Customer.class, new CustomerJsonAdapter(centralController))
                .registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter()).create();
    }
    
    public String getAllRooms()
    {
        return "list rooms:";
    }
    
    public String getBookingsOfDate(Hotel hotel, LocalDate date)
    {
        String message = "list bookings:";
        message += hotel.toString() + ",";
        LocalDateTime dateTime = date.atTime(12,00);
        
        message+= (dateTime.toEpochSecond(ZoneOffset.UTC)*1000);
        return message;
    }
    
    public String getRoomsFromSearch(RoomSearch search)
    {
        String message = "search rooms:";
        String json = gsonParser.toJson(search);
        return message+json;
    }
    
    public String getBookingsFromSearch(BookingSearch search)
    {
        String message = "search bookings:";
        String json = gsonParser.toJson(search);
        return message+json;
    }
    
    public String createBooking(Booking booking)
    {
        String message;
        if(booking.getId() == 0)
        {
            message = "create booking:";
        }
        else
        {
            message = "realize booking:";
        }
        String json = gsonParser.toJson(booking);
        return message+json;
    }
    
    public String finalizeBooking(Booking booking)
    {
        String message = "finalize booking:";
        message += booking.getId();
        message += ",";
        message += booking.getPrice();
        return message;
    }
    
    public String setStatus(Booking booking, Booking.BookingStatus bookingStatus)
    {
        String message = "set status:";
        String bookingID = ""+booking.getId();
        String status = bookingStatus.toString();
        return message+bookingID+ "," +status;
    }
    
    public String setExpence(Booking booking, double paidAmount, double roomTotalAmount)
    {
        String message = "set expense:";
        String bookingID = ""+booking.getId();
        return message+bookingID+ "," + paidAmount + "," + roomTotalAmount;
    }
    
    public String getCustomerDetails(Customer customer)
    {
        String message = "full customer:";
        String customerID = customer.getID();
        return  message+customerID;
    }
    
}

