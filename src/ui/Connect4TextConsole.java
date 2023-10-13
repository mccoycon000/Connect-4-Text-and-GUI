/**
 * A connect 4 game!
 * @author Connor McCoy
 * @version 1.0
 */

package ui;

import core.Connect4ComputerPlayer;
import core.Connect4Logic;
import core.Connect4Player;
import javafx.application.Application;

import java.util.Scanner;

public class Connect4TextConsole {
    /**
     * main method for playing the game
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("Begin Game.");

        Scanner scanner = new Scanner(System.in);

        Connect4Logic newGame = new Connect4Logic();
               
        System.out.println("Enter ‘G’ for JavaFX GUI or ’T’ for text UI");
        		char selection = scanner.next().charAt(0);
        		if (selection == 'G') {
        		   Application.launch(Connect4GUI.class);
        		}
        		else if (selection == 'T') {
        			displayGame(newGame);

        	        System.out.println("Begin Game. Enter P if you want to play against another player; enter C to \n" +
        	                "play against computer.");

        	        boolean correctAns = false;


        	        while (!correctAns) {
        	            try {
        	                String next = scanner.next();

        	                if (next.equals("P")) {
        	                    System.out.println("Start game vs. player");
        	                    playGame(newGame, scanner);
        	                    correctAns = true;
        	                    break;
        	                } else if (next.equals("C")) {
        	                    System.out.println("Start game vs. computer");
        	                    playComputer(newGame, scanner);
        	                    correctAns = true;
        	                    break;
        	                }
        	                System.out.println("Please select either 'P' or 'C'");
        	            } catch (Exception e) {
        	                System.out.println("Please input a valid answer.");
        	            }
        	        }
        		}
        		else {
        		   System.out.println("Invalid Entry!\n");
        		}

        
        System.out.println("Thanks for playing!");
        // close scanner
        scanner.close();
    }

    private static Scanner scanner;



    public Connect4TextConsole(Scanner scanner){
        this.scanner = scanner;
    }

    /**
     * Displays current game board
     * @param game input of the current board being played on
     */
    public static void displayGame(Connect4Logic game){


        for (int i = 5; i >= 0; i--) {
            System.out.print("|");
            for (int j = 0; j < 7; j++) {
                System.out.print(game.getPiece(i,j)+ "|");
            }
            System.out.println();

        }
    }

    /**
     * Starts a game between two players
     * @param newGame a new game being played
     * @param in scanner for receiving inputs
     */
    public static void playGame(Connect4Logic newGame, Scanner in){

        int turn = 0;

        while(turn < 42){

            Connect4Player currentPlayer = new Connect4Player();
            if(turn % 2 ==0) {
               currentPlayer = new Connect4Player(1);
            } else {
                currentPlayer = new Connect4Player(2);
            }

            int col = 0;

            while (true) {

                System.out.print("Player" + currentPlayer.getPlayer() + " your turn. Choose a column number from 1-7");
                String next = in.next();

                try {
                    col = Integer.parseInt(next) -1;
                } catch (Exception e) {
                    System.out.println("Please input a valid number (1-7).");
                    continue;
                }

                if (newGame.insertPiece(currentPlayer, col)) {
                    break;
                }

                System.out.println("Please input a valid number (1-7).");
            }

            displayGame(newGame);

            boolean isWinner = newGame.checkWinner(currentPlayer);
            if (isWinner){
                System.out.println("Player " + currentPlayer.getPlayer() + " wins");
                break;
            }
            turn++;
            if (turn == 42){
                System.out.println("No winner; tie");
                break;
            }
    }
    }

    /**
     * Starts a game vs. a computer player
     * @param newGame the new game being played
     * @param in scanner for inputting moves
     */
    public static void playComputer(Connect4Logic newGame, Scanner in){

        int turn = 0;

        while(turn < 42){

            Connect4Player currentPlayer = new Connect4Player();
            Connect4ComputerPlayer computerPlayer = new Connect4ComputerPlayer();
            if(turn % 2 ==0) {
                currentPlayer = new Connect4Player(1);
            } else {
                currentPlayer = new Connect4Player(2);
            }

            int col = 0;

            while (currentPlayer.getPlayerNum() == 1) {

                System.out.print("Player" + currentPlayer.getPlayer() + " your turn. Choose a column number from 1-7");
                String next = in.next();

                try {
                    col = Integer.parseInt(next) -1;
                } catch (Exception e) {
                    System.out.println("Please input a valid number (1-7).");
                    continue;
                }

                if (newGame.insertPiece(currentPlayer, col)) {
                    break;
                }

                System.out.println("Please input a valid number (1-7).");
            }

            while (currentPlayer.getPlayerNum() == 2) {

                System.out.println("ComputerPlayer Turn");

                try {
                    if (computerPlayer.computerMove(newGame, currentPlayer)){
                        break;
                    }else {
                        System.out.println("Invalid move silly computer, go again");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid move silly computer");
                    continue;
                }
            }

            displayGame(newGame);

            boolean isWinner = newGame.checkWinner(currentPlayer);
            if (isWinner){
                if(currentPlayer.getPlayerNum() == 1) {
                    System.out.println("Player " + currentPlayer.getPlayer() + " wins");
                    break;
                } else if (currentPlayer.getPlayerNum() == 2){
                    System.out.println("Computer wins");
                    break;
                }
            }
            turn++;
            if (turn == 42){
                System.out.println("No winner; tie");
                break;
            }
        }
    }
}
