import java.util.List;
import java.util.Random;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Word {
    private String selectedWord;
    private List<String> wordList;

    // default constructor
    public Word() {
        selectedWord = "";
        setWord();
        System.out.println("Selected word: " + selectedWord);
    }

    public String getWord() {
        return selectedWord;
    }

    public void setWord() {
        try {
            wordList = Files.readAllLines(Paths.get("src/text/words.txt")); // read all words into a list
            Random random = new Random();
            selectedWord = wordList.get(random.nextInt(wordList.size())); // select a random word from the list
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLoadedWord(String word) {
        selectedWord = word;
    }

    public boolean checkValidWord(String word) {
        return wordList.contains(word);
    }
}
