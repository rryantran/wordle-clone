import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/App.css").toExternalForm());
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setTitle("Wordle!");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
