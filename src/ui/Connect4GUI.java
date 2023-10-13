package ui;

/**
 * A class for playing the connect4 game thru a gui 
 */
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;
import core.Connect4ComputerPlayer;
import core.Connect4Logic;
import core.Connect4Player;

public class Connect4GUI extends Application
{
    private char whoseTurn = 'X';
    private Cell[][] cell = new Cell[6][7];
    private Label lblStatus = new Label("X's turn to play");
    private static int directions[][] = {
            {1,0}, {1,-1}, {1,1}, {0,1}
    };

    /**
     * Sets up and begins the GUI connect4 game
     * @param the main stage for setting up GUI
     */
    public void start(Stage primaryStage)
    {

        GridPane pane = new GridPane();
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 7; j++)
                pane.add(cell[i][j] = new Cell(i, j), j, i);
        
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 7; j++)
                cell[i][j].next = cell[i+1][j];
        for (int i = 5; i > 4; i--)
            for (int j = 0; j < 7; j++)
                cell[i][j].next = cell[i][j];

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(lblStatus);
        Scene scene = new Scene(borderPane, 450, 450);
        primaryStage.setTitle("ConnectFour"); 
        primaryStage.setScene(scene); 
        primaryStage.show(); 
    }
    /** Determine if the game has resulted in a tie
     * 
     * */
    public boolean isFull()
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (cell[i][j].getToken() == ' ')
                    return false;

        return true;
    }
    /** 
     * Finds if a winner has been decided. 
     * @param token player who's moves are being checked for a win
     * @return Returns whether or not there is a winner
     */

    public boolean isWon(char token)
    {
        boolean isWinner = false;

        for (int[] d : directions) {
            int xdirection = d[0];
            int ydirection = d[1];
            for (int x = 0; x < 6; x++) {
                for (int y = 0; y < 7; y++) {
                    int finalx = x + 3*xdirection;
                    int finaly = y + 3*ydirection;
                    if (0 <= finalx && finalx < 6 && 0 <= finaly && finaly < 7) {
                        char w = cell[x][y].getToken();
                        if (w != ' ' && w == cell[x+xdirection][y+ydirection].getToken()
                                && w == cell[x+2*xdirection][y+2*ydirection].getToken()
                                && w == cell[finalx][finaly].getToken()) {
                            isWinner = true;
                            break;
                        }
                    }
                }
            }
        }
            return isWinner;
        
    }

    /**
     * Inner class for composing GUI connect4 board.
     *
     *
     */
    public class Cell extends Pane
    {
       
    	private int xPosition;
    	private int yPosition;
        private char token = ' ';
        public Cell next;
        /*
         * Basic constructor
         */
        public Cell()
        {
            setStyle("-fx-border-color: black");
            this.setPrefSize(2000, 2000);
            this.setOnMouseClicked(e -> handleMouseClick());
        }
        /**
         * Constructor that also passes board position information
         * @param x x coordinate on game board
         * @param y y coordinate on game board
         */
        public Cell(int x, int y) //Think about linking cells together aswell, taking param of cell below. If cell below is empty do not update
        {
        	this.xPosition = x;
        	this.yPosition = y;
            setStyle("-fx-border-color: black");
            this.setPrefSize(2000, 2000);
            this.setOnMouseClicked(e -> handleMouseClick());               
        }
        /**
         * Constructor that passes coordinate info and links cell to next cell
         * @param x x coordinate
         * @param y y coordinate
         * @param next linked to the cell below on game board
         */
        public Cell(int x, int y, Cell next) //Think about linking cells together aswell, taking param of cell below. If cell below is empty do not update
        {
        	this.xPosition = x;
        	this.yPosition = y;
        	this.next = next;
            setStyle("-fx-border-color: black");
            this.setPrefSize(2000, 2000);
            this.setOnMouseClicked(e -> handleMouseClick());
        }
        /**
         * Gets player token
         * @return current player's token
         */
        public char getToken() {
            return token;
        }
        /**
         * Sets player token onto board
         * @param c the players token that is being placed
         */
        public void setToken(char c)
        {
            token = c;
            if (token == 'X')
            {
                Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() -
                        10);
                line1.endXProperty().bind(this.widthProperty().subtract(10));
                line1.endYProperty().bind(this.heightProperty().subtract(10));
                Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10,
                        10);
                line2.startYProperty().bind(this.heightProperty().subtract(10));
                line2.endXProperty().bind(this.widthProperty().subtract(10));
                // Add the lines to the pane
                this.getChildren().addAll(line1, line2);
            }
            else if (token == 'O') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2,
                        this.getHeight() / 2, this.getWidth() / 2 - 10,
                        this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));

                ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));

                ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.WHITE);
                getChildren().add(ellipse); // Add the ellipse to the pane
            }
        }
        /**
         * Handles player mouse input for placing a piece only in legal spots
         */
        private void handleMouseClick()
        {
            if (token == ' ' && whoseTurn != ' ')
            {
            	int x = 0;
            	if(next.getToken() == ' ' && xPosition == 5) {
            		setToken(whoseTurn);
            		x =1;
            	}
            	if (next.getToken() != ' ' && xPosition < 5) {
                    setToken(whoseTurn); 
                    x=1;
            	}
            	if (next.getToken() == ' ' && xPosition <5 && !isWon(whoseTurn)) {
            		lblStatus.setText("Player " + whoseTurn + " please select legal position");
            	}

                if (isWon(whoseTurn)) {
                    lblStatus.setText(whoseTurn + " won! The game is over");
                    whoseTurn = ' ';
                }
                else if (isFull()) {
                    lblStatus.setText("Draw! The game is over");
                    whoseTurn = ' '; 
                }
                else if (x != 0) {
                    whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
                    lblStatus.setText(whoseTurn + "'s turn");
                }
            } else if(!isWon(whoseTurn)) {
            	lblStatus.setText("Player " + whoseTurn + " please select legal position");
            }
        }
    }
}