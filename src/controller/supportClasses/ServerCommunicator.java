package controller.supportClasses;


import client.abstractClient.AbstractClient;

import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public class ServerCommunicator extends AbstractClient
{
    ServerReplyParser serverReplyParser;
    
    public ServerCommunicator()
    {
        InetSocketAddress socketAddress = new InetSocketAddress("sixey.es",6464);
        setLogLevel(Level.OFF);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.OFF);
        setLogHandler(handler);
        openConnection(socketAddress);
        serverReplyParser = new ServerReplyParser();

    }
    
    
    @Override
    public boolean sendToServer(String message)
    {
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
