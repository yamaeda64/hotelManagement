package client.abstractClient;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    private char[] buffer;
    private final int BUFFER_SIZE = 1024;
    private final int SOCKET_TIMEOUT = 5000;
    private Logger logger = Logger.getLogger(AbstractClient.class.getName());
    private InputStreamReader inputStream;
    private OutputStreamWriter outputStream;
    
    /**
     * Opens a connection to server, the input is a InetSocketAddress to the server.
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
            outputStream = new OutputStreamWriter(socket.getOutputStream());
            inputStream = new InputStreamReader(socket.getInputStream());
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
    
    /**
     * Close the connection to server, if more transitions are to be made a new open connection has to be called.
     */
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
    
    /**
     * Send a string to the connected server
     * @param message The input
     * @return True if messege could be sent successfully
     */
    public boolean sendToServer(String message)
    {
        message += '\0';
        buffer = message.toCharArray();
    
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
        catch(Exception e)
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
            inputStream = new InputStreamReader(socket.getInputStream());
    
            buffer = new char[BUFFER_SIZE];
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
        catch(Exception e)
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
    
    
    /**
     * Has no implementation but is called every time a connection is established.
     * This can be overridden if any functionality is needed
     */
    public void connectionEstablished()
    {
        
    }
    /**
     * Has no implementation but is called every time a connection is closed.
     * This can be overridden if any functionality is needed
     */
    public void connectionClosed()
    {
        
    }
    /**
     * Has no implementation but is called every time a exception occurs.
     * This can be overridden if any functionality is needed
     */
    public void connectionException(Exception e)
    {
        
    }
}
