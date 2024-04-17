import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GameBoard {
    private Stack<String> gameBoard;
    private Queue<String> gameRow;
    private int i;

    // default constructor
    public GameBoard() {
        gameBoard = new Stack<String>();
        gameRow = new LinkedList<String>();
        i = 0;
    }

    public void rowEnqueue(String c) {
        if (i < 5) {
            gameRow.add(c); // add to row if letter limit not reached
            ++i;

            System.out.println("Added to current row: " + c);
            System.out.println("I: " + i);
        } else {
            System.out.println("Row filled. Resetting...");
            // add to stack if letter limit is reached and move to next row
            boardPush(rowDequeue() + rowDequeue() + rowDequeue() + rowDequeue() + rowDequeue());

            System.out.println("Added to stack: " + gameBoard.peek());
            System.out.println("Reset I: " + i);
        }
    }

    public String boardPop() {
        System.out.println("Popping: " + gameBoard.peek());
        return gameBoard.pop();
    }

    private String rowDequeue() {
        return gameRow.poll();
    }

    private void boardPush(String s) {
        gameBoard.push(s);
        i = 0;
    }
}
