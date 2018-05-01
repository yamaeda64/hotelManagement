package controller;

import controller.ScreenController.Screen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable
{
    private controller.CentralController centralController;
    
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
    private ComboBox<String> expiration_month;
    
    @FXML
    private ComboBox<String> expiration_year;
    
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
        centralController.changeScreen(Screen.MAIN);
    }
    @FXML
    public void confirmButton() throws IOException {
        centralController.changeScreen(Screen.MAIN);
    }
    
    public void setCentralController(controller.CentralController centralController) {
        this.centralController = centralController;
    }
    public boolean hasNoCentralController() {
        return centralController == null;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        expiration_month.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
    
        int currentYear = LocalDate.now().getYear();
        for(int i = currentYear; i < currentYear + 6; i++)
        {
            expiration_year.getItems().add(("" + i).substring(2));  // Take last two digits of current year and five consecutive years
        }
    }
}

