package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class running a single connection on a new thread.
 */
public class ConnectionToClient extends Thread
{
    
    private Socket clientSocket;
    private boolean isConnected;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Logger logger;
    
    public ConnectionToClient(Socket clientSocket, Logger logger) throws IOException
    {
        this.clientSocket = clientSocket;
        isConnected = true;
        run();
    }
    @Override
    public void run()
    {
        try
        {
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
            clientSocket.setSoTimeout(5000);
            listenForClientRequest();
        }
        catch(IOException e)
        {
            logger.log(Level.SEVERE, "There was a problem when communicating with client");
        }
        
        
    }
    
    private void listenForClientRequest() throws IOException
    {
        int readBytes = 0;
        byte[] buffer = new byte[1024];
        StringBuilder inputRequest = new StringBuilder();
        
        while((readBytes = inputStream.read(buffer)) != -1)
        {
            inputRequest.append(new String(buffer,0,readBytes));
        }
        logger.log(Level.FINEST, "MessageFromClient: " + inputRequest.toString());
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
