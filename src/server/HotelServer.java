package server;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.abstractServer.AbstractServer;
import server.sql.SqlDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HotelServer extends AbstractServer
{
    SqlDAO database;

    private Logger logger = Logger.getLogger(HotelServer.class.getName());
    
    public HotelServer() throws SQLException {
    	try {
    		// create new database
			database = new SqlDAO();
			
			/*
			 * The following sets up the clean up routine to run every five minutes.
			 */
			ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
			exec.scheduleAtFixedRate(new Runnable() {
			  @Override
			  public void run() {
				  try {
					SqlDAO tempdao = new SqlDAO();
					tempdao.clean();
					tempdao.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				  
			  }
			}, 0, 5, TimeUnit.MINUTES);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SQLException("Could probably not instantiate database driver");
		}
    }
	
    @Override
    protected void handleMessageFromClient(String message)
    {
    	
    	long incomingTime = System.currentTimeMillis();
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

        		JsonObject bookingJson = new JsonParser().parse(incoming[1]).getAsJsonObject();
        		
        		String[] params = incoming[1].split(",", 3);
        		int bookingID = bookingJson.get("id").getAsInt(); //Integer.parseInt(params[0]);
        		int newPrice = bookingJson.get("givenPrice").getAsInt();
        		
        		//JsonObject customerJson = new JsonParser().parse(params[2]).getAsJsonObject();
        		JsonObject customerJson = bookingJson.get("customer").getAsJsonObject();
        		
        		String firstName = customerJson.get("firstName").getAsString();
        		String lastName = customerJson.get("lastName").getAsString();
        		String telephone = customerJson.get("telephoneNumber").getAsString();
        		String idNumber = customerJson.get("personalNumber").getAsString();
        		String address = customerJson.get("address").getAsJsonObject().toString();
        		String creditCard = customerJson.get("creditCard").getAsJsonObject().toString();
        		String passportNumber = customerJson.get("passportNumber").getAsString();
        		String powerLevel = customerJson.get("powerLevel").getAsString();        		
        		
        		response = database.realizeBooking(bookingID, newPrice, firstName, lastName, telephone, idNumber, address, creditCard, powerLevel, passportNumber);
        		break;
        	}
        	
        	case "finalize booking":
        	{
        		String[] params = incoming[1].split(",");
        		response = database.updateBookingPayment(Integer.parseInt(params[0]), 
        				0, Double.parseDouble(params[1]));
        		break;
        	}
        	
        	
        	} // end case
        	
            logger.log(Level.INFO, response);
            logger.log(Level.INFO, "--------------------");
            long finishTime = System.currentTimeMillis() - incomingTime;
            logger.log(Level.FINE, "took " + finishTime + " ms to finish.");
        	sendToClient(response);
        }
        
    }
    
    @Override
    public void exceptionOccured(Exception e)
    {
       
    }
}
