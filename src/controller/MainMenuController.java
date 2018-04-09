package controller;

import java.io.IOException;

import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import controller.ScreenController;
import controller.ScreenController.Screen;


public class MainMenuController{
	private CentralController CC;
	
	@FXML private Button new_booking_button;
    @FXML private Button find_existing_button;
    @FXML private Button find_available_button;
	@FXML private MenuItem menu_new_booking;
	@FXML private MenuItem menu_find_existing;
	@FXML private MenuItem menu_preferences;
	@FXML private MenuItem menu_quit;
	@FXML private MenuItem menu_about;
	@FXML CheckMenuItem menu_växjö;
	@FXML CheckMenuItem menu_kalmar;
    //public void initialize() {}
	
	
	
    	
    	
    @FXML
    public void new_booking_button() throws IOException {
    	String ost = ScreenController.Screen.SEARCH_ROOMS.getResourceLocation();
    	System.out.println(ost);
        CC.changeScreen(Screen.SEARCH_ROOMS);
    }
    @FXML
    public void find_available_button() throws IOException {
    	CC.changeScreen(Screen.SEARCH_ROOMS);
    }
    @FXML
    public void find_existing_button() throws IOException {
    	CC.changeScreen(Screen.SEARCH_BOOKING);
    }
    @FXML
    public void menu_preferences() throws IOException {
    	CC.changeScreen(Screen.SEARCH_ROOMS);
    }
    @FXML
    public void menu_quit() throws IOException {
       System.exit(1);
    }
    @FXML
    public void menu_about() throws IOException {
    	CC.changeScreen(Screen.SEARCH_ROOMS);
    }
    public void setCentralController(CentralController cc) {
    	CC = cc;
    }
    
    
}

