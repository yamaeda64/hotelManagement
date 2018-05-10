package client.model;


public class Room
{
    private int id;
    private Hotel hotel;
    private int roomNumber;
    private String view;
    private QualityLevel qualityLevel;
    private boolean noSmoking;
    private BedType bedType;
    private Room adjecentRoom;
    private int standardPrice;
    
    
    
    public enum BedType
    {
        SINGLE,
        TWIN,
        KING
    }
    public enum QualityLevel
    {
        
    }
    
    // Getters & Setters
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public Hotel getHotel()
    {
        return hotel;
    }
    
    public void setHotel(Hotel hotel)
    {
        this.hotel = hotel;
    }
    
    public int getRoomNumber()
    {
        return roomNumber;
    }
    
    public void setRoomNumber(int roomNumber)
    {
        this.roomNumber = roomNumber;
    }
    
    public String getView()
    {
        return view;
    }
    
    public void setView(String view)
    {
        this.view = view;
    }
    
    public QualityLevel getQualityLevel()
    {
        return qualityLevel;
    }
    
    public void setQualityLevel(QualityLevel qualityLevel)
    {
        this.qualityLevel = qualityLevel;
    }
    
    public boolean isNoSmoking()
    {
        return noSmoking;
    }
    
    public void setNoSmoking(boolean noSmoking)
    {
        this.noSmoking = noSmoking;
    }
    
    public BedType getBedType()
    {
        return bedType;
    }
    
    public void setBedType(BedType bedType)
    {
        this.bedType = bedType;
    }
    
    public Room getAdjecentRoom()
    {
        return adjecentRoom;
    }
    
    public void setAdjecentRoom(Room adjecentRoom)
    {
        this.adjecentRoom = adjecentRoom;
    }
    public int getStandardPrice() {
    	return standardPrice;
    }
    public void setStandardPrice(int price) {
    	standardPrice = price;
    }
}
