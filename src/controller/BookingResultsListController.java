package controller;

import client.model.Booking;
import client.model.Booking.BookingStatus;
import client.model.Room;
import client.model.customer.RealCustomer.PowerLevel;
import controller.ScreenController.Screen;
import controller.supportClasses.BookingSearch;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.ResourceBundle;

public class BookingResultsListController implements Initializable
{
	CentralController centralController;
	BookingSearch search;
	
	@FXML
	private ListView<Booking> booking_ListView;
	
	@FXML
	private TextField booking_number_field;
	
	@FXML
	private TextField name_field;
	
	@FXML
	private TextField room_number_field;
	
	@FXML
	private TextField checked_in_field;
	
	@FXML
	private Button check_in_button;
	
	@FXML
	private Button check_out_button;
	
	@FXML
	private Button cancel_booking_button;
	
	@FXML
	private Button back_to_search_button;
	
	@FXML
	private Button return_to_main_button;
	
	@FXML
	private TextField check_in_date_field;
	
	@FXML
	private TextField check_out_date_field;
	
	@FXML
	private TextField amount_payed_field;
	
	@FXML
	private TextField amount_remaining_field;

	public void setCentralController(CentralController cc)
	{
		this.centralController = cc;
	}

	@FXML
	public void checkInButton()
	{
		booking_ListView.getSelectionModel().getSelectedItem().setBookingStatus(BookingStatus.CHECKED_IN);
		// TODO, send to server
		checked_in_field.setText("" + booking_ListView.getSelectionModel().getSelectedItem().getBookingStatus());
		centralController.changeBookingStatus(booking_ListView.getSelectionModel().getSelectedItem(), BookingStatus.CHECKED_IN);
	}

	@FXML
	public void checkOutButton()
	{
		if (amount_remaining_field.getText() != "0") {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Payment not complete");
			alert.setHeaderText(null);
			alert.setContentText("Customer has not payed the bill.");

			alert.showAndWait();
		}
		else {
			booking_ListView.getSelectionModel().getSelectedItem().setBookingStatus(BookingStatus.CHECKED_OUT);
			// TODO, send to server
            booking_ListView.getSelectionModel().getSelectedItem().setBookingStatus(BookingStatus.CHECKED_OUT);
            // Todo, send to server
            checked_in_field.setText("" + booking_ListView.getSelectionModel().getSelectedItem().getBookingStatus());
            centralController.changeBookingStatus(booking_ListView.getSelectionModel().getSelectedItem(), BookingStatus.CHECKED_OUT);		}
		
	}

	@FXML
	public void cancelBookingButton()
	{
		booking_ListView.getSelectionModel().getSelectedItem().setBookingStatus(BookingStatus.CANCELLED);
		checked_in_field.setText("" + booking_ListView.getSelectionModel().getSelectedItem().getBookingStatus());
		
		centralController.changeBookingStatus(booking_ListView.getSelectionModel().getSelectedItem(), BookingStatus.CANCELLED);
		// TODO, send to server
		LocalDate bookingStart = booking_ListView.getSelectionModel().getSelectedItem().getStartDate();
		LocalDate current = LocalDate.now();
		if(bookingStart.isAfter(current.minusDays(2)) && bookingStart.isBefore(current.plusDays(1)))
		{
			centralController.changePayedBookingAmount(booking_ListView.getSelectionModel().getSelectedItem(), 0, booking_ListView.getSelectionModel().getSelectedItem().getPrice() * 0.1); // payes 10% of price if late cancellation TODO, this should maybe be set from server
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Late cancellation. Customer will be fined for the cancellation fee.");
			
			alert.showAndWait();
		}
		
	}
	
	@FXML
	public void backToSearchButton() throws IOException
	{
		centralController.changeScreen(Screen.SEARCH_BOOKING);
	}
	
