package client;

import client.abstractClient.AbstractClient;


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
