package controller.supportClasses;

import client.model.customer.Address;
import client.model.customer.CreditCard;
import client.model.customer.Customer;
import client.model.customer.RealCustomer;
import controller.CentralController;

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
    
    private CentralController centralController;
    
    public ProxyCustomer(CentralController centralController)
    {
        this.centralController = centralController;
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
            askForFullCustomer();
        }
        return realCustomer.getAddress();
    }
    
    @Override
    public String getTelephoneNumber()
    {
        if(realCustomer == null)
        {
            askForFullCustomer();
        }
        return realCustomer.getTelephoneNumber();
    }
    
    @Override
    public String getPersonalNumber()
    {
        if(realCustomer == null)
        {
            askForFullCustomer();
        }
        return realCustomer.getPersonalNumber();
    }
    
    @Override
    public String getPassportNumber()
    {
        if(realCustomer == null)
        {
            askForFullCustomer();
        }
        return realCustomer.getPassportNumber();
    }
    
    @Override
    public CreditCard getCreditCard()
    {
        if(realCustomer == null)
        {
            askForFullCustomer();
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
            askForFullCustomer();
        }
        return realCustomer.getPowerLevel();
    }
    
    private void askForFullCustomer()
    {
        centralController.getRealCustomer(this);
    }
    
    public void addRealCustomer(RealCustomer customer)
    {
        this.realCustomer = customer;
    }
}
