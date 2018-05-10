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
        ProxyCustomer customer = new ProxyCustomer();
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