package server.abstractServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class running a single connection on a new thread.
 */
public class ClientConnection extends Thread
{
    private Socket clientSocket;
    private boolean isConnected;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Logger logger;
    private AbstractServer abstractServer;
    
    public ClientConnection(Socket clientSocket, Logger logger, AbstractServer abstractServer) throws IOException
    {
        this.abstractServer = abstractServer;
        this.logger = logger;
        this.clientSocket = clientSocket;
        isConnected = true;
        run();
    }
    @Override
    public void run()
    {
        try
        {
            abstractServer.setClientConnection(this);
            abstractServer.clientConnected();
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
            clientSocket.setSoTimeout(5000);
            listenForClientRequest();
        }
        catch(IOException e)
        {
            logger.log(Level.SEVERE, "There was a problem when communicating with client");
            abstractServer.exceptionOccured(e);
        }
    }
    
    private void listenForClientRequest() throws IOException
    {
        boolean clientIsConnected = true;
        while(clientIsConnected)
        {
            int readBytes = 0;
            byte[] buffer = new byte[1024];
            StringBuilder inputRequest = new StringBuilder();
            
            try
            {
                boolean isEndOfMsg = false;
                while(readBytes != -1 &! isEndOfMsg)
                {
                    readBytes = inputStream.read(buffer);
                    
                    if(readBytes == -1)
                    {
                        abstractServer.clientDisconnected();
                        clientSocket.close();
                        clientIsConnected = false;
                    }
                    else
                    {
                        if(buffer[readBytes - 1] == '\0')
                        {
                            inputRequest.append(new String(buffer, 0, readBytes - 1));
                            isEndOfMsg = true;
                        } else
                        {
                            inputRequest.append(new String(buffer, 0, readBytes));
                        }
                    }
                }
                logger.log(Level.FINEST, "MessageFromClient: " + inputRequest.toString());
    
                abstractServer.handleMessageFromClient(inputRequest.toString());
                
            }
            catch(SocketTimeoutException e)
            {
                
                abstractServer.exceptionOccured(new SocketTimeoutException("The connection timed out"));
                logger.log(Level.SEVERE, "The connection timed out");
            }
        }
    }
    
    public void sendMessageToClient(String message) throws IOException
    {
        message += '\0';
        byte[] buffer = message.getBytes();
        outputStream = clientSocket.getOutputStream();
        outputStream.write(buffer);
        outputStream.flush();
    }
    
    /**
     * Close the current connection
     * @throws IOException
     */
    public void closeConnection() throws IOException
    {
        clientSocket.close();
        isConnected = false;
    }
    
    /**
     * Returns the InetAddress of the host
     * @return InetAddress
     */
    public InetAddress getInetAddress()
    {
        return clientSocket.getInetAddress();
    }
}
