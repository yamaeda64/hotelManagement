package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenController {
	controller.StageManager stageM;
	FXMLLoader loader;
	CentralController centralController;
	/**
	 * Manages the handling of screens. Controller classes call the setScreen method to update the screen.
	 */
	public enum Screen {
		MAIN("/fxml/Main_menu.fxml"), // main menu.
		ABOUT("/fxml/about_fragment.fxml"),// Not yet done.
		CUSTOMER_FORM("/fxml/Customer_form.fxml"),
		RESULTS("/fxml/Result_room_list.fxml"),
		SEARCH_ROOMS("/fxml/Search_room.fxml"),
		BOOKING_RESULTS("/fxml/result_booking_list.fxml"),
		CONFIRM_BOOKING("/fxml/Booking_confirmation.fxml"),
		SEARCH_BOOKING("/fxml/Search_booking.fxml");
	
		
		private String resourceLocation;
		
		Screen(String resourceLocation) {
			this.resourceLocation = resourceLocation;
		}
		
		public String getResourceLocation() {
			return resourceLocation;
		}
	}
	
	public ScreenController(Stage s, CentralController centralController) throws IOException{
		stageM = new controller.StageManager(s,centralController);
		this.centralController = centralController;
	}
	
	public void setScreen(ScreenController.Screen screen)throws IOException {
		if (screen.getResourceLocation() == ScreenController.Screen.MAIN.getResourceLocation()) {
			
			loader = new FXMLLoader(ScreenController.class.getResource("/fxml/Main_menu.fxml"));
			Parent root=loader.load();
			MainMenuController mainMenuController = loader.getController();
			mainMenuController.setCentralController(centralController);
			stageM.setRoot(root);
		}
		else if (screen.getResourceLocation() == ScreenController.Screen.SEARCH_ROOMS.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load();
			SearchRoomController searchRoomController = loader.getController();
			searchRoomController.setCentralController(centralController);
			stageM.setRoot(root);
		}
		else if (screen.getResourceLocation() ==  ScreenController.Screen.RESULTS.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load();
			ResultListController resultListController = loader.getController();
			resultListController.setCentralController(centralController);
			stageM.setRoot(root);
		}
		else if (screen.getResourceLocation() ==  ScreenController.Screen.CUSTOMER_FORM.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load();
			CustomerFormController customerFormController = loader.getController();
			customerFormController.setCentralController(centralController);
			stageM.setRoot(root);
			
		}
		else if (screen.getResourceLocation() == ScreenController.Screen.ABOUT.getResourceLocation()) {
			stageM.setPane(loader.load(ScreenController.class.getResource(screen.getResourceLocation())));
		}
		else if (screen.getResourceLocation() ==  ScreenController.Screen.SEARCH_BOOKING.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load();
			SearchBookingController searchBookingController = loader.getController();
			searchBookingController.setCentralController(centralController);
			stageM.setRoot(root);
		}
		else if (screen.getResourceLocation() == ScreenController.Screen.BOOKING_RESULTS.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load();
			BookingResultsListController bookingResultsListController = loader.getController();
			bookingResultsListController.setCentralController(centralController);
			stageM.setRoot(root);

		}
		else if (screen.getResourceLocation() == ScreenController.Screen.CONFIRM_BOOKING.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load();
			BookingConfirmationController bookingConfirmationController = loader.getController();
			bookingConfirmationController.setCentralController(centralController);
			bookingConfirmationController.setBookingInProgress(centralController.getBookingInProgress());
			stageM.setRoot(root);

		}
		else if (true) {
			System.err.println("Unexpected error during screen switching.");
			
		}
	}
}

