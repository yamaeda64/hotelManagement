package client.controller;

import client.model.Booking;
import client.model.Hotel;
import client.model.ModelAccess;
import client.model.Room;
import client.model.customer.RealCustomer;
import client.controller.ScreenController.Screen;
import client.controller.supportClasses.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The FacadeController is a backbone class of the client side of Hotel Management.
 * It makes a single point of contact for communication between the classes and stores
 * many temp objects lite BookingSearch, RoomSearch, proxyCustomer .
 */
public class FacadeController
{
	private ScreenController screenController;
	private ModelAccess modelAccess;
	private Hotel location;
	private RoomSearch lastRoomSearch;
	private Booking inProgressBooking;
	private BookingSearch bookingSearch;
	private ServerMessage serverMessageConstructor;
	private ServerCommunicator serverCommunicator;
	private ArrayList<Room> availableRooms;
	private ProxyCustomer tempProxyCustomer;
	
	/**
	 * @param stage A stage for the application.
	 * @throws IOException
	 */
	public FacadeController(Stage stage) throws IOException
	{
		screenController = new ScreenController(stage, this);
		modelAccess = new ModelAccess();
		location = Hotel.VAXJO;    //TODO Default, should maybe come from server or save default so not kalmar has vaxjo as default
		serverMessageConstructor = new ServerMessage(this);
		serverCommunicator = new ServerCommunicator(this);
		serverCommunicator.sendToServer(serverMessageConstructor.getAllRooms());
		availableRooms = new ArrayList<>();
	}
	
	public ScreenController getScreenController()
	{
		return screenController;
	}
	
	
	public void changeScreen(Screen screen) throws IOException
	{
		screenController.setScreen(screen);
	}
	
	/**
	 * Get all the bookings currently stored in the model
	 * @return Iterator of type Booking
	 */
	public Iterator<Booking> getBookings()
	{
		return modelAccess.getAllBookings();
	}
	
	/**
	 * Gets all bookings that overlaps a certain date for one of the hotels.
	 * Pass on the call to the server and return the bookings once the model has been updated.
	 * @param hotel the location requested
	 * @param date the date requested
	 * @return bookings that match the inputs
	 */
	public Iterator<Booking> getBookings(Hotel hotel, LocalDate date)
	{
		serverCommunicator.sendToServer(serverMessageConstructor.getBookingsOfDate(hotel, date));
		return modelAccess.getAllBookings();
	}
	
	/**
	 * Adds a Customer to the booking in progress
	 * @param c
	 */
	public void updateBookingInProgress(RealCustomer c) {
		inProgressBooking.setCustomer(c);
	}
	
	/**
	 * Simple getter for the booking in progress.
	 * @return	the booking in progress
	 */
	public Booking getBookingInProgress() {
		return inProgressBooking;
	}
	
	/**
	 * Sets the default hotel of the application. This is only stored in memory which means it will only be
	 * remembered as long as the application is still running.
	 * @param hotel which location to be set as input
	 */
	public void setLocation(Hotel hotel)
	{
		this.location = hotel;
	}
	
	/**
	 * Get the current default hotel. Windows opening which has a choice of hotel will use this
	 * so that the default hotel is the initial one for i.e. create new booking.
	 * @return the default location
	 */
	public Hotel getLocation()
	{
		return location;
	}
	
	/**
	 * Updating the local RoomSearch object before it is made to a server request.
	 * @param currentSearch the input of the search
	 */
	public void updateModel(RoomSearch currentSearch)
	{
		this.lastRoomSearch = currentSearch;
		
		serverCommunicator.sendToServer(serverMessageConstructor.getRoomsFromSearch(currentSearch));
	}
	/**
	 * Updating the local BookingSearch object before it is turned into a server request.
	 * @param bookingsearch
	 */
	public void updateModel(BookingSearch bookingsearch)
	{
		this.bookingSearch = bookingsearch;
		serverCommunicator.sendToServer(serverMessageConstructor.getBookingsFromSearch(bookingsearch));
	}
	
	/**
	 * Getter for the room search
	 * @return
	 */
	public RoomSearch getLastRoomSearch()
	{
		return lastRoomSearch;
	}
	
	/**
	 * An initial method when creating a new booking.
	 * This is step 1 one creating new bookings
	 * @param booking
	 */
	public void createNewBooking(Booking booking)
	{
		this.inProgressBooking = booking;
		serverCommunicator.sendToServer(serverMessageConstructor.createBooking(booking));
	}
	
