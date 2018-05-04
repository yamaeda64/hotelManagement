package controller;

import client.model.Booking;
import client.model.Hotel;
import client.model.ModelAccess;
import controller.ScreenController.Screen;
import controller.supportClasses.BookingSearch;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;

public class CentralController
{
	private ScreenController screenController;
	private StageManager stageM;
	private Stage stage;
	private Scene scene;
	private ModelAccess modelAccess;
	private Hotel location;
	private BookingSearch bookingSearch;
	
	
	
	public CentralController(Stage stage) throws IOException {
		//setup();
		screenController = new ScreenController(stage,this);
		modelAccess = new ModelAccess();
		location = Hotel.VAXJO; 	//TODO Default, should maybe come from server or save default so not kalmar has vaxjo as default
	}
	
	public ScreenController getScreenController() {
		return screenController;
	}
	public StageManager getStageManager() {
		return stageM;
	}
	public void changeScreen(Screen screen) throws IOException {
		screenController.setScreen(screen);
	}
	public BookingSearch getBookingSearch() {
		return bookingSearch;
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
	
	public Iterator<Booking> getBookings(Hotel hotel, LocalDate value)
	{
		modelAccess.updateBookings(hotel, value);
		return modelAccess.getAllBookings();
	}
	
	public void setLocation(Hotel hotel)
	{
		this.location = hotel;
	}
	public Hotel getLocation()
	{
		return location;
	}
	public void updateModel(BookingSearch booking) {
		bookingSearch = booking;
	}
}
