package controller;

import client.model.Booking;
import client.model.Room;
import controller.ScreenController.Screen;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class ResultListController implements Initializable
{
    private controller.CentralController centralController;
    
    @FXML
    private ListView<Room> result_list;
    
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
    private TextField price_field;
    
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
        centralController.changeScreen(Screen.MAIN);
    }
    @FXML
    public void bookingButton() throws IOException
    {
    
        ObservableList<Room> selectedItems = result_list.getSelectionModel().getSelectedItems();
        if(selectedItems.size() == 0)
        {
            centralController.showError("Selection problem","No rooms were selected");
        }
        else
        {
            Booking booking = new Booking();
            booking.setStatus(Booking.BookingStatus.IN_PROGRESS);
            booking.setStartDate(centralController.getLastRoomSearch().getStartDate());
            booking.setEndDate(centralController.getLastRoomSearch().getEndDate());
            for(Room room : selectedItems)
            {
                System.out.println("added room");
                booking.addRoom(room);
            }
    
            centralController.createNewBooking(booking);
            centralController.changeScreen(Screen.CUSTOMER_FORM);
        }
    }
    
    public void setCentralController(controller.CentralController cc) {
        this.centralController = cc;
    }
    
    public boolean hasNoCentralController() {
        return centralController == null;
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        
        
        initListView();
        
        result_list.setOnMouseClicked(event ->
        {
            if(result_list.getSelectionModel().getSelectedItem()!= null)
            {
                Room selectedRoom = result_list.getSelectionModel().getSelectedItem();
                String roomNumber = "" + selectedRoom.getRoomNumber();
                room_number_field.setText(roomNumber);
                floor_field.setText("" + roomNumber.charAt(0));
                location_field.setText("" + selectedRoom.getHotel());
                room_size_field.setText("" + selectedRoom.getBedType());
                smoke_free_field.setText("" + selectedRoom.isNoSmoking());
                view_field.setText(selectedRoom.getView());
                price_field.setText("" + selectedRoom.getStandardPrice());
            }
        });
        
        Platform.runLater(()->
        {
            Iterator<Room> roomIterator = centralController.getAvailableRooms();
            
            while(roomIterator.hasNext())
            {
                
                result_list.getItems().add(roomIterator.next());
            }
        });
       
    }
    
    private void initListView()
    {
        result_list.setCellFactory(param ->new ListCell<Room>() {
            @Override
            protected void updateItem(Room room, boolean empty) {
                super.updateItem(room, empty);
        
                if (empty || room == null) {
                    setText(null);
                } else {
                    setText(""+room.getRoomNumber());
                }
            }
        });
        result_list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
