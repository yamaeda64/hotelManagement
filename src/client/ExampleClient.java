package client;

import client.abstractClient.AbstractClient;

/**
 * Created by joakimbergqvist on 2018-03-29.
 */
public class ExampleClient extends AbstractClient
{
    private boolean isRunning = true;
   
   
    @Override
    public void handleMessageFromServer(String message)
    {
        System.out.println("Message was: " + message);
    }
    
    @Override
    public void connectionException(Exception e)
    {
        System.out.println("connection exception caught in example client");
        isRunning = false;
    }
    
    public boolean isRunning()
    {
        return isRunning;
    }
        
}
