import controller.FacadeController;
import javafx.application.Application;
import javafx.stage.Stage;

public class testMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        new FacadeController(primaryStage);
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
