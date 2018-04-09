package controller;

import java.io.IOException;

import controller.ScreenController.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CustomerFormController {
	private CentralController CC;
	
    @FXML
    private TextField first_name_field;

    @FXML
    private TextField last_name_field;

    @FXML
    private TextField telephone_number_field;

    @FXML
    private TextField personal_number_field;

    @FXML
    private TextField power_level_field;

    @FXML
    private TextField address_field;

    @FXML
    private TextField postal_code_field;

    @FXML
    private TextField town_field;

    @FXML
    private TextField city_state_field;

    @FXML
    private TextField card_number_field;

    @FXML
    private ComboBox<?> expiration_month;

    @FXML
    private ComboBox<?> expiration_year;

    @FXML
    private TextField cvc_code_field;

    @FXML
    private Button cancel_button;

    @FXML
    private Button confirm_button;

    @FXML
    private TextField country_field;
    
    @FXML
    public void cancelButton() throws IOException {
    	CC.changeScreen(Screen.MAIN);
    }
    @FXML
    public void confirmButton() throws IOException {
    	CC.changeScreen(Screen.MAIN);
    }
    
    public void setCentralController(CentralController cc) {
    	CC=cc;
    }
    public boolean hasNoCentralController() {
    	return CC == null;
    }

}

