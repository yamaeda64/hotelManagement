package controller.supportClasses.parsing;

import client.model.customer.Customer;
import client.model.customer.ProxyCustomer;
import com.google.gson.*;

import java.lang.reflect.Type;

public class CustomerJsonAdapter implements JsonDeserializer<Customer>, JsonSerializer<Customer>
{
    @Override
    public Customer deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException
    {
        System.out.println("StartedDeserialize");
        ProxyCustomer customer = new ProxyCustomer();
        customer.setFirstName(((JsonPrimitive) json.getAsJsonObject().get("firstName")).getAsString());
        System.out.println("customer" + customer.getFirstName());
        customer.setLastName(((JsonPrimitive) json.getAsJsonObject().get("lastName")).getAsString());
        System.out.println("lastName = " + customer.getLastName());
        customer.setID(((JsonPrimitive) json.getAsJsonObject().get("id")).getAsString());
        System.out.println("id =" + customer.getID());
        
        
        return customer;
    }
    
    @Override
    public JsonElement serialize(Customer customer, Type type, JsonSerializationContext jsonSerializationContext)
    {
        System.out.println("Started Serialize customer");
    
        JsonElement elem = jsonSerializationContext.serialize(customer);
        
        return elem;
    
    }
}