	/**
	 * After a new booking has been sent to server the server will reply with a bookinging id.
	 * This stores in the inProgressBooking which is set by this function
	 * This is step 2 of creating a new booking
	 * @param id the bookingID for further communication with the database
	 */
	public void setInProgressBookingID(int id)
	{
		this.inProgressBooking.setId(id);
	}
	
	/**
	 * Step 3 in creating a new booking is to realize it, by adding a cusomer to the booking.
	 * @param customer
	 */
	public void realizeBooking(RealCustomer customer)
	{
		inProgressBooking.setCustomer(customer);
		serverCommunicator.sendToServer(serverMessageConstructor.createBooking(inProgressBooking));
	}
	
	/**
	 * Step 4 in creating the booking, the server responds with a price after step 3, and this is stored
	 * to a inProgressBooking by this booking
	 * @param price the price received from server
	 */
	public void setInProgressBookingPrice(double price)
	{
		this.inProgressBooking.setGivenPrice(price);
	}
	/**
	 * Step 5 which is the final step in creating a new booking is to
	 * finalize it by sending a price to the server, either same as received from server,
	 * or an overrided one by the users choice.
	 * @param finalPrice
	 */
	public void finalizeBooking(double finalPrice)
	{
		inProgressBooking.setGivenPrice(finalPrice);
		serverCommunicator.sendToServer(serverMessageConstructor.finalizeBooking(inProgressBooking));
	}
	
	/**
	 * A simple get method to get a booking search
	 * @return the current booking search
	 */
	public BookingSearch getBookingSearch()
	{
		return bookingSearch;
	}
	
	/**
	 * Sends command to server to change the bookingStatus of booking.
	 * @param booking The booking to be changed
	 * @param bookingStatus the new BookingStatus
	 */
	public void changeBookingStatus(Booking booking, Booking.BookingStatus bookingStatus)
	{
		serverCommunicator.sendToServer(serverMessageConstructor.setStatus(booking, bookingStatus));
	}
	
	/**
	 * Change how much a booking has been payed
	 * @param booking Booking, the booking that should be affected
	 * @param amountPaid the total amount paid by the customer
	 * @param totalCost the total cost of the booking
	 */
	public void changePayedBookingAmount(Booking booking, double amountPaid, double totalCost)
	{
		serverCommunicator.sendToServer(serverMessageConstructor.setExpence(booking, amountPaid, totalCost));
	}
	
	/**
	 * A centralized method to display a error message
	 * @param errorHeader The header of the error message
	 * @param errorMsg the more detailed messge
	 */
	public void showError(String errorHeader, String errorMsg)
	{
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(errorHeader);
		alert.setContentText(errorMsg);
		alert.show();
	}
	
	/**
	 * Add a booking, to the model
	 * @param booking booking to be added
	 */
	public void addBooking(Booking booking)
	{
		modelAccess.addBooking(booking);
	}
	
	/**
	 * Add a room to the model
	 * @param room
	 */
	public void addRoom(Room room)
	{
		modelAccess.addRoom(room);
	}
	
	/**
	 * Clears the bookings from the model before populating new booking when updating model.
	 */
	public void clearBookings()
	{
		modelAccess.clearBookings();
	}
	
	/**
	 * Add a room to a list of available rooms received from the server after a search.
	 * @param room
	 */
	public void addAvailableRoom(Room room)
	{
		this.availableRooms.add(room);
	}
	
	/**
	 * Clears the available rooms before populating after a new search
	 */
	public void clearAvailableRooms()
	{
		availableRooms.clear();
	}
	
	/**
	 * Get the available rooms
	 * @return returns an iterator or type Room
	 */
	public Iterator<Room> getAvailableRooms()
	{
		return availableRooms.iterator();
	}
	
	/**
	 * Request a room from an ID
	 * @param id the id of the requested room
	 * @return a Room
	 */
	public Room getRoomByID(int id)
	{
		return modelAccess.getRoomByID(id);
	}
	
	/**
	 * Ask the server for full customer details, used by the Proxy Customer.
	 * @param customer
	 */
	public void getRealCustomer(ProxyCustomer customer)
	{
		this.tempProxyCustomer = customer;
		serverCommunicator.sendToServer(serverMessageConstructor.getCustomerDetails(customer));
	}
	
	/**
	 * method to map the received RealCustomer to the proxycustomer
	 * used when requesting the customer details
	 * @param customer
	 */
	public void setCustomerToProxy(RealCustomer customer)
	{
		tempProxyCustomer.addRealCustomer(customer);
	}
	
	
}
