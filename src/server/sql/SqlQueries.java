package server.sql;

import java.sql.*;
import java.util.*;

class SqlQueries {
	private final String db_url = "jdbc:mysql://" + SqlLogin.db_hostname + ":" + SqlLogin.db_port + "/" + SqlLogin.db_dbname + "?user=" + SqlLogin.db_username + "&password=" + SqlLogin.db_password + "&useUnicode=true&characterEncoding=utf8&autoReconnect=true";
	private Connection uplink;
	SqlQueries() throws ClassNotFoundException, SQLException {
		// creates a new instance of the driver, which loads itself into the place where
		// it needs to be --> we dont need to save it or anything ourselves
		Class.forName("com.mysql.jdbc.Driver");
		this.uplink = DriverManager.getConnection(db_url);
	}
	void close() throws SQLException {
		uplink.close();
	}
	
	
	/**
	 * Inserts a new customer in the database.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param telephoneNumber
	 * @param personalNumber
	 * @param address
	 * @param creditCard
	 * @param powerLevel
	 * @param passportNumber
	 * @return the assigned ID for the new customer.
	 * @throws SQLException
	 */
	int createCustomer(String firstName, String lastName, String telephoneNumber, String personalNumber, String address, String creditCard, String powerLevel, String passportNumber) throws SQLException {
		PreparedStatement prepStatement = uplink.prepareStatement("insert into hotel.customers(firstName, lastName, telephoneNumber, personalNumber, address, creditCard, powerLevel, passportNumber) values (?, ?, ?, ?, ?, ?, ?, ?);");
		prepStatement.setString(1, firstName);
		prepStatement.setString(2, lastName);
		prepStatement.setString(3, telephoneNumber);
		prepStatement.setString(4, personalNumber);
		prepStatement.setString(5, address);
		prepStatement.setString(6, creditCard);
		prepStatement.setString(7, powerLevel);
		prepStatement.setString(8, passportNumber);
		prepStatement.executeUpdate();
		
		PreparedStatement getIdStatement = uplink.prepareStatement("SELECT LAST_INSERT_ID();");
		ResultSet cre8ed = getIdStatement.executeQuery();
		cre8ed.next();
		return cre8ed.getInt(1);
	}
	
	/**
	 *  Search for customers..
	 *  
	 *  ... unfinished, and unused.
	 */
	ResultSet searchCustomer(String firstName, String lastName, String telephoneNumber, String personalNumber, String address, String creditCard, int powerLevel) throws SQLException {
		PreparedStatement prepStatement = uplink.prepareStatement("SELECT * FROM hotel.customers WHERE "
				+ "firstName = ? AND lastName = ?");
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}
	
	/**
	 * Search for customer by ID
	 * 
	 * @param id
	 * @return customer
	 * @throws SQLException
	 */
	ResultSet searchCustomer(int id) throws SQLException {
		PreparedStatement prepStatement = uplink.prepareStatement("SELECT * FROM hotel.customers WHERE "
				+ "id = ?");
		prepStatement.setInt(1, id);
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}
	
	/**
	 * Returns all customers currently in the database.
	 * 
	 * @return
	 * @throws SQLException
	 */
	ResultSet allCustomers() throws SQLException {
		PreparedStatement prepStatement = uplink.prepareStatement("SELECT * FROM hotel.customers");
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}
	
	/**
	 * Get multiple customers by ID.
	 * 
	 * @param which
	 * @return
	 * @throws SQLException
	 */
	ResultSet multipleCustomers(ArrayList<Integer> which) throws SQLException {
		// build the hecking query
		StringBuilder query = new StringBuilder("SELECT * FROM hotel.customers WHERE id IN (");
		Iterator<Integer> idIterator = which.iterator();
		while (idIterator.hasNext()) {
			String i = String.valueOf(idIterator.next());
			query.append(i);
			if (idIterator.hasNext()) {
				query.append(", ");
			}
		}
		query.append(")");
		
		// search
		PreparedStatement prepStatement = uplink.prepareStatement(query.toString());
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}

