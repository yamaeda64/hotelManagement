package client.model.customer;

/**
 * A simple model class for holding Address information
 */
public class Address
{
    private String streetName;
    private int postalCode;
    private String city;
    private String country;

    
    // Getters & Setters
    
    
    public String getStreetName()
    {
        return streetName;
    }
    
    public void setStreetName(String streetName)
    {
        this.streetName = streetName;
    }
    
    public int getPostalCode()
    {
        return postalCode;
    }
    
    public void setPostalCode(int postalCode)
    {
        this.postalCode = postalCode;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    public String getCountry()
    {
        return country;
    }
    
    public void setCountry(String country)
    {
        this.country = country;
    }
}


