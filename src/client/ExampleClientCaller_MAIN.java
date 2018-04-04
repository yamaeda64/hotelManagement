package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

/**
 * Created by joakimbergqvist on 2018-03-29.
 */
public class ExampleClientCaller_MAIN
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        InetSocketAddress socketAddress = new InetSocketAddress(6464);
    
        ExampleClient client = new ExampleClient();
        client.setLogLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        client.setLogHandler(handler);
        client.openConnection(socketAddress);
    
        if(client.isRunning())
    
        {
            client.sendToServer("Hello");
        
            client.receiveMessageFromServer();
        }
        
       /* if(client.isRunning())
        {
            client.sendToServer("TjoFlöjt");
            client.receiveMessageFromServer();
        }
       */
        if(client.isRunning())

        {
            client.sendToServer("Hello");
    
            client.receiveMessageFromServer();
            
        }
        
        if(client.isRunning())
        {
            System.out.println("called CloseConnection");
            client.closeConnection();
        }
    }
    
}
            

