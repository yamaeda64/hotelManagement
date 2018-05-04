package client.model.customer;

/**
 * A customer with will hold all details needed for customer details.
 */
public class RealCustomer implements Customer
{
    private String customerID;
    
    private String firstName;
    private String familyName;
    private Address address;
    private String telephoneNumber;
    private String personalNumber;
    private String passportNumber;
    private CreditCard creditCard;
    private PowerLevel powerLevel;
    
    
    
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
    
    public void setCustomerID(String customerID)
    {
        this.customerID = customerID;
    }
    
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    public void setFamilyName(String familyName)
    {
        this.familyName = familyName;
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
        return customerID;
    }
    
    @Override
    public String getFirstName()
    {
        return firstName;
    }
    
    @Override
    public String getFamilyName()
    {
        return familyName;
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
    public PowerLevel getPowerLevel()
    {
        return powerLevel;
    }
    
    
}
