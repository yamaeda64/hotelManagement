package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenController {
	StageManager stageM;
	FXMLLoader loader;
	CentralController CC;
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
		SEARCH_BOOKING("/fxml/Search_booking.fxml");
		
		private String resourceLocation;

		Screen(String resourceLocation) {
			this.resourceLocation = resourceLocation;
		}

		public String getResourceLocation() {
			return resourceLocation;
		}
	}

	public ScreenController(Stage s, CentralController cc) throws IOException{
		stageM = new StageManager(s,cc);
		CC = cc;
	}

	public void setScreen(Screen screen)throws IOException {
		if (screen.getResourceLocation() == Screen.MAIN.getResourceLocation()) {

			loader = new FXMLLoader(ScreenController.class.getResource("/fxml/Main_menu.fxml"));
			Parent root=loader.load(); 
			//FXMLLoader.load(ScreenController.class.getResource("/fxml/Main_menu.fxml"));
			MainMenuController mm = loader.getController();
			mm.setCentralController(CC);
			stageM.setRoot(root);
			//stageM.setPane(loader.load(ScreenController.class.getResource(screen.getResourceLocation())));
			//stageM.setRoot(FXMLLoader.load(ScreenController.class.getResource(screen.getResourceLocation())));
		}
		else if (screen.getResourceLocation() ==  Screen.SEARCH_ROOMS.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load(); 
			SearchRoomController controller = loader.getController();
			controller.setCentralController(CC);
			//System.out.print(controller == null);
			//System.out.println(controller.hasNoCentralController());
			stageM.setRoot(root);
		}
		else if (screen.getResourceLocation() ==  Screen.RESULTS.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load(); 
			ResultListController controller = loader.getController();
			controller.setCentralController(CC);	
			//System.out.print(controller == null);
			//System.out.println(controller.hasNoCentralController());
			stageM.setRoot(root);
	
			//stageM.setPane(loader.load(ScreenController.class.getResource(screen.getResourceLocation())));
		}
		else if (screen.getResourceLocation() ==  Screen.CUSTOMER_FORM.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load();
			CustomerFormController controller = loader.getController();
			controller.setCentralController(CC);
			//System.out.print(controller == null);
			//System.out.println(controller.hasNoCentralController());
			stageM.setRoot(root);
			
			//stageM.setPane(loader.load(ScreenController.class.getResource(screen.getResourceLocation())));
		}
		else if (screen.getResourceLocation() ==  Screen.ABOUT.getResourceLocation()) {
			stageM.setPane(loader.load(ScreenController.class.getResource(screen.getResourceLocation())));
		}
		else if (screen.getResourceLocation() ==  Screen.SEARCH_BOOKING.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load(); 
			SearchBookingController controller = loader.getController();
			controller.setCentralController(CC);
			//System.out.print(controller == null);
			//System.out.println(controller.hasNoCentralController());
			stageM.setRoot(root);
			
			//stageM.setPane(loader.load(ScreenController.class.getResource(screen.getResourceLocation())));
		}
		else if (screen.getResourceLocation() ==  Screen.BOOKING_RESULTS.getResourceLocation()) {
			loader = new FXMLLoader(ScreenController.class.getResource(screen.getResourceLocation()));
			Parent root=loader.load(); 
			BookingResultsListController controller = loader.getController();
			controller.setCentralController(CC);
			//System.out.print(controller == null);
			//System.out.println(controller.hasNoCentralController());
			stageM.setRoot(root);
			
			//stageM.setPane(loader.load(ScreenController.class.getResource(screen.getResourceLocation())));
		}
		else if (true) {
			System.out.println("error??=?");



			//StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource(screen.getResourceLocation())));
		}
	}
}

