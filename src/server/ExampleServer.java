package server;

import server.abstractServer.AbstractServer;


public class ExampleServer extends AbstractServer
{
    
    @Override
    protected void handleMessageFromClient(String message)
    {

        if(message.equals("HåÅäÄöÖöHö"))
        {
            sendToClient("^%€#^åÅäÄöÖ@£$∞§|[]");
        }
        else
        {
            System.out.println("message from client: " + message);
            sendToClient("a little reply");
        }
    }
    
    @Override
    public void exceptionOccured(Exception e)
    {
      // System.out.println("Exception occured: " +  e.getClass().getName() + e.getMessage());
    }
}
