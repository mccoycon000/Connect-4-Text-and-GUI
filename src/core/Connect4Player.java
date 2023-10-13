package core;

/**
 * This is the palyer class it is seperated from other classes for better organization and possible scalability.
 * @author Connor McCoy
 * @version 1.0
 */
public class Connect4Player {

    private int playerNum;

    /**
     * basic constructor sets player to 0 or 'empty'
     */
    public Connect4Player(){
        this.playerNum = 0;
    }

    /**
     * constructor
     * @param value which player
     */
    public Connect4Player(int value){
        this.playerNum = value;
    }

    public int getPlayerNum(){
        return this.playerNum;
    }

    public char getPlayer(){
        if(playerNum == 0) {
            return ' ';
        }
        if(playerNum == 1) {
            return 'X';
        }
        if(playerNum == 2) {
            return 'O';
        }
        return ' ';
    }
}
