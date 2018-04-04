package controller;

import java.io.IOException;

import javafx.fxml.*;
import javafx.scene.control.Button;
import controller.ScreenController;
import controller.ScreenController.Screen;


public class MainMenuController extends CentralController{

	
	@FXML private Button new_booking_button;
    @FXML private Button find_existing_button;
    @FXML private Button find_available_button;
	
    //public void initialize() {}
    	
    	
    @FXML
    public void new_booking_button() throws IOException {
    	String ost = ScreenController.Screen.SEARCH.getResourceLocation();
    	System.out.println(ost);
        super.getScreenController().setScreen(Screen.SEARCH);
    }
    @FXML
    public void find_available_button() throws IOException {
        super.getScreenController().setScreen(Screen.SEARCH);
    }
    
    
}

