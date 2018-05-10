package controller;

import client.model.Booking;
import client.model.Hotel;
import client.model.ModelAccess;
import client.model.Room;
import client.model.customer.RealCustomer;
import controller.ScreenController.Screen;
import controller.supportClasses.BookingSearch;
import controller.supportClasses.RoomSearch;
import controller.supportClasses.ServerCommunicator;
import controller.supportClasses.ServerMessage;
import javafx.scene.Scene;
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
	
	public CentralController(Stage stage) throws IOException
	{
		//setup();
		screenController = new ScreenController(stage, this);
		modelAccess = new ModelAccess();
		location = Hotel.VAXJO;    //TODO Default, should maybe come from server or save default so not kalmar has vaxjo as default
		serverMessageConstructor = new ServerMessage();
		serverCommunicator = new ServerCommunicator(this);
		serverCommunicator.sendToServer(serverMessageConstructor.getAllRooms());
		
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
	
	public void updateModel(BookingSearch booking)
	{
		this.bookingSearch = booking;
	}
	
	public RoomSearch getLastRoomSearch()
	{
		return lastRoomSearch;
	}
	
	public void sendInProgressBooking(Booking booking)
	{
		this.inProgressBooking = booking;
		serverCommunicator.sendToServer(serverMessageConstructor.createBooking(booking));
		serverCommunicator.receiveMessageFromServer();
		// Todo, receive the reply and add the int as bookingID
	}
	
	public void finishBooking(RealCustomer customer)
	{
		inProgressBooking.setCustomer(customer);
		inProgressBooking.setStatus(Booking.BookingStatus.BOOKED);
		
		serverCommunicator.sendToServer(serverMessageConstructor.createBooking(inProgressBooking));
		// TODO, should this be updateBooking???
	}
	
	public BookingSearch getBookingSearch()
	{
		return bookingSearch;
	}
	
	public void changeBookingStatus(Booking booking, Booking.BookingStatus bookingStatus)
	{
		serverCommunicator.sendToServer(serverMessageConstructor.setStatus(booking, bookingStatus));
	}
	
	public void changePayedBookingAmount(Booking booking, double amountPayed, double totalCost)
	{
		serverCommunicator.sendToServer(serverMessageConstructor.setExpence(booking, amountPayed, totalCost));
	}
	
	public void showError(String errorMsg)
	{
		
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
}
