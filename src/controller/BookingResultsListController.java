package controller;

import controller.ScreenController.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class BookingResultsListController
{
	CentralController centralController;

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