	/**
	 * Create a room in the database.
	 * 
	 * @param hotel
	 * @param number
	 * @param size
	 * @param quality
	 * @param bedSize
	 * @param bedAmount
	 * @param status
	 * @param smokeFree
	 * @param adjacentRooms
	 * @param view
	 * @throws SQLException
	 */
	void createRoom(int hotel, int number, int size, int quality, int bedSize, int bedAmount, int status, int smokeFree, String adjacentRooms, int view) throws SQLException {
		PreparedStatement prepStatement = uplink.prepareStatement("insert into hotel.rooms(hotel, number, size, quality, bedSize, bedAmount, status, smokeFree, adjacentRooms, view) values (?, ?, ?, ?, ?, ?, ?)");
		prepStatement.setInt(1, hotel);
		prepStatement.setInt(2, number);
		prepStatement.setInt(3, size);
		prepStatement.setInt(4, quality);
		prepStatement.setInt(5, bedSize);
		prepStatement.setInt(6, bedAmount);
		prepStatement.setInt(7, status);
		prepStatement.setInt(8, smokeFree);
		prepStatement.setString(9, adjacentRooms);
		prepStatement.setInt(10, view);
		prepStatement.executeUpdate();
	}
	
	/**
	 * Search for rooms in the database by their properties.
	 * 
	 * @param hotel
	 * @param size
	 * @param quality
	 * @param bedSize
	 * @param bedAmount
	 * @param status
	 * @param smokeFree
	 * @param adjacentRooms
	 * @param view
	 * @return
	 * @throws SQLException
	 */
	ResultSet searchRoom(int hotel, int size, int quality, int bedSize, int bedAmount, int status, int smokeFree, int[] adjacentRooms, int view) throws SQLException {
		PreparedStatement prepStatement = uplink.prepareStatement("SELECT * FROM hotel.rooms WHERE "
				+ "hotel=? AND size=? AND quality = ? AND bedSize = ? AND bedAmount = ? AND status = ? AND smokeFree = ? AND adjacentRooms = "
				+ "? AND view = ?");
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}
	
	/**
	 * Returns all rooms currently in the database.
	 * 
	 * @return all rooms in the database.
	 * @throws SQLException
	 */
	ResultSet allRooms() throws SQLException {
		PreparedStatement prepStatement = uplink.prepareStatement("SELECT * FROM hotel.rooms");
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}

	
	/**
	 * Creates a new booking entry, and the relating bindings of rooms to that booking.
	 * 
	 * @param customer
	 * @param rooms
	 * @param givenPrice
	 * @param amountPaid
	 * @param startDate
	 * @param endDate
	 * @throws SQLException
	 */
	int createBooking(int customer, ArrayList<Integer> rooms, int givenPrice, int amountPaid, String startDate, String endDate) throws SQLException {
		// convert dates from String to long to sql-date. EPOCH time in milliseconds.
		java.sql.Date start = new java.sql.Date(Long.parseLong(startDate));
		java.sql.Date end = new java.sql.Date(Long.parseLong(endDate));
		
		PreparedStatement prepStatement = uplink.prepareStatement("insert into hotel.bookings(customer, givenPrice, amountPaid, startDate, endDate, status) values (?, ?, ?, ?, ?, 'IN_PROGRESS');"
				+ " ");
		prepStatement.setInt(1, customer);
		prepStatement.setInt(2, givenPrice);
		prepStatement.setInt(3, amountPaid);
		prepStatement.setDate(4, start);
		prepStatement.setDate(5, end);
		prepStatement.executeUpdate();
		
		// /home/musho/Desktop/hotelserver.jar No value specified for parameter 6
		
		PreparedStatement idStatement = uplink.prepareStatement("SELECT LAST_INSERT_ID();");
		ResultSet svar = idStatement.executeQuery();
		
		svar.next();
		int bookingId = svar.getInt(1); //svar.getInt("LAST_INSERT_ID()");
		
		prepStatement = uplink.prepareStatement("insert into hotel.roomBinding(booking, room) values (?, ?)");
		for(int room : rooms) {
			prepStatement.setInt(1, bookingId);
			prepStatement.setInt(2, room);
			prepStatement.executeUpdate();
		}
		return bookingId;
	}
	