	@FXML
	public void returnToMainButton() throws IOException
	{
		centralController.changeScreen(Screen.MAIN);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		
		Platform.runLater(() ->
		{
			search = centralController.getBookingSearch();
			Iterator<Booking> bookings = centralController.getBookings();
			while(bookings.hasNext())
			{
				booking_ListView.getItems().add(bookings.next());
			}
			
			
			//formatting the ListView.
			booking_ListView.setCellFactory(new Callback<ListView<Booking>, ListCell<Booking>>()
			{
				@Override
				public ListCell<Booking> call(ListView<Booking> param)
				{
					ListCell<Booking> cell = new ListCell<Booking>()
					{
						@Override
						protected void updateItem(Booking item, boolean empty)
						{
							super.updateItem(item, empty);
							if(item != null)
							{
								setText(item.getCustomer().getFamilyName());
							} else
							{
								setText(null);
							}
						}
					};
					return cell;
				}
			});
			
			
		});
		
		booking_ListView.setOnMouseClicked(event ->
		{
			if(booking_ListView.getSelectionModel().getSelectedItem() != null)
			{
				System.out.println(booking_ListView.getSelectionModel().getSelectedItem().getBookingStatus());
				booking_number_field.setText("" + booking_ListView.getSelectionModel().getSelectedItem().getBookingID());
				name_field.setText(booking_ListView.getSelectionModel().getSelectedItem().getCustomer().getFirstName() + " " + booking_ListView.getSelectionModel().getSelectedItem().getCustomer().getFamilyName());

				Iterator<Room> rooms = booking_ListView.getSelectionModel().getSelectedItem().getAllRooms();
				String roomString = "";
				while(rooms.hasNext())
				{
					roomString += Integer.toString(rooms.next().getRoomNumber()) + ", ";
				}
				room_number_field.setText(roomString);
				check_in_date_field.setText(booking_ListView.getSelectionModel().getSelectedItem().getStartDate().toString());
				check_out_date_field.setText(booking_ListView.getSelectionModel().getSelectedItem().getEndDate().toString());
				checked_in_field.setText(booking_ListView.getSelectionModel().getSelectedItem().getBookingStatus().toString());

				//Cash Logic
				amount_payed_field.setText(""+booking_ListView.getSelectionModel().getSelectedItem().getAmountPayed());
				
				if(booking_ListView.getSelectionModel().getSelectedItem().getCustomer().getPowerLevel() == null) {		//Preventing Nullpointer exception.
					amount_remaining_field.setText(""+(booking_ListView.getSelectionModel().getSelectedItem().getPrice() - booking_ListView.getSelectionModel().getSelectedItem().getAmountPayed()));
				}
				else if(booking_ListView.getSelectionModel().getSelectedItem().getCustomer().getPowerLevel() == PowerLevel.NONE) {
					amount_remaining_field.setText(""+(booking_ListView.getSelectionModel().getSelectedItem().getPrice() - booking_ListView.getSelectionModel().getSelectedItem().getAmountPayed()));
				}
				else if(booking_ListView.getSelectionModel().getSelectedItem().getCustomer().getPowerLevel() == PowerLevel.BRONZE) {
					amount_remaining_field.setText(""+((booking_ListView.getSelectionModel().getSelectedItem().getPrice()*0.95)- booking_ListView.getSelectionModel().getSelectedItem().getAmountPayed()));
				}
				else if(booking_ListView.getSelectionModel().getSelectedItem().getCustomer().getPowerLevel() == PowerLevel.SILVER) {
					amount_remaining_field.setText(""+((booking_ListView.getSelectionModel().getSelectedItem().getPrice()*0.90)- booking_ListView.getSelectionModel().getSelectedItem().getAmountPayed()));
				}
				else if(booking_ListView.getSelectionModel().getSelectedItem().getCustomer().getPowerLevel() == PowerLevel.GOLD) {
					amount_remaining_field.setText(""+((booking_ListView.getSelectionModel().getSelectedItem().getPrice()*0.85)- booking_ListView.getSelectionModel().getSelectedItem().getAmountPayed()));
				}
				else if(booking_ListView.getSelectionModel().getSelectedItem().getCustomer().getPowerLevel() == PowerLevel.PLATINUM) {
					amount_remaining_field.setText(""+((booking_ListView.getSelectionModel().getSelectedItem().getPrice()*0.80)- booking_ListView.getSelectionModel().getSelectedItem().getAmountPayed()));
				}
				else if(booking_ListView.getSelectionModel().getSelectedItem().getCustomer().getPowerLevel() == PowerLevel.DIAMOND) {
					amount_remaining_field.setText(""+((booking_ListView.getSelectionModel().getSelectedItem().getPrice()*0.75)- booking_ListView.getSelectionModel().getSelectedItem().getAmountPayed()));
				}
				
				//When an item in the lisst has been double-clicked.
				if(event.getClickCount() == 2)
				{
					System.out.println("Double Clicked");
					Stage infoStage = new Stage();
					FXMLLoader loader = new FXMLLoader(ScreenController.class.getResource("/fxml/Customer_information_form.fxml"));
					Parent root = null;
					try
					{
						root = loader.load();
					} catch(IOException e)
					{
						e.printStackTrace();
					}
					CustomerInformationFormController Controller = loader.getController();
					Controller.setBooking(booking_ListView.getSelectionModel().getSelectedItem());
					Controller.setStage(infoStage);
					if(root != null)
					{
						Scene s = new Scene(root);
						infoStage.setScene(s);
						infoStage.show();
					} else
					{
						System.err.println("File not found");
					}
					
					
				}
			}
			//Handling the return to menu bug.
			else
			{
				System.out.println("Nothing was selected");
			}
		});
	}
}