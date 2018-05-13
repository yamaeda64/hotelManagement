package server.abstractServer;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Handler;
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
    private ClientConnection clientConnection;
    
    /**
     * A function to start server and listen for new clients
     */
    public void startServer()
    {
        logger.log(Level.INFO, "Server started at port: "+ portNumber);
        try
        {
            serverSocket = new ServerSocket(portNumber);
            isConnected = true;
        } catch(IOException e)
        {
            logger.log(Level.SEVERE, "The server could't start: " + e.getMessage());
           exceptionOccured(e);
        }
    
        // A loop that listen for new connection for 5 sec, then cleans connectedClient if any disconnection accured
        while(isConnected())
        {
            try
            {
                serverSocket.setSoTimeout(SERVER_TIMEOUT);
                listenForClient();
                serverStarted();
            }
            /* whenever a timeout occurs the system checks for closed connection and removes them */
            catch(SocketTimeoutException e)
            {
                
                removeUnusedSockets();
                
    
            } catch(SocketException e)
            {
                logger.log(Level.SEVERE, "There was a socket exception: " + e.getMessage());
               exceptionOccured(e);
            }
            catch(IOException e)
            {
                logger.log(Level.SEVERE, "There was a IOException: " + e.getMessage());
                exceptionOccured(e);
            }
            
            
            
        
        }
    }
    // Removes threads that are no longer connected.
    private void removeUnusedSockets()
    {
        ArrayList<Socket> removeList = new ArrayList<>();
        Iterator<Socket> iterator = connectedClients.iterator();
        while(iterator.hasNext())
        {
            Socket socket = iterator.next();
            if(socket.isClosed())
            {
                removeList.add(socket);
            }
        }
        
        for(Socket closedSocket : removeList)
        {
            connectedClients.remove(closedSocket);
        }
    }
    
    /**
     * Stop running the server
     */
    public void stopServer()
    {
        try
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
            serverClosed();
        } catch(InterruptedException e)
        {
            logger.log(Level.SEVERE, "There was a InterruptedException: " + e.getMessage());
            exceptionOccured(e);
        } catch(SocketException e)
        {
            logger.log(Level.SEVERE, "There was a socket exception: " + e.getMessage());
            exceptionOccured(e);
        } catch(IOException e)
        {
            logger.log(Level.SEVERE, "There was a IOException: " + e.getMessage());
            exceptionOccured(e);
        }
    
    }
    
    private void listenForClient() throws IOException
    {
        while(isConnected())
        {
            Socket clientSocket = serverSocket.accept();
            connectedClients.add(clientSocket);
            logger.log(Level.INFO, "Server connected to client: " + clientSocket.getInetAddress().getHostAddress());
            clientConnection = new ClientConnection(clientSocket, logger, this);
            
        }
    }
    
    public void sendToClient(String message)
    {
        if(clientConnection == null)
        {
            logger.log(Level.SEVERE, "there was no connection to send to");
            exceptionOccured(new UnknownHostException("There was no open connection"));
        }
        else
        {
            try
            {
                clientConnection.sendMessageToClient(message);
            }
            catch(IOException e)
            {
                logger.log(Level.SEVERE, "There was an exception when trying to send message");
                exceptionOccured(e);
            }
    
        }
    }
    
    protected abstract void handleMessageFromClient(String message);
    
    public boolean isConnected()
    {
        return isConnected;
    }
    
    
    // Getters & Setters
    public int getNumberOfClients()
    {
        return connectedClients.size();
    }
    
    public void setPortnumber(int portNumber) throws IllegalArgumentException
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
    
    public void setLogHandler(Handler handler)
    {
        logger.addHandler(handler);
    }
    public void setLogLevel(Level logLevel)
    {
        logger.setLevel(logLevel);
    }
    
    public InetAddress getServerInetAddress()
    {
        return serverSocket.getInetAddress();
    }
    
    protected void setClientConnection(ClientConnection currentConnection)
    {
        this.clientConnection = currentConnection;
    }
    
    // Hooks (methods the subclass can override)
    public void serverStarted()
    {
        
    }
    public void serverClosed()
    {
        
    }
    public void clientConnected()
    {
        
    }
    public void clientDisconnected()
    {
        
    }
    public void exceptionOccured(Exception e)
    {
        
    }
    
}
