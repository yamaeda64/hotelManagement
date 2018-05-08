package controller.supportClasses;

import client.model.Booking;
import client.model.Hotel;
import client.model.customer.Customer;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ServerMessage
{
    private Gson gsonParser;
    
    public ServerMessage()
    {
        gsonParser = new Gson();
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
        String message = "create booking:";
        String json = gsonParser.toJson(booking);
        return message+json;
    }
    
    public String setStatus(Booking booking, Booking.BookingStatus bookingStatus)
    {
        String message = "set status:";
        String bookingID = ""+booking.getBookingID();
        String status = bookingStatus.toString();
        return message+bookingID+ "," +status;
    }
    
    public String setExpence(Booking booking, double paidAmount, double roomTotalAmount)
    {
        String message = "set expense:";
        String bookingID = ""+booking.getBookingID();
        return message+bookingID+ "," + paidAmount + "," + roomTotalAmount;
    }
    
    public String getCustomerDetails(Customer customer)
    {
        String message ="full customer:";
        String customerID = customer.getID();
        return  message+customerID;
    }
    
}

