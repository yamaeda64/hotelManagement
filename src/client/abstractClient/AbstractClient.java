package client.abstractClient;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractClient
{
    private boolean isConnected;
    private int portNumber;
    private Socket socket;
    private byte[] buffer;
    private final int BUFFER_SIZE = 1024;
    private final int SOCKET_TIMEOUT = 5000;
    private Logger logger = Logger.getLogger(AbstractClient.class.getName());
    private InputStream inputStream;
    private OutputStream outputStream;
    
    /**
     * Opens a onnection to server, the input is a InetSocketAddress to the server.
     * Using the connectionException method in case of exception.
     *
     * @param serverAddress the server address
     * @return true if connection could connect, false otherwise,
     */
     
    public boolean openConnection(InetSocketAddress serverAddress)
    {
        try
        {
            socket = new Socket(serverAddress.getAddress(), serverAddress.getPort());
            socket.setSoTimeout(SOCKET_TIMEOUT);
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            isConnected = true;
            connectionEstablished();
            return true;
        }
        catch(SocketException e)
        {
            connectionException(e);
            
        }
        catch(IOException e)
        {
            connectionException(e);
        }
        
        return false;
    }
    
    public void closeConnection()
    {
        try
        {
            inputStream.close();
            logger.log(Level.INFO, "Client closed connection to server");
        } catch(IOException e)
        {
            logger.log(Level.SEVERE, "The client could't close the connection");
            connectionException(e);
        }
    }
    
    public boolean sendToServer(String message)
    {
        message += '\0';
        buffer = message.getBytes();
    
        try
        {
            outputStream.write(buffer);
            outputStream.flush();
        } catch(SocketTimeoutException e)
        {
            connectionException(new SocketTimeoutException("The connection timed out"));
        }
        catch(IOException e)
        {
            connectionException(e);
        }
    
        logger.log(Level.FINEST, "Msg sent to server: " + message.substring(0,message.length()-1));
        
        return true;
        
    }
    
    /**
     * Receives a string from server and sends that to handleMessageFromServer method.
     */
    public void receiveMessageFromServer()
    {
        try
        {
            inputStream = socket.getInputStream();
    
            buffer = new byte[BUFFER_SIZE];
            StringBuilder sb = new StringBuilder();
            int readBytes = 0;
            boolean isEndOfMessage = false;
            while(readBytes != -1 &! isEndOfMessage)
            {
                readBytes = inputStream.read(buffer);
                if(readBytes == -1)
                {
                    socket.close();
                }
                else
                {
                    if(buffer[readBytes - 1] == '\0')
                    {
                        sb.append(new String(buffer, 0, readBytes - 1));
                        isEndOfMessage = true;
                    } else
                    {
                        sb.append(new String(buffer, 0, readBytes));
                    }
                }
            }
            
            logger.log(Level.FINEST, "received msg: " + sb.toString());
    
            handleMessageFromServer(sb.toString());
        } catch(SocketTimeoutException e)
        {
            connectionException(new SocketTimeoutException("The connection timed out"));
        }
        catch(IOException e)
        {
            connectionException(e);
        }
    }
    
    /**
     * Abstract method that should handle what the client does when receiving a message.
     * @param message as a String
     */
    public abstract void handleMessageFromServer(String message);
    
    
    // Getters & Setters
    
    public int getPortNumber()
    {
        return portNumber;
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
    
    
    public boolean isConnected()
    {
        return isConnected;
    }
    
    public void setLogHandler(Handler handler)
    {
        logger.addHandler(handler);
    }
    public void setLogLevel(Level logLevel)
    {
        logger.setLevel(logLevel);
    }
    
    
    // Hooks (methods the subclass can override)
    
    public void connectionEstablished()
    {
        
    }
    public void connectionClosed()
    {
        
    }
    public void connectionException(Exception e)
    {
        
    }
}
