package server.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SqlQueries {
	private final String db_url = "jdbc:mysql://" + SqlLogin.db_hostname + ":" + SqlLogin.db_port + "/" + SqlLogin.db_dbname + "?user=" + SqlLogin.db_username + "&password=" + SqlLogin.db_password;
	private Connection uplink;
	public SqlQueries() throws ClassNotFoundException, SQLException {
		// creates a new instance of the driver, which loads itself into the place where
		// it needs to be --> we dont need to save it or anything ourselves
		Class.forName("com.mysql.jdbc.Driver");
		this.uplink = DriverManager.getConnection(db_url);
	}
	public void close() throws SQLException {
		uplink.close();
	}
	
	/*
	 *  Customers
	 */
	
	public int createCustomer(String firstName, String lastName, String telephone, String idNumber, String address, String creditCard, int powerLevel, String passportNumber) throws SQLException {
		PreparedStatement prepStatement = uplink.prepareStatement("insert into hotel.customers(firstName, lastName, telephone, idNumber, address, creditCard, powerLevel) values (?, ?, ?, ?, ?, ?, ?); SELECT LAST_INSERT_ID();");
		prepStatement.setString(1, firstName);
		prepStatement.setString(2, lastName);
		prepStatement.setString(3, telephone);
		prepStatement.setString(4, idNumber);
		prepStatement.setString(5, address);
		prepStatement.setString(6, creditCard);
		prepStatement.setInt(7, powerLevel);
		prepStatement.setString(8, passportNumber);
		ResultSet cre8ed = prepStatement.executeQuery();
		return cre8ed.getInt(0);
	}
	
	public ResultSet searchCustomer(String firstName, String lastName, String telephone, String idNumber, String address, String creditCard, int powerLevel) throws SQLException {
		PreparedStatement prepStatement = uplink.prepareStatement("SELECT * FROM hotel.customers WHERE "
				+ "firstName = '?' AND lastName = '?'");
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}
	
	public ResultSet searchCustomer(int id) throws SQLException {
		PreparedStatement prepStatement = uplink.prepareStatement("SELECT * FROM hotel.customers WHERE "
				+ "id = ?");
		prepStatement.setInt(1, id);
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}

	public ResultSet allCustomers() throws SQLException {
		PreparedStatement prepStatement = uplink.prepareStatement("SELECT * FROM hotel.customers");
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}
	
	public ResultSet multipleCustomers(ArrayList<Integer> which) throws SQLException {
		// build the hecking query
		StringBuilder query = new StringBuilder("SELECT * FROM hotel.customers WHERE id IN (");
		Iterator<Integer> idIterator = which.iterator();
		while (idIterator.hasNext()) {
			String i = String.valueOf(idIterator.next());
			//System.out.print(i);
			query.append(i);
			if (idIterator.hasNext()) {
				query.append(", ");
			}
		}
		query.append(")");
		
		// search
		//System.out.println(query);
		PreparedStatement prepStatement = uplink.prepareStatement(query.toString());
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}

	/*
	 *  Rooms
	 */
	
	public void createRoom(int hotel, int number, int size, int quality, int bedSize, int bedAmount, int status, int smokeFree, String adjacentRooms, int view) throws SQLException {
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
	
	public ResultSet searchRoom(int hotel, int size, int quality, int bedSize, int bedAmount, int status, int smokeFree, int[] adjacentRooms, int view) throws SQLException {
		PreparedStatement prepStatement = uplink.prepareStatement("SELECT * FROM hotel.rooms WHERE "
				+ "hotel='?' AND size='?' AND quality = '?' AND bedSize = '?' AND bedAmount = '?' AND status = '?' AND smokeFree = '?' AND adjacentRooms = "
				+ "'?' AND view = '?'");
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}

	public ResultSet allRooms() throws SQLException {
		PreparedStatement prepStatement = uplink.prepareStatement("SELECT * FROM hotel.rooms");
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}

	/*
	 *  Bookings
	 */
	
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
	/* UNTESTED!!!!!!!!!!!!!!!!! */
	/* UNTESTED!!!!!!!!!!!!!!!!! */
	public void createBooking(int customer, ArrayList<Integer> rooms, int givenPrice, int amountPaid, String startDate, String endDate) throws SQLException {
		// convert dates from String to long to sql-date. EPOCH time in milliseconds.
		java.sql.Date start = new java.sql.Date(Long.parseLong(startDate));
		java.sql.Date end = new java.sql.Date(Long.parseLong(startDate));
		
		PreparedStatement prepStatement = uplink.prepareStatement("insert into hotel.bookings(customer, givenPrice, amountPaid, startDate, endDate, status) values (?, ?, ?, ?, ?, ?, 'BOOKED');"
				+ " SELECT LAST_INSERT_ID();");
		prepStatement.setInt(1, customer);
		prepStatement.setInt(2, givenPrice);
		prepStatement.setInt(3, amountPaid);
		prepStatement.setDate(4, start);
		prepStatement.setDate(5, end);
		ResultSet svar = prepStatement.executeQuery();
		
		svar.next();
		int bookingId = svar.getInt(0);
		
		prepStatement = uplink.prepareStatement("insert into hotel.roomBinding(booking, room) values (?, ?)");
		for(int room : rooms) {
			prepStatement.setInt(1, bookingId);
			prepStatement.setInt(2, room);
			prepStatement.executeQuery();
		}
	}
	
	public ResultSet allBookings() throws SQLException {
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
	public ResultSet bookingsInsideTimeframe(String startDate, String endDate) throws SQLException {
		java.sql.Date start = new java.sql.Date(Long.parseLong(startDate));
		java.sql.Date end = new java.sql.Date(Long.parseLong(endDate));
		PreparedStatement prepStatement = uplink.prepareStatement("SELECT * FROM hotel.bookings "
				+ "WHERE startDate < ? AND endDate > ?");
		prepStatement.setDate(1, end);
		prepStatement.setDate(2, start);
		ResultSet svar = prepStatement.executeQuery();
		return svar;
	}
	
	public ResultSet bookingsForHotel(String hotel, String startDate, String endDate) throws SQLException {
		PreparedStatement bfhPrepStatement = uplink.prepareStatement("SELECT * FROM hotel.rooms WHERE hotel=?");
		bfhPrepStatement.setString(1, hotel);
		ResultSet roomSet = bfhPrepStatement.executeQuery();
		ArrayList<Integer> rooms = new ArrayList<>();
		while (roomSet.next()) {
			rooms.add(roomSet.getInt("id"));
		}
		StringBuilder roomClause = new StringBuilder("SELECT * FROM hotel.bookings WHERE startDate < ? AND endDate > ?");
		roomClause.append(" AND room IN (");
		Iterator<Integer> roomIt = rooms.iterator();
		while(roomIt.hasNext()) {
			roomClause.append(roomIt.next());
			if (roomIt.hasNext()) roomClause.append(",");
		}
		roomClause.append(")");
		PreparedStatement bfhBookingStatement = uplink.prepareStatement(roomClause.toString());
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
	public ResultSet bindingsForRoom(int room, ArrayList<Integer> potentialBookings) throws SQLException {
		StringBuilder query = new StringBuilder("SELECT * FROM hotel.roomBinding WHERE room = ? AND booking IN (");
		Iterator<Integer> bookingIt = potentialBookings.iterator();
		while (bookingIt.hasNext()) {
			String i = String.valueOf(bookingIt.next());
			//System.out.print(i);
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
	
	public ResultSet findFreeRooms(String hotel, String bedType, boolean noSmoking, boolean adjacent, String startDate, String endDate) throws SQLException  {
		// 1. hitta bokningar under en viss tid.
		// 2. hitta rum som matchar alla kriterier samt id NOT IN (1.)
		// 3. returna RESULT SET så får vi välja om vi vill packa eller bara ha en
		// lista.
		ResultSet bookingSet = this.bookingsInsideTimeframe(startDate, endDate);
		ResultSet roomSet = roomBindingsForMultipleBookings(bookingSet);
		Set<Integer> occupiedRooms = new HashSet<>();
		while(roomSet.next()) {
			occupiedRooms.add(roomSet.getInt("room"));
		}
		StringBuilder bookingClause = new StringBuilder("");
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

	public ResultSet roomBindingsForMultipleBookings(ResultSet bookings) throws SQLException {
		Set<Integer> bookingIDs = new HashSet<Integer>();
		while (bookings.next()) {
			bookingIDs.add(bookings.getInt("id"));
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
	
//	public ArrayList<Integer> findFreeRooms(String hotel, String bedType, boolean noSmoking, boolean adjacent, String startDate, String endDate) throws SQLException  {
//		//1. find rooms that are matching
//		// TODO: lägg till view etc här
//		String adjSearch = " AND adjacentRoom IS NULL";
//		if (adjacent) {
//			adjSearch = " AND adjacentRoom IS NOT NULL";
//		}
//		PreparedStatement ffrRoomStatement = uplink.prepareStatement("SELECT * FROM hotel.rooms WHERE hotel='?' AND bedType='?' AND noSmoking='?'" + adjSearch);
//
//		// 2. find bokings for a certain range
//		java.sql.Date start = new java.sql.Date(Long.parseLong(startDate));
//		java.sql.Date end = new java.sql.Date(Long.parseLong(startDate));
//		PreparedStatement ffrBookingStatement = uplink.prepareStatement("SELECT * FROM hotel.bookings "
//				+ "WHERE startDate < ? AND endDate > ?");
//		ffrBookingStatement.setDate(1, start);
//		ffrBookingStatement.setDate(2, end);
//		
//		// 3. cross-reference :(
//		ResultSet applicableRooms = ffrRoomStatement.executeQuery();
//		ResultSet applicableBookings = ffrBookingStatement.executeQuery();
//		StringBuilder roomString = new StringBuilder("(");
//		int roomCount = 0;
//		while (applicableRooms.next()) {
//			String i = applicableRooms.getString("id");
//			roomString.append(i);
//			if (!applicableRooms.isLast()) {
//				roomString.append(", ");
//			}
//			roomCount++;
//		}
//		StringBuilder bookingString = new StringBuilder("(");
//		int bookingCount = 0;
//		while (applicableBookings.next()) {
//			String i = applicableRooms.getString("id");
//			roomString.append(i);
//			if (!applicableRooms.isLast()) {
//				roomString.append(", ");
//			}
//			bookingCount++;
//		}
//		ArrayList<Integer> bookableRooms = new ArrayList<>();
//		if (bookingCount > 0 && roomCount > 0) {
//			PreparedStatement ffrFreeRoomsStatement = uplink.prepareStatement("SELECT * FROM hotel.roomBinding WHERE booking IN " + bookingString + " AND room IN " + roomString);
//			while(applicableRooms.next()) {
//				bookableRooms.add(applicableRooms.getInt("room"));
//			}
//		}
//		bookableRooms.add(-1);
//		return bookableRooms;
//	}
	
	
	public ResultSet findCustomer(HashMap<String, String> parameters) throws SQLException {
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
	
	public ResultSet specificBooking(int id) throws SQLException {
		PreparedStatement sbPrepStatement = uplink.prepareStatement("SELECT * FROM hotel.bookings WHERE id=?");
	
		ResultSet svar = sbPrepStatement.executeQuery();
		return svar;
	}
	
	public ResultSet bookingForCustomer(ArrayList<Integer> customers, Integer bookingId) throws SQLException {
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
		bfcPrepStatement.setInt(1, bookingId);
		ResultSet svar = bfcPrepStatement.executeQuery();
		return svar;
	}
	
	public void updateBookingStatus(int id, String status) throws SQLException {
		PreparedStatement ubsPrepStatement = uplink.prepareStatement("UPDATE hotel.bokings SET status='?' WHERE id=?");
		ubsPrepStatement.setString(1, status);
		ubsPrepStatement.setInt(2,id);
		ubsPrepStatement.executeQuery();
	}
	public void updateBookingPayment(int id, int amountPaid, int totalPayment) throws SQLException {
		//query.updateBookingPayment(int id, int amountPaid, int totalPayment);
		PreparedStatement ubpPrepStatement = uplink.prepareStatement("UPDATE hotel.bookings SET amountPaid=?, givenPrice=? WHERE id=?");
		ubpPrepStatement.setInt(1, amountPaid);
		ubpPrepStatement.setInt(2, totalPayment);
		ubpPrepStatement.setInt(3, id);
		ubpPrepStatement.executeQuery();
	}
	
	
	
	public ResultSet roomsForBooking(int id) throws SQLException {
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
	
	public ResultSet getRoom(int id) throws SQLException {
		PreparedStatement grPrepStatement = uplink.prepareStatement("SELECT * FROM hotel.rooms WHERE id=?");
		grPrepStatement.setInt(1, id);
		ResultSet room = grPrepStatement.executeQuery();
		return room;
	}

}
