import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/App.fxml"));
            Scene scene = new Scene(root, Color.FLORALWHITE);

            stage.setScene(scene);
            stage.setTitle("Test");

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
