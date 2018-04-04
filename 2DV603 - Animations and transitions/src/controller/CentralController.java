package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CentralController {
	private ScreenController screenC;
	private StageManager stageM; 
	
	public CentralController() {};
	
	public CentralController(Stage stage) throws IOException {
		stageM = new StageManager(stage);
		screenC = new ScreenController(stageM);
	}
	
	public ScreenController getScreenController() {
		return screenC;
	}
	public StageManager getStageManager() {
		return stageM;
	}
	
	
}
