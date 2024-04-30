import java.util.LinkedList;
import java.util.Stack;

public class GameBoard {
    private Stack<String> gameBoard;
    private LinkedList<String> gameRow;
    private int iterator;

    // default constructor
    public GameBoard() {
        gameBoard = new Stack<String>();
        gameRow = new LinkedList<String>();
        iterator = 0;
    }

    // get the current row
    public LinkedList<String> getRow() {
        return gameRow;
    }

    // add a letter to the back of the row
    public void rowEnqueue(String c) {
        if (iterator < 5) {
            gameRow.add(c); // add to row if letter limit not reached
            ++iterator;
        }
    }

    // remove first from the row
    public String rowDequeue() {
        return gameRow.removeFirst();
    }

    // remove last letter from the row
    public String rowBackspace() {
        --iterator;
        return gameRow.removeLast();
    }

    // push the finished row to the board
    public void boardPush(String s) {
        gameBoard.push(s);
        iterator = 0; // reset for next row
    }

    // pop the most recent word from the board
    public String boardPop() {
        return gameBoard.pop();
    }

    // get the current iterator
    public int getIterator() {
        return iterator;
    }
}