	/**
	 * Returns all bookings currently in the database.
	 * 
	 * @return
	 * @throws SQLException
	 */
	ResultSet allBookings() throws SQLException {
		PreparedStatement prepStatement = uplink.prepareStatement("SELECT * FROM hotel.bookings");
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}
	
	/**
	 * Queries for all bookings within a specific time frame.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	ResultSet bookingsInsideTimeframe(String startDate, String endDate) throws SQLException {
		java.sql.Date start = new java.sql.Date(Long.parseLong(startDate));
		java.sql.Date end = new java.sql.Date(Long.parseLong(endDate));
		PreparedStatement prepStatement = uplink.prepareStatement("SELECT * FROM hotel.bookings "
				+ "WHERE startDate < ? AND endDate > ?");
		prepStatement.setDate(1, end);
		prepStatement.setDate(2, start);
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}
	
	/**
	 * Queries for all bookings within a specific time frame and a specific hotel.
	 * 
	 * @param hotel
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	ResultSet bookingsForHotel(String hotel, String startDate, String endDate) throws SQLException {
		// get rooms in the hotelt

		PreparedStatement bfhPrepStatement = uplink.prepareStatement("SELECT * FROM hotel.rooms WHERE hotel=?");
		bfhPrepStatement.setString(1, hotel);
		ResultSet roomSet = bfhPrepStatement.executeQuery();
		ArrayList<Integer> rooms = new ArrayList<>();
		while (roomSet.next()) {
			rooms.add(roomSet.getInt("id"));
		}
		
		
		// get bindings between a room and a booking
		StringBuilder roomClause = new StringBuilder("SELECT * FROM hotel.roomBinding WHERE");
		roomClause.append(" room IN (");
		Iterator<Integer> roomIt = rooms.iterator();
		while(roomIt.hasNext()) {
			roomClause.append(roomIt.next());
			if (roomIt.hasNext()) roomClause.append(",");
		}
		roomClause.append(")");

		
		PreparedStatement bindingStatement = uplink.prepareStatement(roomClause.toString());
		ResultSet bindingSet = bindingStatement.executeQuery();
		ArrayList<Integer> bindings = new ArrayList<>();
		while(bindingSet.next()) {
			bindings.add(bindingSet.getInt("booking"));
		}
		
		if (bindings.size() <= 0) {
			return null;
		}

		// get all the affected bookings, within the correct time
		StringBuilder bookingClause = new StringBuilder("SELECT * FROM hotel.bookings WHERE startDate <= ? AND endDate >= ?");
		bookingClause.append(" AND id IN (");
		Iterator<Integer> bindingIt = bindings.iterator();
		while(bindingIt.hasNext()) {
			bookingClause.append(bindingIt.next());
			if (bindingIt.hasNext()) bookingClause.append(",");
		}
		bookingClause.append(")");
		
		java.sql.Date start = new java.sql.Date(Long.parseLong(startDate));
		java.sql.Date end = new java.sql.Date(Long.parseLong(endDate));
		PreparedStatement bfhBookingStatement = uplink.prepareStatement(bookingClause.toString());
		bfhBookingStatement.setDate(1, end);
		bfhBookingStatement.setDate(2, start);
		ResultSet bookings = bfhBookingStatement.executeQuery();
		return bookings;
	}
	
	/**
	 * Filters out a list of bookings to only those that reference a specific room.
	 * 
	 * @param room
	 * @param potentialBookings
	 * @return
	 * @throws SQLException
	 */
	ResultSet bindingsForRoom(int room, ArrayList<Integer> potentialBookings) throws SQLException {
		StringBuilder query = new StringBuilder("SELECT * FROM hotel.roomBinding WHERE room = ? AND booking IN (");
		Iterator<Integer> bookingIt = potentialBookings.iterator();
		while (bookingIt.hasNext()) {
			String i = String.valueOf(bookingIt.next());
			query.append(i);
			if (bookingIt.hasNext()) {
				query.append(", ");
			}
		}
		query.append(")");
		PreparedStatement prepStatement = uplink.prepareStatement(query.toString());
		prepStatement.setInt(1, room);
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}
	
