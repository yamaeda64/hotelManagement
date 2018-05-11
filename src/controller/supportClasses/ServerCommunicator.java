package controller.supportClasses;


import client.abstractClient.AbstractClient;
import controller.FacadeController;

import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public class ServerCommunicator extends AbstractClient
{
    final String hostname = "sixey.es";
    final int port = 6464;
    
    ServerReplyParser serverReplyParser;
    
    public ServerCommunicator()
    {
        setupServer();
    }
    
    public ServerCommunicator(FacadeController facadeController)
    {
        serverReplyParser = new ServerReplyParser(facadeController);
        setupServer();
    }
    
    public void setupServer()
    {
        InetSocketAddress socketAddress = new InetSocketAddress(hostname, port);
        setLogLevel(Level.OFF);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.OFF);
        setLogHandler(handler);
        openConnection(socketAddress);
    }
    
    @Override
    public boolean sendToServer(String message)
    {
        System.out.println("ToServer: " + message); // TODO, debug
        super.sendToServer(message);
        receiveMessageFromServer();
        return true;
    }
    
    @Override
    public void handleMessageFromServer(String message)
    {
        serverReplyParser.handleMessage(message);
    }
}
