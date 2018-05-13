package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenController {
	controller.StageManager stageM;
	FXMLLoader loader;
	FacadeController facadeController;
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
	
	public ScreenController(Stage s, FacadeController facadeController) throws IOException{
		stageM = new controller.StageManager(s, facadeController);
		this.facadeController = facadeController;
	}
	
	/**
	 * 
	 * @param screen
	 * The screen that has the resource location for the selected fxml
	 * 
	 * @throws IOException
	 * 
	 * Changes the current screen in the application. Throws IOException if the screen cannot be found.
	 * 
	 */
	
	public void setScreen(ScreenController.Screen screen)throws IOException {
		if (screen.getResourceLocation() == ScreenController.Screen.MAIN.getResourceLocation()) {
			
			loader = new FXMLLoader(ScreenController.class.getResource("/fxml/Main_menu.fxml"));
			Parent root=loader.load();
			MainMenuController mainMenuController = loader.getController();
			mainMenuController.setFacadeController(facadeController);
			stageM.setRoot(root);
		}
		else if (screen.getResourceLocation() == ScreenController.Screen.SEARCH_ROOMS.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load();
			SearchRoomController searchRoomController = loader.getController();
			searchRoomController.setFacadeController(facadeController);
			stageM.setRoot(root);
		}
		else if (screen.getResourceLocation() ==  ScreenController.Screen.RESULTS.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load();
			ResultListController resultListController = loader.getController();
			resultListController.setFacadeController(facadeController);
			stageM.setRoot(root);
		}
		else if (screen.getResourceLocation() ==  ScreenController.Screen.CUSTOMER_FORM.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load();
			CustomerFormController customerFormController = loader.getController();
			customerFormController.setFacadeController(facadeController);
			stageM.setRoot(root);
			
		}
		else if (screen.getResourceLocation() == ScreenController.Screen.ABOUT.getResourceLocation()) {
			stageM.setPane(loader.load(ScreenController.class.getResource(screen.getResourceLocation())));
		}
		else if (screen.getResourceLocation() ==  ScreenController.Screen.SEARCH_BOOKING.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load();
			SearchBookingController searchBookingController = loader.getController();
			searchBookingController.setFacadeController(facadeController);
			stageM.setRoot(root);
		}
		else if (screen.getResourceLocation() == ScreenController.Screen.BOOKING_RESULTS.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load();
			BookingResultsListController bookingResultsListController = loader.getController();
			bookingResultsListController.setFacadeController(facadeController);
			stageM.setRoot(root);

		}
		else if (screen.getResourceLocation() == ScreenController.Screen.CONFIRM_BOOKING.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load();
			BookingConfirmationController bookingConfirmationController = loader.getController();
			bookingConfirmationController.setFacadeController(facadeController);
			//bookingConfirmationController.setBookingInProgress(facadeController.getBookingInProgress());
			stageM.setRoot(root);

		}
		else if (true) {
			facadeController.showError("ERROR", "An exception occured while reading local resources");
			
		}
	}
}