	/**
	 * Tries to find rooms of a specific quality that are free to be reserved.
	 * 
	 * @param hotel
	 * @param bedType
	 * @param noSmoking
	 * @param adjacent
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	ResultSet findFreeRooms(String hotel, String bedType, boolean noSmoking, boolean adjacent, String startDate, String endDate) throws SQLException  {
		// 1. find bookings in the specific timeframe
		// 2. find rooms with the qualities we want that are NOT referenced by the bookings in 1.
		// 3. return a result set.
		ResultSet bookingSet = this.bookingsInsideTimeframe(startDate, endDate);
		ResultSet roomSet = roomBindingsForMultipleBookings(bookingSet);
		StringBuilder bookingClause = new StringBuilder("");
		if (roomSet != null) {
			Set<Integer> occupiedRooms = new HashSet<>();
			while(roomSet.next()) {
				occupiedRooms.add(roomSet.getInt("room"));
			}
			if (occupiedRooms.size() > 0) {
				Iterator<Integer> bookingIterator = occupiedRooms.iterator();
				bookingClause.append(" AND id NOT IN (");
				while(bookingIterator.hasNext()) {
					bookingClause.append(bookingIterator.next());
					if (bookingIterator.hasNext()) {
						bookingClause.append(", ");
					}
				}
				bookingClause.append(")");
			}
		}
		String adjSearch = ""; //" AND adjacentRoom IS NULL"; <---- NO, we want to allow it, right?
		if (adjacent) {
			adjSearch = " AND adjacentRoom IS NOT NULL";
		}
		String bedTypeSearch = "";
		if (bedType != null) {
			 bedTypeSearch = " AND bedType='"+bedType+"'";
		}
		PreparedStatement ffrRoomStatement = uplink.prepareStatement("SELECT * FROM hotel.rooms WHERE hotel=? AND noSmoking=?" + bedTypeSearch + adjSearch + bookingClause.toString());
		ffrRoomStatement.setString(1, hotel);
		ffrRoomStatement.setBoolean(2, noSmoking);
		
		ResultSet rooms = ffrRoomStatement.executeQuery();
		return rooms;
	}
	
	/**
	 * Find room-bindings for all the bookings in a ResultSet. 
	 * A "room-binding" is a tethering of a room to a specific booking.
	 * 
	 * @param bookings
	 * @return
	 * @throws SQLException
	 */
	ResultSet roomBindingsForMultipleBookings(ResultSet bookings) throws SQLException {
		Set<Integer> bookingIDs = new HashSet<Integer>();
		while (bookings.next()) {
			bookingIDs.add(bookings.getInt("id"));
		}
		if (bookingIDs.size() <= 0) {
			return null;
		}
		StringBuilder bindingQuery = new StringBuilder("SELECT * FROM hotel.roomBinding WHERE booking IN (");
		Iterator<Integer> bindingIterator = bookingIDs.iterator();
		while(bindingIterator.hasNext()) {
			bindingQuery.append(bindingIterator.next());
			if (bindingIterator.hasNext()) {
				bindingQuery.append(", ");
			}
		}
		bindingQuery.append(")");
		PreparedStatement rbfmbPrepStatement = uplink.prepareStatement(bindingQuery.toString());
		ResultSet bindings = rbfmbPrepStatement.executeQuery();
		return bindings;
	}
	
