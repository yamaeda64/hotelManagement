package controller;

import controller.ScreenController.Screen;
import javafx.fxml.FXML;
<<<<<<< HEAD
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
=======
>>>>>>> parent of 55dfa62... Uploading new fxmls and the booking search files.
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
<<<<<<< HEAD
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.ResourceBundle;


import client.model.Booking;
import client.model.Booking.BookingStatus;
import client.model.Room;

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

	public void setCentralController(CentralController cc) {
		this.centralController = cc;
	}
	@FXML
	public void checkInButton() {
		booking_ListView.getSelectionModel().getSelectedItem().setBookingStatus(BookingStatus.CHECKED_IN);
		checked_in_field.setText(""+booking_ListView.getSelectionModel().getSelectedItem().getBookingStatus());
	}
	@FXML
	public void checkOutButton() {
		booking_ListView.getSelectionModel().getSelectedItem().setBookingStatus(BookingStatus.CHECKED_OUT);
		checked_in_field.setText(""+booking_ListView.getSelectionModel().getSelectedItem().getBookingStatus());
	}
	@FXML
	public void cancelBookingButton() {
		booking_ListView.getSelectionModel().getSelectedItem().setBookingStatus(BookingStatus.CANCELLED);
		checked_in_field.setText(""+booking_ListView.getSelectionModel().getSelectedItem().getBookingStatus());
		
		/* TODO
		 * Logic for the late cancellation.
		 */
		LocalDate bookingStart = booking_ListView.getSelectionModel().getSelectedItem().getStartDate();
		LocalDate current = LocalDate.now();
		if (bookingStart.isAfter(current.minusDays(2)) && bookingStart.isBefore(current.plusDays(1)))  {
			System.err.println("Late cancellation");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Late cancellation. Customer will be fined for the cancellation fee.");

			alert.showAndWait();
		}
		
	}
	@FXML
	public void backToSearchButton() throws IOException {
		centralController.changeScreen(Screen.SEARCH_BOOKING);
	}
	@FXML
	public void returnToMainButton() throws IOException {
		centralController.changeScreen(Screen.MAIN);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Platform.runLater(()->
		{
			search = centralController.getBookingSearch();
			Iterator<Booking> bookings = centralController.getBookings();
			while (bookings.hasNext()) {
				booking_ListView.getItems().add(bookings.next());
			}


			//formatting the ListView.
			booking_ListView.setCellFactory(new Callback<ListView<Booking>, ListCell<Booking>>() {
				@Override
				public ListCell<Booking> call(ListView<Booking> param) {
					ListCell<Booking> cell = new ListCell<Booking>() {
						@Override
						protected void updateItem(Booking item, boolean empty) {
							super.updateItem(item, empty);
							if(item != null) {
								setText(item.getCustomer().getFamilyName());
							} else {
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
			if(booking_ListView.getSelectionModel().getSelectedItem() != null) {
				System.out.println(booking_ListView.getSelectionModel().getSelectedItem().getBookingStatus());
				booking_number_field.setText(""+booking_ListView.getSelectionModel().getSelectedItem().getBookingID());
				name_field.setText(booking_ListView.getSelectionModel().getSelectedItem().getCustomer().getFirstName() + " " + booking_ListView.getSelectionModel().getSelectedItem().getCustomer().getFamilyName());

				Iterator<Room> rooms = booking_ListView.getSelectionModel().getSelectedItem().getAllRooms();
				String roomString ="";
				while (rooms.hasNext()) {
					roomString+=Integer.toString(rooms.next().getRoomNumber()) + ", ";
				}
				room_number_field.setText(roomString);
				check_in_date_field.setText(booking_ListView.getSelectionModel().getSelectedItem().getStartDate().toString());
				check_out_date_field.setText(booking_ListView.getSelectionModel().getSelectedItem().getEndDate().toString());
				checked_in_field.setText(booking_ListView.getSelectionModel().getSelectedItem().getBookingStatus().toString());


				//When an item in the lisst has been double-clicked.
				if (event.getClickCount() == 2) {
					System.out.println("Double Clicked");
					//TODO Fixa en extra FXML med mera fält och se glad ut. Gör mer eller mindre om Customer form och fyll i all information.
					Stage infoStage = new Stage();
					FXMLLoader loader = new FXMLLoader(ScreenController.class.getResource("/fxml/Customer_information_form.fxml"));
					Parent root = null;
					try {
						root = loader.load();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					CustomerInformationFormController Controller = loader.getController();
					Controller.setBooking(booking_ListView.getSelectionModel().getSelectedItem());
					Controller.setStage(infoStage);
					if(root != null) {
						Scene s = new Scene(root);
						infoStage.setScene(s);
						infoStage.show();
					}
					else {
						System.err.println("File not found");
					}


				}
			}
			//Handling the return to menu bug.
			else {
				System.out.println("Nothing was selected");
			}
		});


=======

public class BookingResultsListController
{
	CentralController centralController;
>>>>>>> parent of 55dfa62... Uploading new fxmls and the booking search files.

    @FXML
    private ListView<?> booking_ListView;

    @FXML
    private TextField booking_number_field;

    @FXML
    private TextField name_field;

    @FXML
    private TextField room_number_field;

    @FXML
    private TextField telephone_number_field;

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

    public void setCentralController(CentralController cc) {
    	this.centralController = cc;
    }
    @FXML
    public void checkInButton() {
    	
    }
    @FXML
    public void checkOutButton() {
    	
    }
    @FXML
    public void cancelBookingButton() {
    	
    }
    @FXML
    public void backToSearchButton() throws IOException {
    	centralController.changeScreen(Screen.SEARCH_BOOKING);
    }
    @FXML
    public void returnToMainButton() throws IOException {
    	centralController.changeScreen(Screen.MAIN);
    }
}
