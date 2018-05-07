package server;

import java.sql.SQLException;

import server.abstractServer.AbstractServer;
import server.sql.SqlDAO;


public class HotelServer extends AbstractServer
{
    SqlDAO database;
    public HotelServer() throws SQLException {
    	// hej
    	try {
			database = new SqlDAO();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SQLException("Could not instantiate database driver");
		}
    }
	
    @Override
    protected void handleMessageFromClient(String message)
    {
        if(message.indexOf(':') >= 0) {
        	String[] incoming = message.split(":");
        	switch(incoming[0]) {
        	case "retrieve rooms":
        		
        	}
        }
        else
        {
            System.out.println("MESSAGE WAS WRONG!!!!    " + message);
        }
    }
    
    @Override
    public void exceptionOccured(Exception e)
    {
       System.out.println("Exception occured: " +  e.getClass().getName() + e.getMessage());
    }
}
