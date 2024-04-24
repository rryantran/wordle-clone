import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App.fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();
            Scene scene = new Scene(root);

            scene.setOnKeyPressed(e -> {
                controller.handleKeyPress(e);
            }); // key press event handler

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
