
import javafx.application.Application;
import javafx.stage.Stage;
import controller.*;

public class testMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        new CentralController(primaryStage);
    }
    public static void main(String[] args) {launch(args);
    }
}
