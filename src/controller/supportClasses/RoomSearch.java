package controller.supportClasses;

import client.model.Hotel;
import client.model.Room;

import java.time.LocalDate;

public class RoomSearch
{
    
    private LocalDate startDate;
    private LocalDate endDate;
        
    private int numberOfBeds;
    private Hotel hotel;
    private Room.BedType bedType;
    
    private boolean smokingAllowed;
    private boolean adjacentRoomAvailable;
    
    
    
    public RoomSearch()
    {
        
    }
    
    
    // Getters & Setters
    
    public LocalDate getStartDate()
    {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate)
    {
        if(startDate == null)
        {
            throw new IllegalArgumentException("There was no start date");
        }
        if(endDate != null && startDate.compareTo(endDate)>= 0)
        {
            throw new IllegalArgumentException("Check-out cannot be before Check-in");
        }
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate()
    {
        if(startDate == null)
        {
            throw new IllegalArgumentException("There was no start date");
        }
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate)
    {
        if(endDate == null)
        {
            throw new IllegalArgumentException("There was no end date");
        }
        if(startDate != null && startDate.compareTo(endDate)>= 0)
        {
            throw new IllegalArgumentException("Check-out cannot be before Check-in");
        }
        this.endDate = endDate;
    }
    
    public int getNumberOfBeds()
    {
        return numberOfBeds;
    }
    
    public void setNumberOfBeds(int numberOfBeds)
    {
        this.numberOfBeds = numberOfBeds;
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
    
    public Room.BedType getBedType()
    {
        return bedType;
    }
    
    public void setBedType(Room.BedType bedType)
    {
        this.bedType = bedType;
    }
    
    public boolean isSmokingAllowed()
    {
        return smokingAllowed;
    }
    
    public void setSmokingAllowed(boolean smokingAllowed)
    {
        this.smokingAllowed = smokingAllowed;
    }
    
    public boolean isAdjecentRoomAvailable()
    {
        return adjacentRoomAvailable;
    }
    
    public void setAdjecentRoomAvailable(boolean adjecentRoomAvailable)
    {
        this.adjacentRoomAvailable = adjecentRoomAvailable;
    }
}
