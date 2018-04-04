package server;

import server.abstractServer.AbstractServer;

/**
 * Created by joakimbergqvist on 2018-03-28.
 */
public class ExampleServer extends AbstractServer
{
    
    @Override
    protected synchronized void handleMessageFromClient(String message)
    {

        if(message.equals("Hello"))
        {
            sendToClient("World");
        }
        else
        {
            System.out.println("MESSAGE WAS WRONG!!!!    " + message);
        }
    }
    
    @Override
    public void exceptionOccured(Exception e)
    {
       System.out.println("Exception occured: " +  e.getClass().getName() + e.getMessage());
       
    }
}
