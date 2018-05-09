package server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.abstractServer.AbstractServer;
import server.sql.SqlDAO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class HotelServer extends AbstractServer
{
    SqlDAO database;

    private Logger logger = Logger.getLogger(HotelServer.class.getName());
    
    public HotelServer() throws SQLException {
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
        logger.log(Level.INFO, "incoming\n-------------\n");
        String response = "ERROR did not understand the request!";
        if(message.indexOf(':') >= 0) {
        	String[] incoming = message.split(":", 2);
        	switch(incoming[0]) {
        	
        	case "list rooms":
        		response = database.getAllRooms();
        		break;
        		
        	case "list bookings":
        	{
        		String[] params = incoming[1].split(",");
        		logger.log(Level.SEVERE, "PARAM0: " + params[0]);
        		response = database.allBookingsForHotel(params[0], params[1], params[1]);
        		break;
        	}	
        	case "full customer":
        		response = database.fullCustomer(Integer.parseInt(incoming[1]));
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
        		
        		JsonElement bedStep = request.get("bedType");
        		String bedType = null;
        		try {
        			bedType = bedStep.getAsString();
        		} catch (NullPointerException e) {
        			logger.log(Level.FINE, "-- a bed was null so i catched that");
        		}
        		
    			response = database.findFreeRooms(hotel, bedType, noSmoking, adjacent, startDate, endDate);
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
        		
        		response = database.findBookings(firstName, lastName, telephoneNumber, passportNumber, bID);
        		break;
        	}
        	
        	case "set status":
        	{
        		String[] params = incoming[1].split(",");
        		response = database.updateBookingStatus(Integer.parseInt(params[0]), params[1]);
        		break;
        	}
        	
        	case "set expense":
        	{
        		String[] params = incoming[1].split(",");
        		response = database.updateBookingPayment(Integer.parseInt(params[0]), 
        				Double.parseDouble(params[1]), Double.parseDouble(params[2]));
        		break;
        	}
        	
        	case "create booking":
        	{
        		JsonElement requestElement = new JsonParser().parse(incoming[1]);
        		JsonObject request = requestElement.getAsJsonObject();
        		
        		String startDate = request.get("startDate").getAsString();
        		String endDate = request.get("endDate").getAsString();
        		
        		JsonArray roomJ = request.get("bookedRooms").getAsJsonArray();
        		ArrayList<Integer> rooms = new ArrayList<>();
        		Iterator<JsonElement> roomIt = roomJ.iterator();
        		while(roomIt.hasNext()) {
        			JsonObject room = roomIt.next().getAsJsonObject();
        			rooms.add(room.get("id").getAsInt());
        		}
        		
        		response = database.createBooking(rooms, startDate, endDate);
        		break;
        	}
        	
        	case "realize booking":
        	{
        		String[] params = incoming[1].split(",", 3);
        		int bookingID = Integer.parseInt(params[0]);
        		int newPrice = Integer.parseInt(params[1]);
        		JsonElement requestElement = new JsonParser().parse(params[2]);
        		JsonObject request = requestElement.getAsJsonObject();
        		
        		String firstName = request.get("firstName").getAsString();
        		String lastName = request.get("lastName").getAsString();
        		String telephone = request.get("telephone").getAsString();
        		String idNumber = request.get("idNumber").getAsString();
        		String address = request.get("address").getAsString();
        		String creditCard = request.get("creditCard").getAsString();
        		String passportNumber = request.get("passportNumber").getAsString();
        		int powerLevel = request.get("powerLevel").getAsInt();
        		
        		response = database.realizeBooking(bookingID, newPrice, firstName, lastName, telephone, idNumber, address, creditCard, powerLevel, passportNumber);
        		break;
        	}
        	
        	} // end case
        	
            logger.log(Level.INFO, response);
            logger.log(Level.INFO, "--------------------");
        	sendToClient(response);
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
