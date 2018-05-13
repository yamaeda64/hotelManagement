package client.controller.supportClasses;

import client.model.Hotel;

public class BookingSearch
{
    
    private String firstName;
    private String lastName;
    private String passportNumber;
    private String telephoneNumber;
    private String bookingNumber;
    private Hotel hotel;
    
    public BookingSearch()
    {
        
    }
    
    
    // Getters & Setters
    
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String FN)
    {
        this.firstName = FN;
    }
    
    
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String LN)
    {
        this.lastName = LN;
    }
    
    public String getBookingNumber()
    {
        return bookingNumber;
    }
    
    public void setBookingNumber(String bookingNumber)
    {
        this.bookingNumber = bookingNumber;
    }
    public String getPassportNumber()
    {
        return passportNumber;
    }
    public void setPassportNumber(String PN)
    {
        this.passportNumber = PN;
    }
    public String getTelephoneNumber()
    {
        return telephoneNumber;
    }
    public void setTelephoneNumber(String TN)
    {
        this.telephoneNumber = TN;
    }
    
    public Hotel getHotel()
    {
        return hotel;
    }
    
    public void setHotel(Hotel hotel)
    {
        if(hotel == null)
        {
            throw new IllegalArgumentException("There was no hotel selected");
        }
        this.hotel = hotel;
    }
}
