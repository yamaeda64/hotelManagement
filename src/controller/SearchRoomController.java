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
    private FacadeController facadeController;
    
    @FXML
    private DatePicker check_in_datepicker;
    
    @FXML
    private DatePicker check_out_datepicker;
    
    @FXML
    private Button find_rooms_button;
    
    @FXML
    private Button cancel_button;
    
    @FXML
    private CheckBox smoking_allowed_box;
    
    @FXML
    private CheckBox neighboring_room_box;
    
    @FXML
    private ComboBox<Room.BedType> room_size_box;
    
    @FXML
    private ComboBox<Hotel> location_box;
    
    @FXML
    public void findRoomsButton() throws IOException
    {
        if(check_in_datepicker.getValue() == null || check_out_datepicker.getValue() == null)
        {
            facadeController.showError("Date error", "Both start date and end date needs to be set");
        }
        else
        {
            RoomSearch currentSearch = new RoomSearch();
    
            try
            {
                currentSearch.setHotel(location_box.getValue());
                currentSearch.setBedType(room_size_box.getValue());
                currentSearch.setSmokingAllowed(smoking_allowed_box.isSelected());
                currentSearch.setAdjecentRoomAvailable(neighboring_room_box.isSelected());
                currentSearch.setStartDate(check_in_datepicker.getValue());
                currentSearch.setEndDate(check_out_datepicker.getValue());
               System.out.println(1);
                facadeController.updateModel(currentSearch);
                facadeController.changeScreen(Screen.RESULTS);
                System.out.println(2);
            } catch(IllegalArgumentException e)
            {
                System.out.println(3);
                facadeController.showError("The search wasn't complete", e.getMessage());
            }
        }
    }
    @FXML
    public void cancelButton() throws IOException {
        facadeController.changeScreen(Screen.MAIN);
    }
    public void setFacadeController(FacadeController facadeController)
    {
        this.facadeController = facadeController;
    }
    public boolean hasNoCentralController() {
        return facadeController == null;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resource) {
        
        location_box.getItems().removeAll(location_box.getItems());
        location_box.getItems().addAll(Hotel.values());
        room_size_box.getItems().addAll(Room.BedType.values());
        
        SwedishDateFormat swedishDateFormat = new SwedishDateFormat();
        check_in_datepicker.setShowWeekNumbers(true);
        check_in_datepicker.setConverter(swedishDateFormat.getSwedishDateConverter());
        check_out_datepicker.setShowWeekNumbers(true);
        check_out_datepicker.setConverter(swedishDateFormat.getSwedishDateConverter());
        
        Platform.runLater(()->
        {
            location_box.setValue(facadeController.getLocation());
        });
    }
        
    
}