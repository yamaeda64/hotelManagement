package client.controller;


import client.model.Booking;
import client.model.Hotel;
import client.view.RoomWrapper;
import client.controller.ScreenController.Screen;
import client.controller.supportClasses.SwedishDateFormat;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.ResourceBundle;


public class MainMenuController implements Initializable{
    
    private FacadeController facadeController;
    private ObservableList<RoomWrapper> bookingList;
    
	@FXML private Button new_booking_button;
    @FXML private Button find_existing_button;
    @FXML private Button find_available_button;
	@FXML private MenuItem menu_new_booking;
	@FXML private MenuItem menu_find_existing;
	@FXML private MenuItem menu_preferences;
	@FXML private MenuItem menu_quit;
	@FXML private MenuItem menu_about;
	@FXML CheckMenuItem menu_vaxjo;
	@FXML CheckMenuItem menu_kalmar;
	@FXML private DatePicker datePicker;
	@FXML private TableView<RoomWrapper> bookingsTableView;
	@FXML private Button refresh;

    @FXML
    public void new_booking_button() throws IOException {
        facadeController.changeScreen(Screen.SEARCH_ROOMS);
    }
    
    @FXML
    public void find_existing_button() throws IOException {
    	facadeController.changeScreen(Screen.SEARCH_BOOKING);
    }
    @FXML
    public void menu_preferences() throws IOException {
    	facadeController.changeScreen(Screen.SEARCH_ROOMS);
    }
    @FXML
    public void menu_quit() throws IOException {
       System.exit(1);
    }
    @FXML
    public void menu_about() throws IOException {
    	facadeController.changeScreen(Screen.SEARCH_ROOMS);
    }
    
    @FXML
    public void refresh()
    {
        updateBookingsByDate();
        
    }
    
    public void setFacadeController(FacadeController facadeController)
    {
    	this.facadeController = facadeController;
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(()->
        {
            if(facadeController.getLocation().equals(Hotel.VAXJO))
            {
                menu_vaxjo.setSelected(true);
            }
            else if(facadeController.getLocation() == Hotel.KALMAR)
            {
                menu_kalmar.setSelected(true);
            }
            
            bookingList = FXCollections.observableArrayList();
           
            
            
            initTableView();
            updateBookingsByDate();
            
           
        });

        menu_vaxjo.setOnAction(event ->
        {
            menu_vaxjo.setSelected(true);
            menu_kalmar.setSelected(false);
            facadeController.setLocation(Hotel.VAXJO);
            updateBookingsByDate();
        });
        
        menu_kalmar.setOnAction(event ->
        {
            menu_kalmar.setSelected(true);
            menu_vaxjo.setSelected(false);
            facadeController.setLocation(Hotel.KALMAR);
            updateBookingsByDate();
        });
        bookingsTableView.setOnMouseClicked(event ->
        {
            if(event.getClickCount() > 1)
            {
                facadeController.clearBookings();
                facadeController.addBooking(bookingsTableView.getSelectionModel().getSelectedItem().getBooking());
                
                try
                {
                    facadeController.changeScreen(Screen.BOOKING_RESULTS);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        
        /* Date picker setup for default as today, and Swedish date format. */
        datePicker.setValue(LocalDate.now());
        datePicker.setShowWeekNumbers(true);
        
        SwedishDateFormat swedishDateFormat = new SwedishDateFormat();
        datePicker.setConverter(swedishDateFormat.getSwedishDateConverter());
    
        Hotel hotel;
        // Action when changing date picker
        datePicker.onActionProperty().set(event ->
        {
            updateBookingsByDate();
        });
    
    }
    
    private void initTableView()
    {
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<RoomWrapper, String>("firstName"));
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<RoomWrapper, String>("lastName"));
        
        TableColumn roomNumberCol = new TableColumn("Room Number");
        roomNumberCol.setCellValueFactory(
                new PropertyValueFactory<RoomWrapper, String>("roomNumbers"));
        roomNumberCol.setMaxWidth(130);
        
        TableColumn bookingStatusCol = new TableColumn("Booking Status");
        bookingStatusCol.setCellValueFactory(
                new PropertyValueFactory<RoomWrapper, String>("bookingStatus"));
        
        bookingsTableView.getColumns().addAll(firstNameCol, lastNameCol, roomNumberCol, bookingStatusCol);
        
        bookingsTableView.setItems(bookingList);
    }
    
    private void updateBookingsByDate()
    {
        bookingList.clear();
        
        if(menu_vaxjo.isSelected())
        {
            facadeController.clearBookings();
            facadeController.getBookings(Hotel.VAXJO, datePicker.getValue());
        }
        else if(menu_kalmar.isSelected())
        {
            facadeController.clearBookings();
            facadeController.getBookings(Hotel.KALMAR, datePicker.getValue());
        }
        Iterator<Booking> bookingIterator = facadeController.getBookings();
        while(bookingIterator.hasNext())
        {
            bookingList.add(new RoomWrapper(bookingIterator.next()));
        }
    }
}

