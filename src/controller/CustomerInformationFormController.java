package controller;

import client.model.Booking;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerInformationFormController implements Initializable{
	Booking booking;
	Stage stage;
	
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
    private Button Close_button;

    @FXML
    private TextField country_field;

    @FXML
    public void closeButton() {
    	stage.close();
    	
    }
    public void setStage(Stage s) {
    	stage = s;
    }
    public void setBooking(Booking b) {
    	booking = b;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		  Platform.runLater(()->
	        {
	    		first_name_field.setText(booking.getCustomer().getFirstName());
	    		last_name_field.setText(booking.getCustomer().getLastName());
	    		telephone_number_field.setText(booking.getCustomer().getTelephoneNumber());
	    		personal_number_field.setText(booking.getCustomer().getPersonalNumber());
	    		power_level_field.setText(booking.getCustomer().getPowerLevel().toString());
	    		address_field.setText(booking.getCustomer().getAddress().getStreetName());
	    		postal_code_field.setText(Integer.toString(booking.getCustomer().getAddress().getPostalCode()));
	    		town_field.setText(booking.getCustomer().getAddress().getCity());
	    		city_state_field.setText(booking.getCustomer().getAddress().getCity());
	    		card_number_field.setText(booking.getCustomer().getCreditCard().getCardNumber());
	    		expiration_month.setValue(""+booking.getCustomer().getCreditCard().getExpMonth());
	    		expiration_year.setValue(""+booking.getCustomer().getCreditCard().getExpYear());
	    		cvc_code_field.setText(""+booking.getCustomer().getCreditCard().getCvc());
	    		country_field.setText(booking.getCustomer().getAddress().getCountry());
	           
	        });

		// TODO Auto-generated method stub
		
	}

}