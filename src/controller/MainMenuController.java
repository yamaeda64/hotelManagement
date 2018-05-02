package controller;


import client.model.Booking;
import client.model.Hotel;
import client.view.BookingWrapper;
import controller.ScreenController.Screen;
import controller.supportClasses.SwedishDateFormat;
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
    
    private CentralController centralController;
    private ObservableList<BookingWrapper> bookingList;
    
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
	@FXML private TableView<BookingWrapper> bookingsTableView;
	@FXML private Button refresh;
	
    
    

    @FXML
    public void new_booking_button() throws IOException {
    	String ost = ScreenController.Screen.SEARCH_ROOMS.getResourceLocation();
    	System.out.println(ost);
        centralController.changeScreen(Screen.SEARCH_ROOMS);
    }
    
    @FXML
    public void find_existing_button() throws IOException {
    	centralController.changeScreen(Screen.SEARCH_BOOKING);
    }
    @FXML
    public void menu_preferences() throws IOException {
    	centralController.changeScreen(Screen.SEARCH_ROOMS);
    }
    @FXML
    public void menu_quit() throws IOException {
       System.exit(1);
    }
    @FXML
    public void menu_about() throws IOException {
    	centralController.changeScreen(Screen.SEARCH_ROOMS);
    }
    
    @FXML
    public void refresh()
    {
        bookingList.clear();
        Iterator<Booking> bookingIterator = centralController.getBookings();
        while(bookingIterator.hasNext())
        {
            bookingList.add(new BookingWrapper(bookingIterator.next()));
        }
    }
    
    public void setCentralController(CentralController centralController)
    {
    	this.centralController = centralController;
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(()->
        {
            bookingList = FXCollections.observableArrayList();
            
            Iterator<Booking> bookingIterator = centralController.getBookings();
            while(bookingIterator.hasNext())
            {
                bookingList.add(new BookingWrapper(bookingIterator.next()));
            }
    
            initTableView();
    
            if(centralController.getLocation().equals(Hotel.VAXJO))
            {
                menu_vaxjo.setSelected(true);
            }
            else if(centralController.getLocation() == Hotel.KALMAR)
            {
                menu_kalmar.setSelected(true);
            }
           
        });
        
        
        menu_vaxjo.setOnAction(event ->
        {
            menu_vaxjo.setSelected(true);
            menu_kalmar.setSelected(false);
            centralController.setLocation(Hotel.VAXJO);
        });
        
        menu_kalmar.setOnAction(event ->
        {
            menu_kalmar.setSelected(true);
            menu_vaxjo.setSelected(false);
            centralController.setLocation(Hotel.KALMAR);
        });
    
        
        
        /* Date picker setup for dafault as today, and Swedish date format. */
        datePicker.setValue(LocalDate.now());
        datePicker.setShowWeekNumbers(true);
        
        SwedishDateFormat swedishDateFormat = new SwedishDateFormat();
        datePicker.setConverter(swedishDateFormat.getSwedishDateConverter());
    
        Hotel hotel;
        // Action when changing date picker
        datePicker.onActionProperty().set(event ->
        {
            System.out.println("came here");
            System.out.println(menu_vaxjo == null);
            if(menu_vaxjo.isSelected())
            {
                System.out.println("växjö");
                centralController.getBookings(Hotel.VAXJO, datePicker.getValue());
            } else if(menu_kalmar.isSelected())
            {
                centralController.getBookings(Hotel.KALMAR, datePicker.getValue());
            }
            else
            {
                System.out.println("non was selected");
            }
            
        });
    
    }
    
    private void initTableView()
    {
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<BookingWrapper, String>("firstName"));
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<BookingWrapper, String>("familyName"));
        
        TableColumn roomNumberCol = new TableColumn("Room Number");
        roomNumberCol.setCellValueFactory(
                new PropertyValueFactory<BookingWrapper, String>("roomNumbers"));
        
        TableColumn bookingStatusCol = new TableColumn("Booking Status");
        bookingStatusCol.setCellValueFactory(
                new PropertyValueFactory<BookingWrapper, String>("bookingStatus"));
        
        bookingsTableView.getColumns().addAll(firstNameCol, lastNameCol, roomNumberCol, bookingStatusCol);
        bookingsTableView.setItems(bookingList);
    }
}

