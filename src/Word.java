import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Word {
    private String selectedWord;

    // default constructor
    public Word() {
        selectedWord = "";
    }

    public String getWord() {
        return selectedWord;
    }

    public void setWord() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/words.txt")); // read all words into a list
            Random random = new Random();
            selectedWord = lines.get(random.nextInt(lines.size())); // select a random word from the list
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
