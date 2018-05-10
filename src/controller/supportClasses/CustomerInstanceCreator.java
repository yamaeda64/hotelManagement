package controller.supportClasses;

import client.model.customer.Customer;
import client.model.customer.ProxyCustomer;
import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

public class CustomerInstanceCreator implements InstanceCreator<Customer> {
    private Customer customer;
    
    public CustomerInstanceCreator(Customer customer) {
        this.customer = customer;
    }
    
    @Override
    public ProxyCustomer createInstance(Type type) {
        // create new object with our additional property
        ProxyCustomer proxyCustomer = new ProxyCustomer();
        
        // return it to gson for further usage
        return proxyCustomer;
    }
}
