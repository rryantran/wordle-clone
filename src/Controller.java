import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javafx.stage.Popup;
import javafx.stage.Stage;

public class Controller {
    private GameBoard gameBoard = new GameBoard();
    private StatBoard statBoard = new StatBoard();
    private Word word = new Word();
    private List<String> wrongLetters = new ArrayList<>();

    private int row = 0;
    private int col = 0;
    private int numGuesses = 0;
    private boolean gameOver = false;
    private boolean win = false;

    private Stage stage;
    private Popup popup;
    private StatBoardController statBoardController;

    @FXML
    private GridPane board;
    @FXML
    private GridPane keyrow1;
    @FXML
    private GridPane keyrow2;
    @FXML
    private GridPane keyrow3;
    @FXML
    private Button reset;

    // ------------------------------------------------------------------------------------------------------------
    // SET EXTERNAL VARIABLES
    // ------------------------------------------------------------------------------------------------------------
    public void setStage(Stage startStage) {
        stage = startStage;
    }

    // ------------------------------------------------------------------------------------------------------------
    // POPUP HANDLER
    // ------------------------------------------------------------------------------------------------------------
    public void showPopup(Stage stage, String FXMLPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLPath));
            Parent root = loader.load();

            if (FXMLPath.equals("/fxml/StatBoard.fxml")) {
                // load stat board controller for stat board popup
                statBoardController = loader.getController();
                statBoardController.setStatBoard(statBoard);
            }

            // set up popup
            popup = new Popup();
            popup.getContent().add(root);
            popup.show(stage);

            // center position
            double centerX = stage.getWidth() / 2;
            double centerY = stage.getHeight() / 2;

            // hide popup to access popup dimensions
            popup.hide();

            // subtract popup size to get true center
            double finalX = centerX - popup.getWidth() / 2;
            double finalY = centerY - popup.getHeight() / 2;

            // set popup position
            popup.setX(finalX);
            popup.setY(finalY);

            popup.show(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hidePopup() {
        if (popup != null) {
            popup.hide();
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // KEY EVENT HANDLER
    // ------------------------------------------------------------------------------------------------------------
    public void handleKeyPress(KeyEvent e) {
        if (!gameOver) {
            String keyPressed = e.getCode().toString(); // get pressed key

            if (!wrongLetters.contains(keyPressed)) {

                if (keyPressed.equals("ENTER")) {
                    if (col >= 5) {
                        enterLogic();
                        checkGameOver();
                    }
                } else if (keyPressed.equals("BACK_SPACE")) {
                    if (col > 0) {
                        backspaceLogic();

                        Label label = getLabelByIndex(board, row, col);

                        if (label != null) {
                            label.setText("");
                        }
                    }
                } else if (Character.isLetter(keyPressed.charAt(0)) && keyPressed.length() == 1) {
                    gameBoard.rowEnqueue(keyPressed);

                    Label label = getLabelByIndex(board, row, col);

                    if (label != null) {
                        label.setText(keyPressed);
                    }

                    if (col < 5) {
                        incrementCol();
                    }
                }
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // KEY CLICK EVENT HANDLER
    // ------------------------------------------------------------------------------------------------------------
    public void handleKeyboardPress(MouseEvent e) {
        if (!gameOver) {
            Label key = (Label) e.getSource(); // get pressed button
            String keyPressed = key.getText(); // get text of button

            if (!wrongLetters.contains(keyPressed)) {

                if (keyPressed.equals("ENTER")) {
                    if (col >= 5) {
                        enterLogic();
                        checkGameOver();
                    }
                } else if (keyPressed.equals("DELETE")) {
                    if (col > 0) {
                        backspaceLogic();

                        Label label = getLabelByIndex(board, row, col);

                        if (label != null) {
                            label.setText("");
                        }
                    }
                } else if (Character.isLetter(keyPressed.charAt(0)) && keyPressed.length() == 1) {
                    gameBoard.rowEnqueue(keyPressed);

                    Label label = getLabelByIndex(board, row, col);

                    if (label != null) {
                        label.setText(keyPressed);
                    }

                    if (col < 5) {
                        incrementCol();
                    }
                }
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // KEY LOGIC
    // ------------------------------------------------------------------------------------------------------------
    private void enterLogic() {
        // temporary word storage
        List<String> currWord = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            currWord.add(gameBoard.rowDequeue());
        }

        // push word to board
        gameBoard.boardPush(currWord.get(0) + currWord.get(1) + currWord.get(2) + currWord.get(3) + currWord.get(4));

        boolean valid = word.checkValidWord(gameBoard.boardPop().toLowerCase()); // check if word is valid

        if (valid) {
            col = 0;
            setLabelColors(word.getWord());
            incrementRow();
            incrementGuesses();
        } else {
            showPopup(stage, "/fxml/InvalidWordPopup.fxml");
            gameBoard.rowEnqueue(currWord.get(0));
            gameBoard.rowEnqueue(currWord.get(1));
            gameBoard.rowEnqueue(currWord.get(2));
            gameBoard.rowEnqueue(currWord.get(3));
            gameBoard.rowEnqueue(currWord.get(4));
        }
    }

    private void backspaceLogic() {
        hidePopup();
        gameBoard.rowBackspace();
        decrementCol();
    }

    // ------------------------------------------------------------------------------------------------------------
    // LABEL LOGIC
    // ------------------------------------------------------------------------------------------------------------
    private Label getLabelByIndex(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);

            if (rowIndex == null) {
                rowIndex = 0;
            }
            if (colIndex == null) {
                colIndex = 0;
            }
            if (rowIndex == row && colIndex == col && node instanceof Label) {
                return (Label) node;
            }
        }

        return null;
    }

    // ------------------------------------------------------------------------------------------------------------
    // INCREMENT / DECREMENT
    // ------------------------------------------------------------------------------------------------------------
    private void incrementRow() {
        ++row;
    }

    private void incrementCol() {
        ++col;
    }

    private void decrementCol() {
        --col;
    }

    private void incrementGuesses() {
        ++numGuesses;
    }

    // ------------------------------------------------------------------------------------------------------------
    // GAME LOGIC
    // ------------------------------------------------------------------------------------------------------------
    private void setLabelColors(String word) {
        for (int j = 0; j < 5; ++j) {
            Label label = getLabelByIndex(board, row, j);
            String letter = String.valueOf(word.charAt(j)).toUpperCase();

            if (label != null) {
                if (label.getText().equals(letter)) {
                    label.setStyle("-fx-background-color: #538d4e; -fx-border-color: #538d4e");
                    setKeyColors(label.getText(), "-fx-background-color: #538d4e;");
                } else if (word.contains(label.getText().toLowerCase())) {
                    label.setStyle("-fx-background-color: #b49f3a; -fx-border-color: #b49f3a");
                    setKeyColors(label.getText(), "-fx-background-color: #b49f3a;");
                } else {
                    label.setStyle("-fx-background-color: #3a3a3c; -fx-border-color: #3a3a3c");
                    setKeyColors(label.getText(), "-fx-background-color: #3a3a3c;");
                    wrongLetters.add(label.getText());
                }
            }
        }
    }

    private void setKeyColors(String letter, String color) {
        for (int j = 0; j < 10; ++j) {
            Label label1 = getLabelByIndex(keyrow1, 0, j);

            if (label1 != null) {
                if (label1.getText().equals(letter)) {
                    label1.setStyle(color);
                }
            }

            if (j < 9) {
                Label label2 = getLabelByIndex(keyrow2, 0, j);
                Label label3 = getLabelByIndex(keyrow3, 0, j);

                if (label2 != null) {
                    if (label2.getText().equals(letter)) {
                        label2.setStyle(color);
                    }
                }

                if (label3 != null) {
                    if (label3.getText().equals(letter)) {
                        label3.setStyle(color);
                    }
                }
            }
        }
    }

    private void checkGameOver() {
        if (numGuesses <= 6 && !win) {
            for (int j = 0; j < 5; ++j) {
                Label label = getLabelByIndex(board, row - 1, j);

                if (label != null) {
                    if (label.getStyle().equals("-fx-background-color: #538d4e; -fx-border-color: #538d4e")) {
                        gameOver = true;
                        win = true;
                    } else {
                        gameOver = false;
                        win = false;
                        break;
                    }
                }
            }

            // max guesses reached and game loss
            if (numGuesses == 6) {
                gameOver = true;
            }
        }

        if (gameOver) {
            statBoard.saveStats(win, numGuesses);
            showPopup(stage, "/fxml/StatBoard.fxml");
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // RESET GAME
    // ------------------------------------------------------------------------------------------------------------
    public void resetGame() {
        // reset all variables
        gameBoard = new GameBoard();
        word = new Word();
        statBoard = new StatBoard();
        row = 0;
        col = 0;
        numGuesses = 0;
        gameOver = false;
        win = false;
        wrongLetters.clear();
        hidePopup();

        // reset gameboard
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 5; ++j) {
                Label label = getLabelByIndex(board, i, j);
                label.setStyle("-fx-background-color: #121213");
                label.setText("");
            }
        }

        // reset keyboard
        for (int i = 0; i < 10; ++i) {
            Label label1 = getLabelByIndex(keyrow1, 0, i);
            label1.setStyle("-fx-background-color: #808384");

            if (i < 9) {
                Label label2 = getLabelByIndex(keyrow2, 0, i);
                label2.setStyle("-fx-background-color: #808384");

                Label label3 = getLabelByIndex(keyrow3, 0, i);
                label3.setStyle("-fx-background-color: #808384");
            }
        }
    }

    // hand cursor when hovering over reset button
    public void resetHover() {
        Cursor cursor = Cursor.HAND;
        reset.setCursor(cursor);
    }
}
