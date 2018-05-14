package client.controller;

import client.model.Booking;
import client.model.Room;
import client.model.customer.RealCustomer;
import client.controller.ScreenController.Screen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * A client.controller class for the BookingConfirmationWindow
 */
public class BookingConfirmationController implements Initializable{
	private FacadeController facadeController;
	private RealCustomer customer; 
	private String totalCost;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String roomNumber;
	private String powerLevel;
	private String checkIn;
	private String checkOut;
	private Booking bookingInProgress;
	private boolean priceOverrided = false;
	private String completeString;




	@FXML
	private TextArea text_area;

	@FXML
	private Button cancel_button;

	@FXML
	private Button confirm_booking_button;

	@FXML
	private Button override_price_button;

	@FXML
	void override_price_button(ActionEvent event) {

		TextInputDialog dialog = new TextInputDialog("Price");
		dialog.setTitle("Override price");
		dialog.setHeaderText("Override price");
		dialog.setContentText("Enter the new price.");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			String price = result.get().replaceAll(",", ".");
			double newPrice = 0;
			try {
			newPrice = Double.parseDouble(price);
			totalCost = result.get();
			priceOverrided = true;
			updateCompleteString();
			text_area.setText(completeString);
			bookingInProgress.setGivenPrice(newPrice);
			}
			catch (NumberFormatException e) {
				facadeController.showError("Faulty input", "Digits and one dot only");
			}
			
		}

	}



	@FXML
	void bookingButton(ActionEvent event) throws IOException {
		
		facadeController.finalizeBooking(bookingInProgress.getGivenPrice());
		facadeController.changeScreen(Screen.MAIN);
	}

	@FXML
	void cancelButton(ActionEvent event) throws IOException {
		facadeController.changeScreen(Screen.SEARCH_ROOMS);
	}

	public void setFacadeController(FacadeController facadeController) {
		this.facadeController = facadeController;
	}
	public boolean hasNoCentralController() {
		return facadeController == null;
	}
	//public void setBookingInProgress(Booking booking) {
	//	bookingInProgress = booking;
	//}
	public void updateCompleteString() {
		completeString = 
				"Summary of the Booking \n"
						+ "Name: "+firstName+" "+lastName +"\n"+
						"Phone number: "+phoneNumber + "\n"+
						"Rooms: "+roomNumber+"\n"+
						"Power level: "+powerLevel+"\n"+
						"Check In:  " + checkIn + "\n"+
						"Check Out:  " + checkOut + "\n" +
						"\n"+
						"\n"+
						"Total cost: "+totalCost + "\n"+
						"Price overrided: "+priceOverrided;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		text_area.setEditable(false);
		
		// Platform.runLater makes these line wait until the facadecontroller is read into this class.
		Platform.runLater(()->
		{
			bookingInProgress = facadeController.getBookingInProgress();
			customer = (RealCustomer)bookingInProgress.getCustomer();
			totalCost = ""+bookingInProgress.getGivenPrice();
			firstName = customer.getFirstName();
			lastName = customer.getLastName();
			phoneNumber = customer.getTelephoneNumber();
			Iterator<Room> itr = bookingInProgress.getAllRooms();
			StringBuilder sb = new StringBuilder();
			while (itr.hasNext()) {
				sb.append(itr.next().getRoomNumber());
				if(itr.hasNext())
				{
					sb.append(", ");
				}
			}
			roomNumber = sb.toString();
			powerLevel = ""+customer.getPowerLevel();
			checkIn = bookingInProgress.getStartDate().format(DateTimeFormatter.ISO_DATE).toString();
			checkOut = bookingInProgress.getEndDate().format(DateTimeFormatter.ISO_DATE).toString();
			updateCompleteString();
	
			text_area.setText(completeString);
		});
	}

}
