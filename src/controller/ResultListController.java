package controller;

import controller.ScreenController.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ResultListController {
    private controller.CentralController CC;
    
    @FXML
    private ListView<?> result_list;
    
    @FXML
    private TextField room_number_field;
    
    @FXML
    private TextField floor_field;
    
    @FXML
    private TextField location_field;
    
    @FXML
    private TextField smoke_free_field;
    
    @FXML
    private TextField number_of_beds_field;
    
    @FXML
    private TextField bed_size_field;
    
    @FXML
    private TextField view_field;
    
    @FXML
    private TextField room_size_field;
    
    @FXML
    private Button proceed_to_booking_button;
    
    @FXML
    private Button return_to_main_menu_button;
    
    @FXML
    public void cancelButton() throws IOException {
        CC.changeScreen(Screen.MAIN);
    }
    @FXML
    public void bookingButton() throws IOException {
        CC.changeScreen(Screen.CUSTOMER_FORM);
    }
    
    public void setCentralController(controller.CentralController cc) {
        CC=cc;
    }
    public boolean hasNoCentralController() {
        return CC == null;
    }
}