	/**
	 * Find a customer by a set of parameters.
	 * 
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	ResultSet findCustomer(HashMap<String, String> parameters) throws SQLException {
		StringBuilder query = new StringBuilder("SELECT * FROM hotel.customers WHERE ");
		Set<String> params = parameters.keySet();
		Iterator<String> paramIterator = params.iterator();
		while(paramIterator.hasNext()) {
			String p = paramIterator.next();
			query.append(p).append('=').append('\'').append(parameters.get(p)).append('\'');
			if (paramIterator.hasNext()) {
				query.append(" AND ");
			}
		}
		PreparedStatement fcPrepStatement = uplink.prepareStatement(query.toString());
		ResultSet svar = fcPrepStatement.executeQuery();
		return svar;
	}
	
	/**
	 * Return the booking with a specific ID.
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	ResultSet specificBooking(int id) throws SQLException {
		PreparedStatement sbPrepStatement = uplink.prepareStatement("SELECT * FROM hotel.bookings WHERE id=?");
	
		ResultSet svar = sbPrepStatement.executeQuery();
		return svar;
	}
	
	/**
	 * Find all bookings that belong to any in a list of integers or has a specific bookingID.
	 * 
	 * @param customers
	 * @param bookingId can be NULL, is ignored if so.
	 * @return
	 * @throws SQLException
	 */
	ResultSet bookingForCustomer(ArrayList<Integer> customers, Integer bookingId) throws SQLException {
		StringBuilder customerClause = new StringBuilder("");
				
		customerClause.append("customer in (");
		Iterator<Integer> customerIt = customers.iterator();
		while(customerIt.hasNext()) {
			customerClause.append(customerIt.next());
			if (customerIt.hasNext()) customerClause.append(", ");
		}
		customerClause.append(")");
		
		if (bookingId != null) {
			customerClause.append(" OR id=" + bookingId);
		}
		PreparedStatement bfcPrepStatement = uplink.prepareStatement("SELECT * FROM hotel.bookings WHERE " + customerClause.toString());
		
		if (bookingId != null) bfcPrepStatement.setInt(1, bookingId);
		ResultSet svar = bfcPrepStatement.executeQuery();
		return svar;
	}
	
	/**
	 * Update booking status, for example "checked in" to "checked out".
	 * 
	 * @param id
	 * @param status
	 * @throws SQLException
	 */
	void updateBookingStatus(int id, String status) throws SQLException {
		PreparedStatement ubsPrepStatement = uplink.prepareStatement("UPDATE hotel.bookings SET status=? WHERE id=?");
		ubsPrepStatement.setString(1, status);
		ubsPrepStatement.setInt(2,id);
		ubsPrepStatement.executeUpdate();
	}
	
	/**
	 * Update the amount that a customer has paid on a booking bill, and what they are expected to pay.
	 * 
	 * @param id
	 * @param amountPaid
	 * @param totalPayment
	 * @throws SQLException
	 */
	void updateBookingPayment(int id, double amountPaid, double totalPayment) throws SQLException {
		//query.updateBookingPayment(int id, int amountPaid, int totalPayment);
		PreparedStatement ubpPrepStatement = uplink.prepareStatement("UPDATE hotel.bookings SET amountPaid=?, givenPrice=? WHERE id=?");
		ubpPrepStatement.setDouble(1, amountPaid);
		ubpPrepStatement.setDouble(2, totalPayment);
		ubpPrepStatement.setInt(3, id);
		ubpPrepStatement.executeUpdate();
	}
	
	
	/**
	 * Select and return all the rooms that are booked in a single booking.
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	ResultSet roomsForBooking(int id) throws SQLException {
		PreparedStatement rfbPrepStatement = uplink.prepareStatement("SELECT * FROM hotel.roomBinding WHERE booking=?");
		rfbPrepStatement.setInt(1, id);
		ResultSet bindingSet = rfbPrepStatement.executeQuery();
		ArrayList<Integer> matchingRoomIds = new ArrayList<>();
		while(bindingSet.next()) {
			matchingRoomIds.add(bindingSet.getInt("room"));
		}
		StringBuilder query = new StringBuilder("SELECT * FROM hotel.rooms where id IN (");
		Iterator<Integer> roomIt = matchingRoomIds.iterator();
		while (roomIt.hasNext()) {
			int roomId = roomIt.next();
			query.append(roomId);
			if (roomIt.hasNext()) query.append(',');
		}
		query.append(')');
		PreparedStatement rfbPrepStatement2 = uplink.prepareStatement(query.toString());
		return rfbPrepStatement2.executeQuery();
	}
	
	/**
	 * Get the room with the specified ID.
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	ResultSet getRoom(int id) throws SQLException {
		PreparedStatement grPrepStatement = uplink.prepareStatement("SELECT * FROM hotel.rooms WHERE id=?");
		grPrepStatement.setInt(1, id);
		ResultSet room = grPrepStatement.executeQuery();
		return room;
	}
	
	/**
	 * Update the assigned customer on a booking.
	 * 
	 * @param bookingID
	 * @param customerID
	 * @throws SQLException
	 */
	void setCustomerOnBooking(int bookingID, int customerID) throws SQLException {
		PreparedStatement scobPrepStatement = uplink.prepareStatement("UPDATE hotel.bookings SET customer=? WHERE id=?");
		scobPrepStatement.setInt(1, customerID);
		scobPrepStatement.setInt(2, bookingID);
		scobPrepStatement.executeUpdate();
	}
	
