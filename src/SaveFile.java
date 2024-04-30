import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SaveFile {
    List<String> save;

    public SaveFile() {
        save = new ArrayList<String>();
    }

    public void loadSave(String savefile) {
        try {
            save = Files.readAllLines(Paths.get(savefile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeSave(String selectedWord, List<String> wrongLetters, int row, int col, int numGuesses, Queue<String> gameHistory) {
        save.add(selectedWord);
        
        for (String letter : wrongLetters) {
            save.add(letter);
        }
        
        save.add(Integer.toString(row));
        save.add(Integer.toString(col));
        save.add(Integer.toString(numGuesses));

        for (String word : gameHistory) {
            save.add(word);
        }
    }

    public void saveSave(String savefile) {
        try {
            Files.write(Paths.get(savefile), save);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getSave() {
        return save;
    }
}