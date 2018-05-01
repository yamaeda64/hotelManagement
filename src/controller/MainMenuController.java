package controller;

import client.model.Booking;
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
    
    private CentralController CC;
    private ObservableList<BookingWrapper> bookingList;
    
	@FXML private Button new_booking_button;
    @FXML private Button find_existing_button;
    @FXML private Button find_available_button;
	@FXML private MenuItem menu_new_booking;
	@FXML private MenuItem menu_find_existing;
	@FXML private MenuItem menu_preferences;
	@FXML private MenuItem menu_quit;
	@FXML private MenuItem menu_about;
	@FXML CheckMenuItem menu_växjö;
	@FXML CheckMenuItem menu_kalmar;
	@FXML private DatePicker datePicker;
	@FXML private TableView<BookingWrapper> bookingsTableView;
    
    

    @FXML
    public void new_booking_button() throws IOException {
    	String ost = ScreenController.Screen.SEARCH_ROOMS.getResourceLocation();
    	System.out.println(ost);
        CC.changeScreen(Screen.SEARCH_ROOMS);
    }
    
    @FXML
    public void find_existing_button() throws IOException {
    	CC.changeScreen(Screen.SEARCH_BOOKING);
    }
    @FXML
    public void menu_preferences() throws IOException {
    	CC.changeScreen(Screen.SEARCH_ROOMS);
    }
    @FXML
    public void menu_quit() throws IOException {
       System.exit(1);
    }
    @FXML
    public void menu_about() throws IOException {
    	CC.changeScreen(Screen.SEARCH_ROOMS);
    }
    public void setCentralController(CentralController cc) {
    	CC = cc;
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(()->
        {
            if(CC == null)
            {
                System.out.println("CC = null");
            }
            else
            {
                System.out.println("CC is available");
            }
            bookingList = FXCollections.observableArrayList();
    
    
            Iterator<Booking> bookingIterator = CC.getBookings();
            while(bookingIterator.hasNext())
            {
                bookingList.add(new BookingWrapper(bookingIterator.next()));
            }
            System.out.println(bookingList.size());
    
            initTableView();
           
        });
    
        
        
        /* Date picker setup for dafault as today, and Swedish date format. */
        datePicker.setValue(LocalDate.now());
        datePicker.setShowWeekNumbers(true);
        
        SwedishDateFormat swedishDateFormat = new SwedishDateFormat();
        datePicker.setConverter(swedishDateFormat.getSwedishDateConverter());
        
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

