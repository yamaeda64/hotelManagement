package controller;

import client.model.Booking;
import client.model.Hotel;
import client.model.ModelAccess;
import client.model.Room;
import client.model.customer.RealCustomer;
import controller.ScreenController.Screen;
import controller.supportClasses.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class CentralController
{
	
	private ScreenController screenController;
	private StageManager stageM;
	private Stage stage;
	private Scene scene;
	private ModelAccess modelAccess;
	private Hotel location;
	private RoomSearch lastRoomSearch;
	private Booking inProgressBooking;
	
	private BookingSearch bookingSearch;
	
	private ServerMessage serverMessageConstructor;
	private ServerCommunicator serverCommunicator;
	
	private ArrayList<Room> availableRooms;
	private ProxyCustomer tempProxyCustomer;
	
	public CentralController(Stage stage) throws IOException
	{
		//setup();
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
	
	public StageManager getStageManager()
	{
		return stageM;
	}
	
	public void changeScreen(Screen screen) throws IOException
	{
		screenController.setScreen(screen);
	}
	
	
	/*
	public void setup() throws IOException {
	        this.stage= new Stage();
	        stage.setTitle("Hotel System");
	        stage.setMinWidth(600.0);
	        Parent root= FXMLLoader.load(ScreenController.class.getResource("/fxml/Main_menu.fxml"));

	        this.scene = new Scene(root);
	        scene.getStylesheets().add("css/menu_items.css"); // css for design
	        stage.setScene(scene);
	        System.out.print(Screen.MAIN.getResourceLocation());
	        stageM.setPaneFragment(root);
	        //screenC.setScreen(Screen.MAIN);
	        stage.show();
	}
	
	*/
	public Iterator<Booking> getBookings()
	{
		return modelAccess.getAllBookings();
	}
	
	public Iterator<Booking> getBookings(Hotel hotel, LocalDate date)
	{
		serverCommunicator.sendToServer(serverMessageConstructor.getBookingsOfDate(hotel, date));
		//TODO modelAccess.updateBookings(hotel, date);
		return modelAccess.getAllBookings();
	}
	
	/*public Iterator<Room> getRooms()
	{
		return modelAccess.getAllRooms();
    }*/

	public void updateBookingInProgress(RealCustomer c) {
		inProgressBooking.setCustomer(c);
	}
	public Booking getBookingInProgress() {
		return inProgressBooking;
	}

	
	
	public void setLocation(Hotel hotel)
	{
		this.location = hotel;
	}
	
	public Hotel getLocation()
	{
		return location;
	}
	
	public void updateModel(RoomSearch currentSearch)
	{
		this.lastRoomSearch = currentSearch;
		
		serverCommunicator.sendToServer(serverMessageConstructor.getRoomsFromSearch(currentSearch));
		//modelAccess.updateBookings(currentSearch);
		
	}
	
	public void updateModel(BookingSearch bookingsearch)
	{
		this.bookingSearch = bookingsearch;
		serverCommunicator.sendToServer(serverMessageConstructor.getBookingsFromSearch(bookingsearch));
	}
	
	public RoomSearch getLastRoomSearch()
	{
		return lastRoomSearch;
	}
	
	public void createNewBooking(Booking booking)
	{
		this.inProgressBooking = booking;
		serverCommunicator.sendToServer(serverMessageConstructor.createBooking(booking));
	}
	
	public void setInProgressBookingID(int id)
	{
		this.inProgressBooking.setId(id);
	}
	
	public void realizeBooking(RealCustomer customer)
	{
		inProgressBooking.setCustomer(customer);
		inProgressBooking.setStatus(Booking.BookingStatus.BOOKED);
		
		serverCommunicator.sendToServer(serverMessageConstructor.createBooking(inProgressBooking));
		
	}
	
	public void finalizeBooking(double finalPrice)
	{
		inProgressBooking.setPrice(finalPrice);
		
		serverCommunicator.sendToServer(serverMessageConstructor.finalizeBooking(inProgressBooking));
	}
	
	public BookingSearch getBookingSearch()
	{
		return bookingSearch;
	}
	
	public void changeBookingStatus(Booking booking, Booking.BookingStatus bookingStatus)
	{
		serverCommunicator.sendToServer(serverMessageConstructor.setStatus(booking, bookingStatus));
	}
	
	public void changePayedBookingAmount(Booking booking, double amountPaid, double totalCost)
	{
		serverCommunicator.sendToServer(serverMessageConstructor.setExpence(booking, amountPaid, totalCost));
	}
	
	public void showError(String errorHeader, String errorMsg)
	{
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(errorHeader);
		alert.setContentText(errorMsg);
		alert.show();
	}
	
	public void addBooking(Booking booking)
	{
		modelAccess.addBooking(booking);
	}
	
	public void addRoom(Room room)
	{
		modelAccess.addRoom(room);
	}
	
	public void clearBookings()
	{
		modelAccess.clearBookings();
	}
	
	public void addAvailableRoom(Room room)
	{
		System.out.println("added available room");
		this.availableRooms.add(room);
	}
	
	public void clearAvailableRooms()
	{
		availableRooms.clear();
	}
	
	public Iterator<Room> getAvailableRooms()
	{
		return availableRooms.iterator();
	}
	
	public Room getRoomByID(int id)
	{
		return modelAccess.getRoomByID(id);
	}
	
	public void getRealCustomer(ProxyCustomer customer)
	{
		serverCommunicator.sendToServer(serverMessageConstructor.getCustomerDetails(customer));
	}
	
	public void setCustomerToProxy(RealCustomer customer)
	{
		this.tempProxyCustomer.addRealCustomer(customer);
	}
}
