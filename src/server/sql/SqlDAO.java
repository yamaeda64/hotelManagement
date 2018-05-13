package server.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class SqlDAO {
	private SqlQueries query;
	
	HashMap<String, Integer> customerPowerLevelTranslator = new HashMap<>();

	public SqlDAO() throws SQLException, ClassNotFoundException {
		query = new SqlQueries();
		
        customerPowerLevelTranslator.put("NONE", 0);
		customerPowerLevelTranslator.put("BRONZE", 5);
		customerPowerLevelTranslator.put("SILVER", 10);
		customerPowerLevelTranslator.put("GOLD", 15);
		customerPowerLevelTranslator.put("PLATINUM", 20);
		customerPowerLevelTranslator.put("DIAMOND", 25);
	}
	
	public void close() throws SQLException {
		query.close();
	}
	/**
	 * Creates a json string representing a single room.
	 * 
	 * @param room
	 *            a ResultSet containing the specific room.
	 * @param idOnly
	 *            if true, only the id of a room will be returned.
	 * @return json string.
	 * @throws SQLException
	 */
	private String packRoom(ResultSet room, boolean idOnly) throws SQLException {
		String[] keys = new String[] { "id", "hotel", "roomNumber", "bedType", "noSmoking", "adjacentRoom",
				"view", "standardPrice" };
		
		if (idOnly) {
			return room.getString("id");
		}
		
		StringBuilder output = new StringBuilder("{");
		int i = 0;
		for (String key : keys) {
			if (key.equals("adjacentRoom")) {
				int adjacentRoom = room.getInt("adjacentRoom");
				output.append("\"adjacentRoom\":\"" + adjacentRoom + "\"");
			} else {
				output.append("\"").append(key).append("\": \"").append(room.getString(key)).append("\"");
			}
			if (i++ < (keys.length - 1))
				output.append(", ");
		}
		output.append("}");
		return output.toString();
	}

	/**
	 * Creates a json list containing a set of rooms.
	 * 
	 * @param rooms
	 *            the rooms to list.
	 * @return a json string.
	 * @throws SQLException
	 */
	private String packRooms(ResultSet rooms, boolean idOnly) throws SQLException {
		StringBuilder output = new StringBuilder("");
		output.append("[");
		rooms.beforeFirst();
		while (rooms.next()) {
			output.append(packRoom(rooms, idOnly));
			if (!rooms.isLast()) {
				output.append(",");
			}
		}
		output.append("]");
		return output.toString();
	}
	private String packRooms(ResultSet rooms) throws SQLException {
		return packRooms(rooms, true); // true
	}
	
	/**
	 * Creates a json string representing a single customer.
	 * 
	 * @param customer
	 *            a ResultSet containing the specific customer.
	 * @param extended
	 *            specifies if we want to send all data (credit card and passport
	 *            number is "protected".)
	 * @return json string.
	 * @throws SQLException
	 */
	private String packCustomer(ResultSet customer, boolean extended) throws SQLException {
		String[] customerKeys = new String[] { "id", "firstName", "lastName" };
		if (extended)
			customerKeys = new String[] { "id", "firstName", "lastName", "telephoneNumber", "personalNumber", "address",
					"creditCard", "powerLevel", "passportNumber" };

		StringBuilder output = new StringBuilder("{");
		int i = 0;
		for (String key : customerKeys) {
			if (key.equals("creditCard") || key.equals("address")) {
				output.append("\"").append(key).append("\":").append(customer.getString(key));
			} else {
				output.append("\"").append(key).append("\": \"").append(customer.getString(key)).append("\"");
			}
			if (i++ < (customerKeys.length - 1))
				output.append(", ");
		}
		output.append("}");
		return output.toString();
	}

	/**
	 * Creates a json list containing a set of customers.
	 * 
	 * @param customers
	 *            the customers to list.
	 * @return a json string.
	 * @throws SQLException
	 */
	private String packCustomers(ResultSet customers) throws SQLException {
		StringBuilder output = new StringBuilder("[");
		customers.beforeFirst();
		while (customers.next()) {
			output.append(packCustomer(customers, false));
			if (customers.isLast()) {
				output.append("");
			} else {
				output.append(",");
			}
		}
		output.append("]");
		return output.toString();
	}

	/**
	 * Creates a json string representing a single booking.
	 * 
	 * @param booking
	 *            a ResultSet containing the specific booking.
	 * @return json string.
	 * @throws SQLException
	 */
	private String packBooking(ResultSet booking) throws SQLException {
		String[] bookingKeys = new String[] { "id", "customer", "givenPrice", "amountPaid", "startDate", "endDate", "status" };

		StringBuilder output = new StringBuilder("{");
		int i = 0;
		for (String key : bookingKeys) {
			output.append("\"").append(key).append("\": ");
			if (key.equals("customer")) {
				ResultSet customer = query.searchCustomer(booking.getInt("customer"));
				customer.next();
				output.append(packCustomer(customer, false));
			} else {
				output.append("\"").append(booking.getString(key)).append("\"");
			}
			//if (i++ < (bookingKeys.length - 1))
			output.append(", ");
		}

		// rooms is a bit special because it's not a real key
		ResultSet rooms = query.roomsForBooking(booking.getInt("id"));
		output.append("\"bookedRooms\": ").append(packRooms(rooms));

		output.append("}");
		return output.toString();
	}

	/**
	 * Creates a json list containing a set of bookings.
	 * 
	 * @param bookings
	 *            the bookings to list.
	 * @return a json string.
	 * @throws SQLException
	 */
	private String packBookings(ResultSet bookings) throws SQLException {
		StringBuilder output = new StringBuilder("[");
		bookings.beforeFirst();
		while (bookings.next()) {
			output.append(packBooking(bookings));
			if (bookings.isLast()) {
				output.append("");
			} else {
				output.append(",");
			}
		}
		output.append("]");
		return output.toString();
	}
	
	/**
	 * Checks the number of rows to see if it's empty.
	 * Most likely more costly than we'd like.
	 * @param set
	 * @return
	 * @throws SQLException
	 */
	private boolean resultSetIsEmpty(ResultSet set) throws SQLException {
		set.beforeFirst();
		int count = 0;
		while(set.next()) {
			if (count++ > 0) return false;
		}
		return true;
	}
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	/**
	 * Tries to get a list of all the available rooms. This is for the client to
	 * build a cache at startup.
	 * 
	 * @return a JSON-string representing all the rooms in the database.
	 */
	public String getAllRooms() {
		try {
			ResultSet roomSet = query.allRooms();
			String rooms = packRooms(roomSet, false);
			return "rooms: " + rooms;
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR " + e.toString();
		}
	}

	/**
	 * Returns all bookings within a certain timeframe.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String allBookings(String startDate, String endDate) {
		try {
			ResultSet bookingSet = query.bookingsInsideTimeframe(startDate, endDate);
			bookingSet.beforeFirst();
			String response = "bookings:" + packBookings(bookingSet);
			return response;
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	
	/**
	 * Returns all bookings within a certain timeframe and a specific hotel.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String allBookingsForHotel(String hotel, String startDate, String endDate) {
		try {
			ResultSet bookingSet = query.bookingsForHotel(hotel, startDate, endDate);
			if (bookingSet == null) return "bookings:[]";
			bookingSet.beforeFirst();
			String response = "bookings:" + packBookings(bookingSet);
			return response;
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}

	/**
	 * Compiles a list of rooms meeting the criteria, that are unbooked during a
	 * specific time.
	 * 
	 * @param hotel
	 * @param bedSize
	 * @param smoking
	 * @param adjacent
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String findFreeRooms(String hotel, String bedType, boolean noSmoking, boolean adjacent, String startDate,
			String endDate) {
		try {
			// bedtype can be null!
			ResultSet rooms = query.findFreeRooms(hotel, bedType, noSmoking, adjacent, startDate, endDate);
			if (resultSetIsEmpty(rooms)) return "ERROR no such rooms available";
			return "available rooms:" + packRooms(rooms);
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR";
		}

	}

	/**
	 * Tries to find any bookings with a specific ID, or with a customer that we
	 * know. At least one parameter needs to be specified.
	 * 
	 * @param firstName
	 *            Name of customer
	 * @param lastName
	 *            Name of customer
	 * @param telephoneNumber
	 *            Customers telephoneNumber
	 * @param passport
	 *            Customers passport number
	 * @param bookingId
	 *            The booking id
	 * @return A json string of the booking.
	 */
	public String findBookings(String firstName, String lastName, String telephoneNumber, String passport,
			Integer bookingId) {
		try {
			// compile hashmap
			HashMap<String, String> parameter = new HashMap<>();
			if (firstName != null)
				parameter.put("firstName", firstName);
			if (lastName != null)
				parameter.put("lastName", lastName);
			if (telephoneNumber != null)
				parameter.put("telephoneNumber", telephoneNumber);
			if (passport != null)
				parameter.put("passportNumber", passport);
			// see if there are any people to find?
			ResultSet results = null;
			if (parameter.size() > 0) {
				ResultSet customerLead = query.findCustomer(parameter);
				ArrayList<Integer> potentialCustomers = new ArrayList<>();
				while (customerLead.next()) {
					potentialCustomers.add(customerLead.getInt("id"));
				}
				if (!potentialCustomers.isEmpty()) {
					results = query.bookingForCustomer(potentialCustomers, bookingId); // bookingId is allowed to be
																						// null here!
				}
			} else if (bookingId != null) {
				// ok no customer. but does the bookingId give us anything?
				results = query.specificBooking(bookingId);
			} else {
				return "ERROR bad parameters";
			}
			
			if (results != null) {
				return "bookings:" + packBookings(results);
			} else {
				// exception.
				return "ERROR no such bookings available!";
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR mysql error";
		}
	}

	/**
	 * Returns a json list of all the customers in the database, without sensitive
	 * data.
	 * 
	 * @return json string.
	 */
	public String allCustomers() {
		try {
			ResultSet allCustomers = query.allCustomers();
			return "customers:" + packCustomers(allCustomers);
		} catch (SQLException e) {
			return "ERROR";
		}
	}

	/**
	 * Returns a json object of one specific customer, including sensitive data.
	 * 
	 * @param id
	 *            ID of the customer.
	 * @return json string.
	 */
	public String fullCustomer(int id) {
		try {
			ResultSet customer = query.searchCustomer(id);
			customer.next();
			return "customer:" + packCustomer(customer, true);
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	/**
	 * Creates a booking.
	 * 
	 * @param customer
	 * @param givenPrice
	 * @param rooms
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String createBooking(ArrayList<Integer> rooms, String startDate,
			String endDate) {
		try {
			if (query.roomsUnbooked(rooms, startDate, endDate)) {
				int bookingId = query.createBooking(-1, rooms, 0, 0, startDate, endDate);
				return "new booking:" + bookingId;
			} else {
				return "ERROR the selected rooms are already booked";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR " +  e.getMessage();
		}
	}
	
	/**
	 * Realize a booking - create a customer, add it to the booking, and set the price.
	 * 
	 * @param id
	 * @param newPrice
	 * @param firstName
	 * @param lastName
	 * @param telephoneNumber
	 * @param personalNumber
	 * @param address
	 * @param creditCard
	 * @param powerLevel
	 * @param passportNumber
	 * @return
	 */
	public String realizeBooking(int id, int newPrice, String firstName, String lastName, String telephoneNumber, String personalNumber, String address, String creditCard, String powerLevel, String passportNumber) {
		try {
			int newCustomerId = query.createCustomer(firstName, lastName, telephoneNumber, personalNumber, address, creditCard,
					powerLevel, passportNumber);
			query.setCustomerOnBooking(id, newCustomerId);
			query.updateBookingPayment(id, 0, newPrice);
			query.updateBookingStatus(id, "BOOKED");
			
			ResultSet rooms = query.roomsForBooking(id);
			double basePrice = 0;
			
			while(rooms.next()) {
				int nyPris = rooms.getInt("standardPrice");
				basePrice += nyPris;
			}
			double powerLevelModifier = 1-(customerPowerLevelTranslator.get(powerLevel)/100.0);
			basePrice = basePrice * powerLevelModifier;
			
			return "booking price:" + basePrice;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR";
		}
		
		
	}

	/**
	 * Creates a customer.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param telephoneNumber
	 * @param personalNumber
	 * @param address
	 * @param creditCard
	 * @param powerLevel
	 * @param passportNumber
	 * @return
	 */
	public String createCustomer(String firstName, String lastName, String telephoneNumber, String personalNumber, String address,
			String creditCard, String powerLevel, String passportNumber) {
		int newCustomerId;
		try {
			newCustomerId = query.createCustomer(firstName, lastName, telephoneNumber, personalNumber, address, creditCard,
					powerLevel, passportNumber);
			ResultSet newCustomer = query.searchCustomer(newCustomerId);
			newCustomer.next();
			return "customer:" + packCustomer(newCustomer, false);
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}

	/**
	 * Updates the STATUS of a booking (active/cancelled/etc.)
	 * 
	 * @param id
	 *            ID of the booking.
	 * @param status
	 *            New status of the booking.
	 * @return "OK" if successful.
	 */
	public String updateBookingStatus(int id, String status) {
		try {
			query.updateBookingStatus(id, status);
			return "OK";
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}

	/**
	 * Updates the amount the customer has paid and should pay.
	 * 
	 * @param id
	 *            Booking ID that this is about.
	 * @param amountPaid
	 *            The amount the customer has paid.
	 * @param totalPayment
	 *            The amount the customer is expected to pay.
	 * @return OK if successful.
	 */
	public String updateBookingPayment(int id, double amountPaid, double totalPayment) {
		try {
			query.updateBookingPayment(id, amountPaid, totalPayment);
			return "OK";
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	
	public void clean() {
		try {
			query.removeUnrealizedBookings();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

