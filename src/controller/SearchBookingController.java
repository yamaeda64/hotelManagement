package controller;

import controller.ScreenController.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SearchBookingController{
	private CentralController centralController;
	
	
    @FXML
    private TextField first_name_field;

    @FXML
    private TextField last_name_field;

    @FXML
    private TextField telephone_number_field;

    @FXML
    private TextField booking_number_field;

    @FXML
    private Button cancel_button;

    @FXML
    private Button find_booking_button;

    @FXML
    private TextField passport_number_field;
    
    
    @FXML
    public void cancelButton() throws IOException {
    	centralController.changeScreen(Screen.MAIN);
    }
    @FXML
    public void findBookingButton() throws IOException {
    	centralController.changeScreen(Screen.BOOKING_RESULTS);
    }
    
    
    public void setCentralController(CentralController cc) {
    	centralController=cc;
    }
    public boolean hasNoCentralController() {
    	return centralController == null;
    }
    
    
}
