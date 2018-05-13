package client.controller;

import client.model.Booking;
import client.model.Booking.BookingStatus;
import client.model.Room;
import client.controller.ScreenController.Screen;
import client.controller.supportClasses.BookingSearch;
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
import java.util.Iterator;
import java.util.ResourceBundle;

public class BookingResultsListController implements Initializable
{
	FacadeController facadeController;
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
	private TextField amount_paid_field;

	@FXML
	private TextField amount_remaining_field;

	@FXML
	private Button pay_button;


	public void setFacadeController(FacadeController cc)
	{
		this.facadeController = cc;
	}

	@FXML
	public void checkInButton()
	{
		booking_ListView.getSelectionModel().getSelectedItem().setStatus(BookingStatus.CHECKED_IN);
		// TODO, send to server
		checked_in_field.setText("" + booking_ListView.getSelectionModel().getSelectedItem().getStatus());
		facadeController.changeBookingStatus(booking_ListView.getSelectionModel().getSelectedItem(), BookingStatus.CHECKED_IN);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Check in successful");
		alert.setHeaderText(null);
		alert.setContentText("The booking has been successfully checked in.");

		alert.showAndWait();
	}

	@FXML
	public void checkOutButton()
	{
		if (!amount_remaining_field.getText().equals("0.0")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Payment not complete");
			alert.setHeaderText(null);
			alert.setContentText("Customer has not paid the bill.");

			alert.showAndWait();
		}
		else {
			booking_ListView.getSelectionModel().getSelectedItem().setStatus(BookingStatus.CHECKED_OUT);
			// TODO, send to server
			checked_in_field.setText("" + booking_ListView.getSelectionModel().getSelectedItem().getStatus());
			facadeController.changeBookingStatus(booking_ListView.getSelectionModel().getSelectedItem(), BookingStatus.CHECKED_OUT);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Check out succeded");
			alert.setHeaderText(null);
			alert.setContentText("The booking has been successfully checked out");
			alert.showAndWait();
		}


	}

	@FXML
	public void cancelBookingButton()
	{
		booking_ListView.getSelectionModel().getSelectedItem().setStatus(BookingStatus.CANCELLED);
		checked_in_field.setText("" + booking_ListView.getSelectionModel().getSelectedItem().getStatus());
		facadeController.changeBookingStatus(booking_ListView.getSelectionModel().getSelectedItem(), BookingStatus.CANCELLED);
		
   
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Cancelation succeded");
		alert.setHeaderText(null);
		alert.setContentText("The booking has been successfully canceled");

		alert.showAndWait();

	}

	@FXML
	public void payButton() {

		//TODO Fix database access connection.
		
		booking_ListView.getSelectionModel().getSelectedItem().setAmountPaid(booking_ListView.getSelectionModel().getSelectedItem().getGivenPrice());
		
		facadeController.changePayedBookingAmount(
				booking_ListView.getSelectionModel().getSelectedItem(),
				booking_ListView.getSelectionModel().getSelectedItem().getGivenPrice(),
				booking_ListView.getSelectionModel().getSelectedItem().getAmountPaid()
		);
		 
		updateFields();
		
		
		
  
    amount_paid_field.setText(""+booking_ListView.getSelectionModel().getSelectedItem().getAmountPaid());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Payment succeded");
		alert.setHeaderText(null);
		alert.setContentText("The booking has been successfully marked as paid");

		alert.showAndWait();
	}

	@FXML
	public void backToSearchButton() throws IOException
	{
		facadeController.changeScreen(Screen.SEARCH_BOOKING);
	}

	@FXML
	public void returnToMainButton() throws IOException
	{
		facadeController.changeScreen(Screen.MAIN);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{

		Platform.runLater(() ->
		{
			search = facadeController.getBookingSearch();
			Iterator<Booking> bookings = facadeController.getBookings();
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
								setText(item.getCustomer().getLastName());
							} else
							{
								setText(null);
							}
						}
					};
					return cell;
				}
			});
		booking_ListView.getSelectionModel().selectFirst();
		updateFields();
		});

		booking_ListView.setOnMouseClicked(event ->
		{
			updateFields();
			
			//When an item in the list has been double-clicked.
			
			if(event.getClickCount() == 2)
			{
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
					
				}
				
				
			}
		});
	}
	/**
	 * Update the fields in the GUI.
	 */
	private void updateFields()
	{
		if(booking_ListView.getSelectionModel().getSelectedItem() != null)
		{
			booking_number_field.setText("" + booking_ListView.getSelectionModel().getSelectedItem().getId());
			name_field.setText(booking_ListView.getSelectionModel().getSelectedItem().getCustomer().getFirstName() + " " + booking_ListView.getSelectionModel().getSelectedItem().getCustomer().getLastName());
			
			Iterator<Room> rooms = booking_ListView.getSelectionModel().getSelectedItem().getAllRooms();
			String roomString = "";
			while(rooms.hasNext())
			{
				roomString += Integer.toString(rooms.next().getRoomNumber());
				if(rooms.hasNext())
				{
					roomString+= ", ";
				}
			}
			String shorterRoomString;
			if(roomString.length()> 19)
			{
				shorterRoomString = roomString.substring(0,19);
				shorterRoomString += "...";
				room_number_field.setText(shorterRoomString);
				Tooltip tooltip = new Tooltip(roomString);
				room_number_field.setTooltip(tooltip);
				
			}
			else
			{
				room_number_field.setText(roomString);
			}
			
			check_in_date_field.setText(booking_ListView.getSelectionModel().getSelectedItem().getStartDate().toString());
			check_out_date_field.setText(booking_ListView.getSelectionModel().getSelectedItem().getEndDate().toString());
			checked_in_field.setText(booking_ListView.getSelectionModel().getSelectedItem().getStatus().toString());
			Double amountPaid = booking_ListView.getSelectionModel().getSelectedItem().getAmountPaid();
			amount_paid_field.setText(""+amountPaid);
			amount_remaining_field.setText(""+
					(booking_ListView.getSelectionModel().getSelectedItem().getGivenPrice() - amountPaid));
		}
		else 		// when nothing is selected
		{
			
		}
	}
}