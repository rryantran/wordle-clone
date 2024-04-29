import java.util.LinkedList;
import java.util.Stack;

public class GameBoard {
    private Stack<String> gameBoard;
    private LinkedList<String> gameRow;
    private int iter;

    // default constructor
    public GameBoard() {
        gameBoard = new Stack<String>();
        gameRow = new LinkedList<String>();
        iter = 0;
    }

    // enqueue a letter to the row
    public void rowEnqueue(String c) {
        if (iter < 5) {
            gameRow.add(c); // add to row if letter limit not reached
            ++iter;
        }
    }

    // dequeue a letter from the row
    public String rowDequeue() {
        return gameRow.removeFirst();
    }

    // remove last letter from the row
    public String rowBackspace() {
        --iter;
        return gameRow.removeLast();
    }

    // push the finished row to the board
    public void boardPush(String s) {
        gameBoard.push(s);
        iter = 0; // reset for next row
    }

    // pop the most recent word from the board
    public String boardPop() {
        return gameBoard.pop();
    }

    // get the current iterator
    public int getIterator() {
        return iter;
    }
}
