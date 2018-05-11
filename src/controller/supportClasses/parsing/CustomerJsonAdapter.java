package controller.supportClasses.parsing;

import client.model.customer.Customer;
import controller.FacadeController;
import controller.supportClasses.ProxyCustomer;
import com.google.gson.*;

import java.lang.reflect.Type;

public class CustomerJsonAdapter implements JsonDeserializer<Customer>, JsonSerializer<Customer>
{
    private FacadeController facadeController;
    
    
    public CustomerJsonAdapter(FacadeController facadeController)
    {
        this.facadeController = facadeController;
    }
    
    @Override
    public Customer deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException
    {
        ProxyCustomer customer = new ProxyCustomer(facadeController);
        customer.setFirstName(((JsonPrimitive) json.getAsJsonObject().get("firstName")).getAsString());
        customer.setLastName(((JsonPrimitive) json.getAsJsonObject().get("lastName")).getAsString());
        customer.setID(((JsonPrimitive) json.getAsJsonObject().get("id")).getAsString());
        
        return customer;
    }
    
    @Override
    public JsonElement serialize(Customer customer, Type type, JsonSerializationContext jsonSerializationContext)
    {
        JsonElement elem = jsonSerializationContext.serialize(customer);
        
        return elem;
    }
}