package controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManager {
    private Stage stage;
    private Pane pane;

    public StageManager(Stage stage) throws IOException {
        this.stage=stage;
        stage.setTitle("Hotel System");
        stage.setMinWidth(600.0);
        Parent root= FXMLLoader.load(ScreenController.class.getResource("/fxml/Main_menu.fxml"));

        Scene s = new Scene(root);
        s.getStylesheets().add("css/menu_items.css"); // css for design
        stage.setScene(s);
        stage.show();
    }

    public Stage getStage() {return stage;}

    public void setRoot(Parent root) {stage.getScene().setRoot(root);}

    public void setPane(Pane pane) {
    	this.pane=pane;
    	Scene scene = new Scene(pane);
    	stage.setScene(scene);
    	stage.show();
    	}

    public Pane getPane() {
        return pane;
    }

    public void setPaneFragment(Parent root) {
        this.pane.getChildren().setAll(root);
    }
}