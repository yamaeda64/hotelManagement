package client.model;


import client.model.customer.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;


public class Booking
{
    private ArrayList<Room> bookedRooms;
    
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private double givenPrice;
    private Customer customer;
    private BookingStatus status;
    private double amountPaid;
    
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
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
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
    
    public double getGivenPrice()
    {
        return givenPrice;
    }
    
    public void setGivenPrice(double price)
    {
        this.givenPrice = price;
    }
    
    public Customer getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    public double getAmountPaid() {
    	return amountPaid;
    }
    public void setAmountPaid(double amount) {
    	amountPaid = amount;
    }
    
}