	/**
	 * Checks how many of the supplied rooms are currently booked.
	 * @param rooms
	 * @return
	 * @throws SQLException
	 */
	boolean roomsUnbooked(ArrayList<Integer> rooms, String startDate, String endDate) throws SQLException {
		ResultSet swef = this.bookingsInsideTimeframe(startDate, endDate);
		ArrayList<Integer> bookingIds = new ArrayList<>();
		while(swef.next()) {
			bookingIds.add(swef.getInt("id"));
		}
		if (bookingIds.isEmpty()) return true;
		
		StringBuilder clause = new StringBuilder("SELECT count(*) as amount FROM hotel.roomBinding WHERE room IN (");
		Iterator<Integer> roomIt = rooms.iterator();
		while (roomIt.hasNext()) {
			clause.append(roomIt.next());
			if (roomIt.hasNext()) clause.append(",");
		}
		clause.append(") AND booking IN (");
		Iterator<Integer> bookingIt = bookingIds.iterator();
		while (bookingIt.hasNext()) {
			clause.append(bookingIt.next());
			if (bookingIt.hasNext()) clause.append(",");
		}
		clause.append(")");
		
		PreparedStatement roomPrep = uplink.prepareStatement(clause.toString());
		ResultSet roomResult = roomPrep.executeQuery();
		roomResult.next();
		int amount = roomResult.getInt("amount");
		return amount == 0;
	}
	
	
	
	// cleanup!!
	void removeUnrealizedBookings() throws SQLException {
		// make atomic!
		PreparedStatement rubPrepStatement = uplink.prepareStatement("SELECT * FROM hotel.bookings WHERE customer = -1 AND timestamp <= NOW() - INTERVAL 15 MINUTE");
		ResultSet bookings = rubPrepStatement.executeQuery();
		ArrayList<Integer> lameBookings = new ArrayList<>();
		while(bookings.next()) {
			lameBookings.add(bookings.getInt("id"));
		}
		if ( !lameBookings.isEmpty()) {
			StringBuilder liberatorOne = new StringBuilder("DELETE FROM hotel.roomBinding WHERE booking IN (");
			Iterator<Integer> bookingIt = lameBookings.iterator();
			while (bookingIt.hasNext()) {
				liberatorOne.append(bookingIt.next());
				if (bookingIt.hasNext()) liberatorOne.append(",");
			}
			liberatorOne.append(")");
			PreparedStatement libStatement = uplink.prepareStatement(liberatorOne.toString());
			libStatement.executeUpdate();
		}
		PreparedStatement libTwoStatement = uplink.prepareStatement("DELETE FROM hotel.bookings WHERE customer = -1 AND timestamp <= NOW() - INTERVAL 15 MINUTE");
		libTwoStatement.executeUpdate();
	}
	
	

}
