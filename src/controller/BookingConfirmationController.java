package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import client.model.Booking;
import client.model.Room;
import client.model.customer.RealCustomer;
import controller.ScreenController.Screen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class BookingConfirmationController implements Initializable{
	private CentralController centralController;
	private RealCustomer customer; 
	private String totalCost;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String roomNumber;
	private String powerLevel;
	private Booking bookingInProgress;
	
	private String completeString;
			
					
			
	
	
    @FXML
    private TextArea text_area;

    @FXML
    private Button cancel_button;

    @FXML
    private Button confirm_booking_button;

    @FXML
    void bookingButton(ActionEvent event) throws IOException {
    	centralController.finishBooking(customer);
        centralController.changeScreen(Screen.MAIN);
    }

    @FXML
    void cancelButton(ActionEvent event) throws IOException {
    	centralController.changeScreen(Screen.SEARCH_ROOMS);
    }

    public void setCentralController(CentralController centralController) {
        this.centralController = centralController;
    }
    public boolean hasNoCentralController() {
        return centralController == null;
    }
    public void setBookingInProgress(Booking booking) {
    	bookingInProgress = booking;
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		Platform.runLater(()->
        {
        	bookingInProgress = centralController.getBookingInProgress();
    		customer = (RealCustomer)bookingInProgress.getCustomer();
        	totalCost = ""+bookingInProgress.getPrice();
        	firstName = customer.getFirstName();
        	lastName = customer.getLastName();
        	phoneNumber = customer.getTelephoneNumber();
        	Iterator<Room> itr = bookingInProgress.getAllRooms();
        	StringBuilder sb = new StringBuilder();
        	while (itr.hasNext()) {
        		sb.append(itr.next().getRoomNumber() + ", ");
        	}
        	roomNumber = sb.toString();
        	powerLevel = ""+customer.getPowerLevel();
        	
        	completeString = 
        			"Summary of the Booking \n"
        			+ "Name: "+firstName+" "+lastName +"\n"+
        			"Phone number: "+phoneNumber + "\n"+
        			"Rooms: "+roomNumber+"\n"+
        			"Power level: "+powerLevel+"\n"+
        			"\n"+
        			"\n"+
        			"Total cost: "+totalCost;
        	
        	text_area.setText(completeString);
        });
	}

}
