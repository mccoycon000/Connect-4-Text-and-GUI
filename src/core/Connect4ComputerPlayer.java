package core;
/**
 * ComputerPlayer class
 * @author Connor McCoy
 * @version 1.0
 */
public class Connect4ComputerPlayer {

    private int playerNum = 3;

    public Connect4ComputerPlayer() {
        this.playerNum = 3;
    }

    /**
     * Inserts piece in a random column to make the computers move
     * @param board the current board being played 0n
     * @param player denotes players turn for correct piece to be placed into board
     * @return return whether the piece was successfully inserted.
     */
    public boolean computerMove (Connect4Logic board, Connect4Player player){
        if (board.insertPiece(player, (int) (Math.random() * 7))){
            return true;
        }
        return false;
    }
}
