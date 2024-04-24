import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Word {
    private String selectedWord;
    private List<String> wordList;

    // default constructor
    public Word() {
        selectedWord = "";
        setWord();
        System.out.println("Selected word: " + selectedWord);
    }

    public void setWord() {
        try {
            wordList = Files.readAllLines(Paths.get("src/words.txt")); // read all words into a list
            Random random = new Random();
            selectedWord = wordList.get(random.nextInt(wordList.size())); // select a random word from the list
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getWord() {
        return selectedWord;
    }

    public boolean checkValidWord(String word) {
        return wordList.contains(word);
    }
}
