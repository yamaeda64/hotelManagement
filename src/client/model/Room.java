package client.model;


public class Room
{
    private Hotel hotel;
    private int roomNumber;
    private String view;
    private QualityLevel qualityLevel;
    private RoomStatus roomStatus;
    private boolean noSmoking;
    private BedType bedType;
    
    
    public enum Hotel
    {
        VAXJO,
        KALMAR
    }
    
    public enum RoomStatus
    {
        AVAILABLE,
        BOOKED,
        CHECKED_IN,
        HELD_FOR_BOOKING
    }
    
    public enum BedType
    {
        KINGSIZE,
        QUEENSIZE,
        TWIN,
        SINGLE
    }
    public enum QualityLevel
    {
        
    }
}
