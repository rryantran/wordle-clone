import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GameBoard {
    private Stack<String> gameBoard;
    private Queue<String> gameRow;
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
        return gameRow.poll();
    }

    // push the finished row to the board
    public void boardPush(String s) {
        gameBoard.push(s);
        iter = 0; // reset for next row
    }

    // pop the most recent word from the board
    public String boardPop() {
        System.out.println("Most Recent: " + gameBoard.peek());
        return gameBoard.pop();
    }

    // get the current iterator
    public int getIterator() {
        return iter;
    }

    // increment the iterator
    public void decrementIterator() {
        --iter;
    }
}
