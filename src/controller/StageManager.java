package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManager {
    private Stage stage;
    private Pane pane;
    
    /**
     * 
     * @param stage
     * The stage you wish to control
     * @param fc
     * The controller for the facade
     * @throws IOException
     * Throws IOException if the screens are not found.
     */
    
    public StageManager(Stage stage, FacadeController fc) throws IOException {
        this.stage=stage;
        FXMLLoader loader = new FXMLLoader(ScreenController.class.getResource("/fxml/Main_menu.fxml"));
        Parent root=loader.load();

        MainMenuController mm = loader.getController();
        mm.setFacadeController(fc);
        Scene s = new Scene(root);
        stage.setScene(s);
        stage.show();
        
    }
    
    public Stage getStage() {return stage;}
    
    public void setRoot(Parent root) {
        System.out.println(root.getScene() == null);
        stage.getScene().setRoot(root);
        stage.setMinWidth(root.getScene().getWidth());
        stage.setMinHeight(root.getScene().getHeight());
        stage.getScene().setRoot(root);
    }
    
    public void setRoot(Parent root, double height, double width) {
        stage.setWidth(width);
        stage.setHeight(height);
        stage.getScene().setRoot(root);
    }
    
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