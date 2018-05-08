package client.model.customer;


public interface Customer
{
    public String getID();
    public String getFirstName();
    public String getFamilyName();
    public Address getAddress();
    public String getTelephoneNumber();
    public String getPersonalNumber();
    public String getPassportNumber();
    public CreditCard getCreditCard();
    
    /**
     * Clients can have different powerlevels, high level results in better prices and special offers.
     * @return The enum PowerLevel
     */
    public RealCustomer.PowerLevel getPowerLevel();

    
}
