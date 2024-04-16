import java.util.ArrayList;

public class GameboardTest {
    public static void main(String[] args) {
        Gameboard gameboard = new Gameboard();
        ArrayList<Character> word = new ArrayList<>();

        word.add('h');
        word.add('e');
        word.add('l');
        word.add('l');
        word.add('o');
        gameboard.push(word);
        
        ArrayList<Character> poppedWord = gameboard.pop();
        System.out.println(poppedWord);
    }
}
