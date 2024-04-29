import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StatBoardController {
    @FXML
    private Label played;
    @FXML
    private Label won;
    @FXML
    private Label one;
    @FXML
    private Label two;
    @FXML
    private Label three;
    @FXML
    private Label four;
    @FXML
    private Label five;
    @FXML
    private Label six;

    public void setStatBoard(StatBoard statBoard) {
        List<String> stats = statBoard.getStats();

        played.setText("Played: " + stats.get(0));
        won.setText("Won: " + stats.get(1));
        one.setText("1: " + stats.get(2));
        two.setText("2: " + stats.get(3));
        three.setText("3: " + stats.get(4));
        four.setText("4: " + stats.get(5));
        five.setText("5: " + stats.get(6));
        six.setText("6: " + stats.get(7));
    }
}
