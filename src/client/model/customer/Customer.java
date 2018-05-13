package client.model.customer;

/**
 * An interface for a customer with getters and setters needed to fullfill the customer interface
 */
public interface Customer
{
    public String getID();
    public String getFirstName();
    public String getLastName();
    public Address getAddress();
    public String getTelephoneNumber();
    public String getPersonalNumber();
    public String getPassportNumber();
    public CreditCard getCreditCard();
    public void setID(String id);
    public void setFirstName(String firstName);
    public void setLastName(String lastName);
    
    /**
     * Clients can have different powerlevels, high level results in better prices and special offers.
     * @return The enum PowerLevel
     */
    public RealCustomer.PowerLevel getPowerLevel();

    
}
