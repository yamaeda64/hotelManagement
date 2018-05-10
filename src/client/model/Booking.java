package client.model;


import client.model.customer.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;


public class Booking
{
    private ArrayList<Room> bookedRooms;
    
    private long bookingID;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private Customer customer;
    private BookingStatus status;
    private double amountPayed;
    
    public Booking()
    {
        bookedRooms = new ArrayList<>();
    }
    
    public enum BookingStatus
    {
        IN_PROGRESS,
        BOOKED,
        CHECKED_IN,
        CHECKED_OUT,
        CANCELLED
    }
    
    
    public void addRoom(Room room)
    {
        bookedRooms.add(room);
    }
    
    // Getters & Setters
    
    
    public BookingStatus getStatus()
    {
        return status;
    }
    
    public void setStatus(BookingStatus status)
    {
        this.status = status;
    }
    
    public Iterator<Room> getAllRooms()
    {
        return bookedRooms.iterator();
    }
    
    public long getBookingID()
    {
        return bookingID;
    }
    
    public void setBookingID(long bookingID)
    {
        this.bookingID = bookingID;
    }
    
    public LocalDate getStartDate()
    {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate()
    {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }
    
    public double getPrice()
    {
        return price;
    }
    
    public void setPrice(double price)
    {
        this.price = price;
    }
    
    public Customer getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    public double getAmountPayed() {
    	return amountPayed;
    }
    public void setAmountPayed(double amount) {
    	amountPayed = amount;
    }
    
}
