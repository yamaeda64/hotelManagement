package controller;
import controller.ScreenController.Screen;
import controller.supportClasses.SwedishDateFormat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchRoomController implements Initializable
{
    private controller.CentralController CC;
    
    @FXML
    private DatePicker check_in_datepicker;
    
    @FXML
    private DatePicker check_out_datepicker;
    
    @FXML
    private Button find_rooms_button;
    
    @FXML
    private Button cancel_button;
    
    @FXML
    private TextField number_of_beds_field;
    
    @FXML
    private CheckBox smoking_allowed_box;
    
    @FXML
    private CheckBox neighboring_room_box;
    
    @FXML
    private ComboBox<String> bed_size_box;
    
    @FXML
    private ComboBox<String> location_box;
    
    @FXML
    public void findRoomsButton() throws IOException {
        CC.changeScreen(Screen.RESULTS);
    }
    @FXML
    public void cancelButton() throws IOException {
        CC.changeScreen(Screen.MAIN);
    }
    public void setCentralController(controller.CentralController cc) {
        CC=cc;
    }
    public boolean hasNoCentralController() {
        return CC == null;
    }
    @Override
    public void initialize(URL url, ResourceBundle resource) {
        
        location_box.getItems().removeAll(location_box.getItems());
        location_box.getItems().addAll("V" + (char)228 + "xj" + (char)246,"Kalmar");               // TODO. choises should probably be received from server
        bed_size_box.getItems().addAll("Single", "Twin", "King");                                 // TODO, choises should probably be received form server
        
        
        SwedishDateFormat swedishDateFormat = new SwedishDateFormat();
        check_in_datepicker.setShowWeekNumbers(true);
        check_in_datepicker.setConverter(swedishDateFormat.getSwedishDateConverter());
        check_out_datepicker.setShowWeekNumbers(true);
        check_out_datepicker.setConverter(swedishDateFormat.getSwedishDateConverter());
    
    }
        
    
}