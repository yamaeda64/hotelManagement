package client.model.customer;


/**
 * Proxy customer is a lightweight Customer which loads more customer
 * data if no realCustomer (full customer details) is available.
 * Then simply asks the real customer for those details that is not part
 * of the proxy customer.
 */
public class ProxyCustomer implements Customer
{
    private String customerID;
    private RealCustomer realCustomer;
    private String firstName;
    private String familyName;
    
    
    /**
     * The constructor of the proxy takes the inputs of ID, first name, and family name
     * @param ID ID which is used as database referece
     * @param firstName
     * @param familyName
     */
    public ProxyCustomer(String ID, String firstName, String familyName)
    {
        this.firstName = firstName;
        this.familyName = familyName;
        this.customerID = ID;
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
        if(realCustomer == null)
        {
            loadFullCustomerDetails();
        }
        return realCustomer.getAddress();
    }
    
    @Override
    public String getTelephoneNumber()
    {
        if(realCustomer == null)
        {
            loadFullCustomerDetails();
        }
        return realCustomer.getTelephoneNumber();
    }
    
    @Override
    public String getPersonalNumber()
    {
        if(realCustomer == null)
        {
            loadFullCustomerDetails();
        }
        return realCustomer.getPersonalNumber();
    }
    
    @Override
    public String getPassportNumber()
    {
        if(realCustomer == null)
        {
            loadFullCustomerDetails();
        }
        return realCustomer.getPassportNumber();
    }
    
    @Override
    public CreditCard getCreditCard()
    {
        if(realCustomer == null)
        {
            loadFullCustomerDetails();
        }
        return realCustomer.getCreditCard();
    }
    
    @Override
    public RealCustomer.PowerLevel getPowerLevel()
    {
        if(realCustomer == null)
        {
            loadFullCustomerDetails();
        }
        return realCustomer.getPowerLevel();
    }
    
    private void loadFullCustomerDetails()
    {
        
        //TODO Code to load the details to create a full customer
        this.realCustomer = new RealCustomer();
        
        realCustomer.setCustomerID(customerID);
        realCustomer.setFirstName(firstName);
        realCustomer.setFamilyName(familyName);
        
    }
}
