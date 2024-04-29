import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StatBoard {
    private List<String> statList;
    private int gamesPlayed;
    private int gamesWon;
    private int oneGuess;
    private int twoGuess;
    private int threeGuess;
    private int fourGuess;
    private int fiveGuess;
    private int sixGuess;

    // default constructor
    public StatBoard() {
        loadStats();
        gamesPlayed = Integer.parseInt(statList.get(0));
        gamesWon = Integer.parseInt(statList.get(1));
        oneGuess = Integer.parseInt(statList.get(2));
        twoGuess = Integer.parseInt(statList.get(3));
        threeGuess = Integer.parseInt(statList.get(4));
        fourGuess = Integer.parseInt(statList.get(5));
        fiveGuess = Integer.parseInt(statList.get(6));
        sixGuess = Integer.parseInt(statList.get(7));
    }

    public List<String> getStats() {
        return statList;
    }

    private void loadStats() {
        try {
            statList = Files.readAllLines(Paths.get("src/text/stats.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveStats(boolean win, int numGuesses) {
        try {
            List<String> stats = Files.readAllLines(Paths.get("src/text/stats.txt")); // read stats into a list
            stats.set(0, String.valueOf(++gamesPlayed)); // +1 games played
            if (win) {
                // +1 games won if win condition is set
                stats.set(1, String.valueOf(++gamesWon));
                // find index for number of guesses
                int index = ++numGuesses;
                // +1 games won for number of guesses
                stats.set(index, String.valueOf(Integer.parseInt(stats.get(index)) + 1));
            }
            Files.write(Paths.get("src/text/stats.txt"), stats);
            loadStats();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
