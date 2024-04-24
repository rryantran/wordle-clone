import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class Controller {
    GameBoard gameBoard = new GameBoard();
    Word word = new Word();
    int row = 0;
    int col = 0;

    @FXML
    GridPane board;

    // ------------------------------------------------------------------------------------------------------------
    // KEY EVENT HANDLER
    // ------------------------------------------------------------------------------------------------------------
    public void handleKeyPress(KeyEvent e) {
        String keyPressed = e.getCode().toString(); // get pressed key

        if (keyPressed == "ENTER") {
            if (col >= 5) {
                enterLogic();
            }
        }
        if (keyPressed == "BACK_SPACE") {
            if (col > 0) {
                backspaceLogic();

                Label label = getLabelByIndex(board, row, col);

                if (label != null) {
                    label.setText("");
                }
            }
        }
        if (Character.isLetter(keyPressed.charAt(0)) && keyPressed.length() == 1) {
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

    // ------------------------------------------------------------------------------------------------------------
    // LABEL LOGIC
    // ------------------------------------------------------------------------------------------------------------
    private Label getLabelByIndex(GridPane gridPane, int row, int col) {
        for (Node node : board.getChildren()) {
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
    // ROW + COL LOGIC
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

    // ------------------------------------------------------------------------------------------------------------
    // KEY LOGIC
    // ------------------------------------------------------------------------------------------------------------
    private void enterLogic() {
        String[] currWord = new String[5];
        for (int i = 0; i < 5; i++) {
            currWord[i] = gameBoard.rowDequeue();
        }

        gameBoard.boardPush(currWord[0] + currWord[1] + currWord[2] + currWord[3] + currWord[4]); // push the 5 letter word to the board

        boolean valid = word.checkValidWord(gameBoard.boardPop().toLowerCase()); // check if word is valid

        if (valid) {
            col = 0;
            incrementRow();
        } else {
            System.out.println("Invalid word");
            gameBoard.rowEnqueue(currWord[0]);
            gameBoard.rowEnqueue(currWord[1]);
            gameBoard.rowEnqueue(currWord[2]);
            gameBoard.rowEnqueue(currWord[3]);
            gameBoard.rowEnqueue(currWord[4]);
        }
    }

    private void backspaceLogic() {
        gameBoard.rowBackspace();
        decrementCol();
    }
}
