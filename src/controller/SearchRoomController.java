package controller;

import client.model.Hotel;
import client.model.Room;
import controller.ScreenController.Screen;
import controller.supportClasses.RoomSearch;
import controller.supportClasses.SwedishDateFormat;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchRoomController implements Initializable
{
    private CentralController centralController;
    
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
    private ComboBox<Room.BedType> bed_size_box;
    
    @FXML
    private ComboBox<Hotel> location_box;
    
    @FXML
    public void findRoomsButton() throws IOException
    {
    
        RoomSearch currentSearch = new RoomSearch();
    
        try
        {
            currentSearch.setHotel(location_box.getValue());
            currentSearch.setBedType(bed_size_box.getValue());
            currentSearch.setSmokingAllowed(smoking_allowed_box.isSelected());
            currentSearch.setAdjecentRoomAvailable(smoking_allowed_box.isSelected());
            currentSearch.setStartDate(check_in_datepicker.getValue());
            currentSearch.setEndDate(check_out_datepicker.getValue());
            
           
            System.out.println(currentSearch.getBedType() + "" + currentSearch.getHotel());
            centralController.updateModel(currentSearch);
            centralController.changeScreen(Screen.RESULTS);
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());  // TODO, this should be GUI error message
        }
        
    }
    @FXML
    public void cancelButton() throws IOException {
        centralController.changeScreen(Screen.MAIN);
    }
    public void setCentralController(CentralController centralController) {
        this.centralController = centralController;
    }
    public boolean hasNoCentralController() {
        return centralController == null;
    }
    @Override
    public void initialize(URL url, ResourceBundle resource) {
        
        location_box.getItems().removeAll(location_box.getItems());
        location_box.getItems().addAll(Hotel.values());
        bed_size_box.getItems().addAll(Room.BedType.values());
        
        SwedishDateFormat swedishDateFormat = new SwedishDateFormat();
        check_in_datepicker.setShowWeekNumbers(true);
        check_in_datepicker.setConverter(swedishDateFormat.getSwedishDateConverter());
        check_out_datepicker.setShowWeekNumbers(true);
        check_out_datepicker.setConverter(swedishDateFormat.getSwedishDateConverter());
    
        Platform.runLater(()->
        {
            location_box.setValue(centralController.getLocation());
        });
    }
        
    
}