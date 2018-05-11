package client.model.customer;

/**
 * A customer with will hold all details needed for customer details.
 */
public class RealCustomer implements Customer
{
    private String id;
    
    private String firstName;
    private String lastName;
    private Address address;
    private String telephoneNumber;
    private String personalNumber;
    private String passportNumber;
    private CreditCard creditCard;
    private PowerLevel powerLevel;
    
    public RealCustomer()
    {
        
    }
    
    public enum PowerLevel
    {
        DIAMOND,
        PLATINUM,
        GOLD,
        SILVER,
        BRONZE,
        NONE
    }
    
    /* Getters & Setters */
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
    public void setAddress(Address address)
    {
        this.address = address;
    }
    
    public void setTelephoneNumber(String telephoneNumber)
    {
        this.telephoneNumber = telephoneNumber;
    }
    
    public void setPersonalNumber(String personalNumber)
    {
        this.personalNumber = personalNumber;
    }
    
    public void setPassportNumber(String passportNumber)
    {
        this.passportNumber = passportNumber;
    }
    
    public void setCreditCard(CreditCard creditCard)
    {
        this.creditCard = creditCard;
    }
    
    public void setPowerLevel(PowerLevel powerLevel)
    {
        this.powerLevel = powerLevel;
    }
    
    @Override
    public String getID()
    {
        return id;
    }
    
    @Override
    public String getFirstName()
    {
        return firstName;
    }
    
    @Override
    public String getLastName()
    {
        return lastName;
    }
    
    @Override
    public Address getAddress()
    {
        return address;
    }
    
    @Override
    public String getTelephoneNumber()
    {
        return telephoneNumber;
    }
    
    @Override
    public String getPersonalNumber()
    {
        return personalNumber;
    }
    
    @Override
    public String getPassportNumber()
    {
        return passportNumber;
    }
    
    @Override
    public CreditCard getCreditCard()
    {
        return creditCard;
    }
    
    @Override
    public void setID(String id)
    {
        this.id = id;
    }
    
    @Override
    public PowerLevel getPowerLevel()
    {
        return powerLevel;
    }
    
    
}
