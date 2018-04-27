package controller;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controller.ScreenController.Screen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.collections.*;

public class SearchRoomController implements Initializable{
private CentralController CC;

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
    public void setCentralController(CentralController cc) {
    	CC=cc;
    }
    public boolean hasNoCentralController() {
    	return CC == null;
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	    location_box.getItems().removeAll(location_box.getItems());
	    location_box.getItems().addAll("Växjö","Kalmar");
	    bed_size_box.getItems().addAll("Single", "Twin", "King");
	    
	}
    
    
}