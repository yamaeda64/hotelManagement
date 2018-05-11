package controller;

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
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

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
	private boolean priceOverrided = false;

	private String completeString;


	
	/* TODO
	 * Fix connection so that price is updated correctly when entering this fxml.
	 */



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
			bookingInProgress.setPrice(newPrice);
			}
			catch (NumberFormatException e) {
				centralController.showError("Faulty input", "Digits and one dot only");
			}
			
		}

	}



	@FXML
	void bookingButton(ActionEvent event) throws IOException {

		//TODO, if set final price isn't blank
		// check if right format, (maybe change , to . )
		// if blank, do below code, else set the price to the same as the field.
		centralController.finalizeBooking(bookingInProgress.getPrice());
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
	public void updateCompleteString() {
		completeString = 
				"Summary of the Booking \n"
						+ "Name: "+firstName+" "+lastName +"\n"+
						"Phone number: "+phoneNumber + "\n"+
						"Rooms: "+roomNumber+"\n"+
						"Power level: "+powerLevel+"\n"+
						"\n"+
						"\n"+
						"Total cost: "+totalCost + "\n"+
						"Price overrided: "+priceOverrided;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		text_area.setEditable(false);

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

			updateCompleteString();

			text_area.setText(completeString);
		});
	}

}
