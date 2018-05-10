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
    private String lastName;
    
    
    public ProxyCustomer()
    {
        
    }
    
    
    /**
     * The constructor of the proxy takes the inputs of ID, first name, and last name
     * @param ID ID which is used as database referece
     * @param firstName
     * @param lastName
     */
    public ProxyCustomer(String ID, String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
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
    public String getLastName()
    {
        return lastName;
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
    public void setID(String id)
    {
        this.customerID = id;
    }
    
    @Override
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    @Override
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
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
        realCustomer.setLastName(lastName);
       
       /* ServerMessage message = new ServerMessage();
        ServerCommunicator communicator = new ServerCommunicator();
        communicator.sendToServer(message.getCustomerDetails(this));
        */
    }
}
