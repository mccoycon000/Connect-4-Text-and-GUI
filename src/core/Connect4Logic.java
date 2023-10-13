package core;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the main logic for connect 4 game.
 * @author Connor McCoy
 * @version 1.0
 */

public class Connect4Logic {

    private static final int NO_ROWS = 6;
    private static final int NO_COLS = 7;
    private static final int POS_DIR = 4;

    private static int directions[][] = {
            {1,0}, {1,-1}, {1,1}, {0,1}
    };

    private List<int[]> possibleDirections[][];

    private Connect4Player board[][];

    /**
     * Constructor builds new board
     */

    public Connect4Logic() {
        buildBoard();
    }

    /**
     * gets the piece at any spot on the board
     *
     * @param i x component
     * @param j y component
     * @return what piece is at that spot on the board
     */
    public char getPiece(int i, int j) {
        return board[i][j].getPlayer();
    }

    /**
     * Building the board out of Player objects then building an array for each spot contains its possible directions
     * for a connect 4.
     */
    public void buildBoard() {

        board = new Connect4Player[NO_ROWS][NO_COLS];

        for (int i = 0; i < NO_ROWS; i++) {
            for (int j = 0; j < NO_COLS; j++) {
                board[i][j] = new Connect4Player(0);
            }

        }

    }

    /**
     * method used to insert piece at selected col
     * starts at the top of the row and check each spot downward
     *
     * @param player the player whose turn  it is
     * @param col    the col that they want to drop their piece in
     * @return whether it was successful
     */
    public boolean insertPiece(Connect4Player player, int col) {
        if (col < 0 || col >= 7) {
            return false;
        }
        int i;
        for (i = 5; i >= 0; i--) {
            if (board[i][col].getPlayerNum() != 0) {
                i++;
                break;
            }
        }
        if (i == 6) {
            return false;
        } else if (i == -1) {
            i++;
        }

        board[i][col] = player;

        return true;
    }


    /**
     * checks the whole board to see if there are any spots where there are the same type of piece in 4 consecutive spots
     *in each direction.
     * @param player the current player
     * @return if there is winner
     */
    public boolean checkWinner(Connect4Player player) {
        boolean isWinner = false;

        for (int[] d : directions) {
            int xdirection = d[0];
            int ydirection = d[1];
            for (int x = 0; x < NO_ROWS; x++) {
                for (int y = 0; y < NO_COLS; y++) {
                    int finalx = x + 3*xdirection;
                    int finaly = y + 3*ydirection;
                    if (0 <= finalx && finalx < NO_ROWS && 0 <= finaly && finaly < NO_COLS) {
                        int w = board[x][y].getPlayerNum();
                        if (w != 0 && w == board[x+xdirection][y+ydirection].getPlayerNum()
                                && w == board[x+2*xdirection][y+2*ydirection].getPlayerNum()
                                && w == board[finalx][finaly].getPlayerNum()) {
                            isWinner = true;
                            break;
                        }
                    }
                }
            }
        }
            return isWinner;
        }
    }


