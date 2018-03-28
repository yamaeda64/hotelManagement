package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An abstract class for setting up Client-Server
 * This should be kept abstract for future reuse.
 */

public abstract class AbstractServer
{
    
    private ServerSocket serverSocket = null;
    private ArrayList<Socket> connectedClients = new ArrayList<>();
    private boolean isConnected;
    
    private int portNumber = 4568;
    
    private final int SERVER_TIMEOUT = 5000;
    private Logger logger = Logger.getLogger(AbstractServer.class.getName());
    
    
    /**
     * A function to start server and listen for new clients
     */
    public void startServer() throws IOException
    {
        logger.log(Level.INFO, "Server started at port: "+ portNumber);
        
        serverSocket = new ServerSocket(portNumber);
        isConnected = true;
        
        // A loop that listen for new connection for 5 sec, then cleans connectedClient if any disconnection accured
        while(isConnected())
        {
            serverSocket.setSoTimeout(SERVER_TIMEOUT);
            try
            {
                listenForClient();
            } catch(SocketTimeoutException e)
            {
                // A timeout is normal here and no need for error handeling
            }
            for(Socket s : connectedClients)
            {
                if(s.isClosed())
                {
                    logger.log(Level.FINEST, "Server found closed client and remove client from list");
                    connectedClients.remove(s);
                }
            }
        }
    }
    
    public void stopServer() throws IOException, InterruptedException
    {
        serverSocket.close();
        
        // soft shutdown
        for(Socket s : connectedClients)
        {
            s.setSoTimeout(100);
        }
        // wait a second
        Thread.sleep(1000);
        
        // hard shutdown
        for(Socket s : connectedClients)
        {
            s.close();
        }
        logger.log(Level.INFO, "Server closed");
    }
    
    private void listenForClient() throws IOException
    {
        while(isConnected())
        {
            Socket clientSocket = serverSocket.accept();
            connectedClients.add(clientSocket);
            logger.log(Level.INFO, "Server connected to client: " + clientSocket.getInetAddress().getHostAddress());
            ConnectionToClient newConnection = new ConnectionToClient(clientSocket, logger);
        }
    }
    
    public boolean isConnected()
    {
        return isConnected;
    }
    
    
    // Getters & Setters
    public int getNumberOfClients()
    {
        return connectedClients.size();
    }
    
    public void setPortnumber(int portNumber)
    {
        if(isConnected())
        {
            throw new IllegalArgumentException("Close server connection before changing the port");
        }
        if(portNumber >= 0 || portNumber <= 65535)
        {
            this.portNumber = portNumber;
            logger.log(Level.INFO, "Server changed port to: " + portNumber);
        }
        
        else
        {
            throw new IllegalArgumentException("The port number was illegal");
        }
    }
    
    public int getPortNumber()
    {
        return portNumber;
    }
    
    public void setLogLevel(Level logLevel)
    {
        logger.setLevel(logLevel);
    }
    
    
    public InetAddress getServerInetAddress()
    {
        return serverSocket.getInetAddress();
    }
}
