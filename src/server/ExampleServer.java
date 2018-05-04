package server;

import server.abstractServer.AbstractServer;


public class ExampleServer extends AbstractServer
{
    
    @Override
    protected void handleMessageFromClient(String message)
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
