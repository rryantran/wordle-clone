import java.util.LinkedList;
import java.util.Queue;

public class GameHistory {
    private LinkedList<String> gameHistory;

    public GameHistory() {
        gameHistory = new LinkedList<String>();
    }

    public void addLetter(String s) {
        gameHistory.add(s);
    }

    public String removeLetterLast() {
        return gameHistory.removeLast();
    }

    public String removeLetterFirst() {
        return gameHistory.removeFirst();
    }

    public Queue<String> getGameHistory() {
        return gameHistory;
    }
}
