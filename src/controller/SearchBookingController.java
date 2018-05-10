package controller;

import client.model.Hotel;
import controller.ScreenController.Screen;
import controller.supportClasses.BookingSearch;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchBookingController implements Initializable{
    private CentralController centralController;
    
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
    private ComboBox<Hotel> location_box;
    
    @FXML
    public void cancelButton() throws IOException {
        centralController.changeScreen(Screen.MAIN);
    }
    @FXML
    public void findBookingButton() throws IOException {
        if (first_name_field.getText() == null && last_name_field.getText() == null && telephone_number_field.getText() == null && booking_number_field.getText() == null && passport_number_field.getText() == null) {
            centralController.showError("Search problem", "You need to fill in at least one field to do a search");
        }
        else {
            BookingSearch booking = new BookingSearch();
            booking.setFirstName(first_name_field.getText());
            booking.setLastName(last_name_field.getText());
            booking.setTelephoneNumber(telephone_number_field.getText());
            booking.setBookingNumber(booking_number_field.getText());
            booking.setPassportNumber(passport_number_field.getText());
            booking.setHotel(location_box.getValue());
            
            centralController.updateModel(booking);
            centralController.changeScreen(Screen.BOOKING_RESULTS);
        }
    }
    
    
    public void setCentralController(CentralController cc) {
        centralController=cc;
    }
    public boolean hasNoCentralController() {
        return centralController == null;
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        location_box.getItems().removeAll(location_box.getItems());
        location_box.getItems().addAll(Hotel.values());
        
        Platform.runLater(()->
        {
            location_box.setValue(centralController.getLocation());
        });
    }
    
    
}