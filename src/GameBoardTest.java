public class GameBoardTest {
    public static void main(String[] args) {
        GameBoard gameboard = new GameBoard();
        gameboard.rowEnqueue("h");
        gameboard.rowEnqueue("e");
        gameboard.rowEnqueue("l");
        gameboard.rowEnqueue("l");
        gameboard.rowEnqueue("o");
        gameboard.rowEnqueue("o");
        gameboard.rowEnqueue("h");
        gameboard.rowEnqueue("a");
        gameboard.rowEnqueue("l");
        gameboard.rowEnqueue("l");
        gameboard.rowEnqueue("o");
        gameboard.rowEnqueue("o");
        gameboard.boardPop();
        gameboard.boardPop();
    }
}
