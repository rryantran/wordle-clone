import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    GameBoard gameBoard = new GameBoard();
    Word word = new Word();
    List<String> wrongLetters = new ArrayList<>();
    int row = 0;
    int col = 0;
    int numGuesses = 0;
    boolean gameOver = false;

    @FXML
    GridPane board;
    @FXML
    GridPane keyrow1;
    @FXML
    GridPane keyrow2;
    @FXML
    GridPane keyrow3;
    @FXML
    Button reset;

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
                    gameBoard.rowEnqueue(e.getCode().toString());

                    Label label = getLabelByIndex(board, row, col);

                    if (label != null) {
                        label.setText(e.getCode().toString());
                    }

                    if (col < 5) {
                        incrementCol();
                    }
                }
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // LABEL LOGIC
    // ------------------------------------------------------------------------------------------------------------
    private Label getLabelByIndex(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);

            if (rowIndex == null)
                rowIndex = 0;
            if (colIndex == null)
                colIndex = 0;
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
            System.out.println("Invalid word");
            gameBoard.rowEnqueue(currWord.get(0));
            gameBoard.rowEnqueue(currWord.get(1));
            gameBoard.rowEnqueue(currWord.get(2));
            gameBoard.rowEnqueue(currWord.get(3));
            gameBoard.rowEnqueue(currWord.get(4));
        }
    }

    private void backspaceLogic() {
        gameBoard.rowBackspace();
        decrementCol();
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
        if (numGuesses == 6) {
            gameOver = true;
        } else {
            for (int j = 0; j < 5; ++j) {
                Label label = getLabelByIndex(board, row - 1, j);

                if (label != null) {
                    if (label.getStyle().equals("-fx-background-color: #538d4e; -fx-border-color: #538d4e")) {
                        gameOver = true;
                    } else {
                        gameOver = false;
                        break;
                    }
                }
            }
        }

        if (gameOver) {
            System.out.println("Game Over");
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // RESET GAME
    // ------------------------------------------------------------------------------------------------------------
    public void resetGame() {
        gameBoard = new GameBoard();
        word = new Word();
        row = 0;
        col = 0;
        numGuesses = 0;
        gameOver = false;
        wrongLetters.clear();

        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 5; ++j) {
                Label label = getLabelByIndex(board, i, j);
                label.setStyle("-fx-background-color: #121213");
                label.setText("");
            }
        }

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

    public void resetHover() {
        Cursor cursor = Cursor.HAND;
        reset.setCursor(cursor);
    }
}
