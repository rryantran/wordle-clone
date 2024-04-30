import java.util.LinkedList;
import java.util.Queue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class GameHistory {
    private LinkedList<String> gameHistory;

    public GameHistory() {
        gameHistory = new LinkedList<String>();
        clearLog();
    }

    public void addLetter(String s) {
        gameHistory.add(s);
        writeToLog(s);
    }

    public void writeToLog(String s) {
        // write move to log of current game
        try {
            Files.write(Paths.get("src/text/gamelog.txt"), (s + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearLog() {
        try {
            Files.write(Paths.get("src/text/gamelog.txt"), "".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
