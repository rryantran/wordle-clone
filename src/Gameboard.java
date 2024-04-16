import java.util.Stack;
import java.util.ArrayList;

public class Gameboard {
    private Stack<ArrayList<Character>> gameBoard;
    
    public Gameboard() {
        gameBoard = new Stack<>();
    }

    public void push(ArrayList<Character> word) {
        gameBoard.push(word);
    }

    public ArrayList<Character> pop() {
        return gameBoard.pop();
    }
}
