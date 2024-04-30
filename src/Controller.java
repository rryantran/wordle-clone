import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

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
    private GameHistory gameHistory = new GameHistory();
    private StatBoard statBoard = new StatBoard();
    private Word word = new Word();
    private SaveFile saveFile = new SaveFile();
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
    @FXML
    private Button load;
    @FXML
    private Button save;

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
                    } else {
                        showPopup(stage, "/fxml/TooShortPopup.fxml");
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
                    hidePopup();
                    if (col < 5) {
                        gameBoard.rowEnqueue(keyPressed);
                        gameHistory.addLetter(keyPressed);

                        Label label = getLabelByIndex(board, row, col);

                        if (label != null) {
                            label.setText(keyPressed);
                        }

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
                    } else {
                        showPopup(stage, "/fxml/TooShortPopup.fxml");
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
                    hidePopup();
                    if (col < 5) {
                        gameBoard.rowEnqueue(keyPressed);
                        gameHistory.addLetter(keyPressed);

                        Label label = getLabelByIndex(board, row, col);

                        if (label != null) {
                            label.setText(keyPressed);
                        }

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
            setLabelColors(word.getWord(), row);
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
        gameHistory.removeLetterLast();
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
    private void setLabelColors(String word, int row) {
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
            load.setDisable(true);
            save.setDisable(true);
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // RESET GAME
    // ------------------------------------------------------------------------------------------------------------
    public void resetGame() {
        // reset all variables
        gameHistory.clearLog();
        gameBoard = new GameBoard();
        gameHistory = new GameHistory();
        statBoard = new StatBoard();
        word = new Word();
        saveFile = new SaveFile();
        row = 0;
        col = 0;
        numGuesses = 0;
        gameOver = false;
        win = false;
        wrongLetters.clear();
        hidePopup();
        load.setDisable(false);
        save.setDisable(false);

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

    // ------------------------------------------------------------------------------------------------------------
    // LOAD / SAVE
    // ------------------------------------------------------------------------------------------------------------
    public void saveGame() {
        saveFile.makeSave(word.getWord(), wrongLetters, row, col, numGuesses, gameHistory.getGameHistory());
        saveFile.saveSave("src/text/save.txt");
    }

    public void loadGame() {
        saveFile.loadSave("src/text/save.txt");
        List<String> saveToLoad = saveFile.getSave();
        LinkedList<String> gameHistoryCopy = new LinkedList<String>();
        int j = 0;

        // load save state into current game variables
        word.setLoadedWord(saveToLoad.get(0));
        for (int i = 1; i < saveToLoad.size(); ++i) {
            String currItem = saveToLoad.get(i);
            if (Character.isLetter(currItem.charAt(0)) && currItem.length() == 1 && j == 0) {
                wrongLetters.add(currItem);
            } else if (Character.isDigit(currItem.charAt(0))) {
                if (j == 0) {
                    row = Integer.parseInt(currItem);
                    ++j;
                } else if (j == 1) {
                    col = Integer.parseInt(currItem);
                    ++j;
                } else if (j == 2) {
                    numGuesses = Integer.parseInt(currItem);
                    ++j;
                }
            } else {
                gameHistory.addLetter(currItem);
            }
        }

        // copy game history
        for (String item : gameHistory.getGameHistory()) {
            gameHistoryCopy.add(item);
        }

        // load saved rows (completed guesses)
        for (int i = 0; i < row; ++i) {
            for (int k = 0; k < 5; ++k) {
                Label label = getLabelByIndex(board, i, k);
                label.setText(gameHistoryCopy.removeFirst());
            }

            setLabelColors(word.getWord(), i);
        }
        // load saved row (incomplete guess)
        for (int i = 0; i < col; ++i) {
            Label label = getLabelByIndex(board, row, i);
            label.setText(gameHistoryCopy.removeFirst());
            gameBoard.rowEnqueue(label.getText());
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // MISC
    // ------------------------------------------------------------------------------------------------------------
    public void hover() {
        // hand cursor when hovering over button
        Cursor cursor = Cursor.HAND;
        reset.setCursor(cursor);
        load.setCursor(cursor);
        save.setCursor(cursor);
    }
}
