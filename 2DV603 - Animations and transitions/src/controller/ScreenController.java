package controller;

import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class ScreenController {
	StageManager stageM;
	/**
	 * Manages the handling of screens. Controller classes call the setScreen method to update the screen.
	 */
	public enum Screen {
		MAIN("/fxml/Main_menu.fxml"), // main menu.
		ABOUT("/fxml/about_fragment.fxml"),// Not yet done.
		CUSTOMER_FORM("/fxml/Customer_form.fxml"),
		RESULTS("/fxml/Result_list.fxml"),
		SEARCH("/fxml/Search_room.fxml");
		private String resourceLocation;

		Screen(String resourceLocation) {
			this.resourceLocation = resourceLocation;
		}

		public String getResourceLocation() {
			return resourceLocation;
		}
	}

	public ScreenController(StageManager s){
		stageM = s;
	}

	public void setScreen(Screen screen)throws IOException {
		if (screen.resourceLocation.equals(Screen.MAIN.getResourceLocation())) {
			stageM.setRoot(FXMLLoader.load(ScreenController.class.getResource(screen.getResourceLocation())));
			System.out.println("Fisk eller något");
		}
		else {
			System.out.println("bk");
			System.out.println(screen == null);
			System.out.println(screen.getResourceLocation());
			stageM.setPane(FXMLLoader.load(ScreenController.class.getResource(screen.getResourceLocation())));
			//StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource(screen.getResourceLocation())));
		}
	}
}

