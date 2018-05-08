package server;

import java.sql.SQLException;

import server.abstractServer.AbstractServer;
import server.sql.SqlDAO;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


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
        	
        	case "list rooms":
        		sendToClient(database.getAllRooms());
        		break;
        		
        	case "list bookings":
        	{
        		String[] params = incoming[1].split(",");
        		sendToClient(database.allBookings(params[0], params[1], params[1]));
        		break;
        	}	
        	case "full customer":
        		sendToClient(database.fullCustomer(Integer.parseInt(incoming[1])));
        		break;
        		
        	case "search rooms":
        	{
        		JsonElement requestElement = new JsonParser().parse(incoming[1]);
        		JsonObject request = requestElement.getAsJsonObject();
        		String hotel = request.get("hotel").getAsString();
        		Boolean noSmoking = ! request.get("smokingAllowed").getAsBoolean(); // NOT-ing here
        		Boolean adjacent = request.get("adjacentRoomAvailable").getAsBoolean();
        		String startDate = request.get("startDate").getAsString();
        		String endDate = request.get("endDate").getAsString();
        		
        		String bedType = request.get("bedType").getAsString();
        		if (bedType.isEmpty()) bedType = null;
        		
        		String response = database.findFreeRooms(hotel, bedType, noSmoking, adjacent, startDate, endDate);
        		sendToClient(response);
        		break;
        	}
        	case "search bookings":
        	{
        		JsonElement requestElement = new JsonParser().parse(incoming[1]);
        		JsonObject request = requestElement.getAsJsonObject();
        		String firstName = request.get("firstName").getAsString();
        		String lastName = request.get("lastName").getAsString();
        		String telephoneNumber = request.get("telephoneNumber").getAsString();
        		String passportNumber = request.get("passportNumber").getAsString();
        		String bookingID = request.get("bookingNumber").getAsString();
        		Integer bID;
        		
        		if (firstName.isEmpty()) firstName = null;
        		if (lastName.isEmpty()) lastName = null;
        		if (telephoneNumber.isEmpty()) telephoneNumber = null;
        		if (passportNumber.isEmpty()) passportNumber = null;
        		if (bookingID.isEmpty()) {
        			bID = null;
        		} else {
        			bID = Integer.getInteger(bookingID);
        		}
        		
        		String response = database.findBookings(firstName, lastName, telephoneNumber, passportNumber, bID);
        		sendToClient(response);
        		break;
        	}
        	
        	case "set status":
        	{
        		String[] params = incoming[1].split(",");
        		sendToClient(database.updateBookingStatus(Integer.parseInt(params[0]), params[1]));
        		break;
        	}
        	
        	case "set expense":
        	{
        		String[] params = incoming[1].split(",");
        		sendToClient(database.updateBookingPayment(Integer.parseInt(params[0]), 
        				Integer.parseInt(params[1]), Integer.parseInt(params[2])));
        		break;
        	}
        	
        	
        	
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
