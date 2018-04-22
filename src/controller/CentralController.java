package controller;

import client.model.ModelAccess;
import controller.ScreenController.Screen;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CentralController
{
	private ScreenController screenC;
	private StageManager stageM;
	private Stage stage;
	private Scene scene;
	private ModelAccess modelAccess;
	
	public CentralController() {
		
	};
	
	public CentralController(Stage stage) throws IOException {
		//setup();
		screenC = new ScreenController(stage,this);
		modelAccess = new ModelAccess();
	}
	
	public ScreenController getScreenController() {
		return screenC;
	}
	public StageManager getStageManager() {
		return stageM;
	}
	public void changeScreen(Screen screen) throws IOException {
		screenC.setScreen(screen);
	}
	
	
	/*
	public void setup() throws IOException {
	        this.stage= new Stage();
	        stage.setTitle("Hotel System");
	        stage.setMinWidth(600.0);
	        Parent root= FXMLLoader.load(ScreenController.class.getResource("/fxml/Main_menu.fxml"));

	        this.scene = new Scene(root);
	        scene.getStylesheets().add("css/menu_items.css"); // css for design
	        stage.setScene(scene);
	        System.out.print(Screen.MAIN.getResourceLocation());
	        stageM.setPaneFragment(root);
	        //screenC.setScreen(Screen.MAIN);
	        stage.show();
	}
	
	*/

	
}
