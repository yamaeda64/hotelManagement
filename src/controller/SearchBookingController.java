package controller;

import java.io.IOException;

import controller.ScreenController.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SearchBookingController {
	private CentralController CC;
	
	
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
    	CC.changeScreen(Screen.MAIN);
    }
    @FXML
    public void findBookingButton() throws IOException {
    	CC.changeScreen(Screen.RESULTS);
    }
    
    
    public void setCentralController(CentralController cc) {
    	CC=cc;
    }
    public boolean hasNoCentralController() {
    	return CC == null;
    }
    
    
}
