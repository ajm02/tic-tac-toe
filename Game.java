package tictactoe;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    /**
     * This class is responsible for handling the tictactoe game
     */

    private ArrayList<Square> squares; // The squares in this game
    private StringProperty displayText; // The text to be displayed in the game scene
    private boolean gameOver;
    private int currentPlayer; // 1 if the current player is the AI, 2 if the current player is the user
    private int[][] grid; // The game grid as a 2D array, 0 in a spot means spot is unoccupied, 1 means occupied by
    // an AI player, 2 means occupied by the user
    private AI ai; // The AI player

    /**
     * New Game
     */
    public Game() {
        gameOver = false;
        ai = new AI();
        grid = new int[3][3];
        squares = new ArrayList<Square>();
        currentPlayer = new Random().nextInt(2) + 1; // Randomise the starting player
        displayText = new SimpleStringProperty();

        if (currentPlayer == 1) {
            displayText.set("AI's turn to make a move");
        }

        else {
            displayText.set("Your turn to make a move");
        }
    }

    /**
     * Add a square to the game's list of squares
     * @param square sqaure to add
     */
    public void addSquare(Square square) {
        squares.add(square);
    }

    /**
     * Check if a move can be made in the given grid spot
     * @param col column of the grid
     * @param row row of the grid
     * @return true if the move can be made, false otherwise
     */
    private boolean isValidMove(int col, int row) {

        if (grid[col][row] != 0 || gameOver) { // If the spot is not empty, or the game is over, a move cannot be made
            return false;
        }

        return true;
    }

    /**
     * Play a move in the given grid spot
     * @param col column of the grid
     * @param row row of the grid
     */
    public void movePlayed(int col, int row) {

        if (isValidMove(col, row)) {
            grid[col][row] = currentPlayer; // Plays the move by changing the spot in the grid to that of the current player

            // Draw a cross or circle on that particular square and change the current player
            for (Square square : squares) {

                if (square.getColumn() == col && square.getRow() == row) {

                    if (currentPlayer == 1) {
                        square.drawCross();
                        currentPlayer = 2;
                    }

                    else {
                        square.drawCircle();
                        currentPlayer = 1;
                    }
                }
            }

            if (currentPlayer == 1) {
                displayText.set("AI's turn to make a move");
            }

            else {
                displayText.set("Your turn to make a move");
            }

            // If there is a winner, game is over and the appropriate message displayed
            if (checkWinner() != 0) {
                gameOver = true;

                if (checkWinner() == 1) {
                    displayText.set("You Lose!");
                }

                else {
                    displayText.set("You broke the game");
                }
            }

            // If the board is full, game is over and the appropriate message is displayed
            else if (boardFull()) {
                gameOver = true;
                displayText.set("It's a draw!");
            }

            // If the game is not over and it's the AI's turn, have the AI make a move
            if (currentPlayer == 1 && !gameOver) {
                makeAIMove();
            }
        }
    }

    /**
     * Checks the board to see if there is a winner
     * @return 0 if there is no winner, 1 if AI wins, 2 if the player wins
     */
    private int checkWinner() {

        // Check complete columns
        for (int i = 0; i < 3; i++) {

            if (grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2] && grid[i][0] != 0) {

                for (Square square : squares) { // Cross out the complete column

                    if (square.getColumn() == i) {
                        square.crossOutVer();
                    }
                }

                return grid[i][0];
            }
        }

        // Check complete rows
        for (int i = 0; i < 3; i++) {

            if (grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i] && grid[0][i] != 0) {

                for (Square square : squares) { // Cross out the complete row

                    if (square.getRow() == i) {
                        square.crossOutHor();
                    }
                }

                return grid[0][i];
            }
        }

        // Check diagonal
        if (grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && grid[0][0] != 0) {

            for (Square square : squares) { // Cross out the complete diagonal

                if (square.getRow() == square.getColumn()) {
                    square.crossOutDia();
                }
            }

            return grid[0][0];
        }

        // Check reverse diagonal
        if (grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0] && grid[0][2] != 0) {

            for (Square square : squares) {

                if (square.getRow() ==  2 - square.getColumn()) { // Cross out the complete diagonal
                    square.crossOutRevDia();
                }
            }

            return grid[0][2];
        }

        return 0;
    }

    /**
     * Checks if the board is full
     * @return true if the board is full, false otherwise
     */
    private boolean boardFull() {

        for (int[] column : grid) {

            for (int row : column) {

                if (row == 0) { // If a single spot is 0, the board is not full
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Gets the AI to chose a spot and play a move there
     */
    public void makeAIMove() {
        Pair<Integer, Integer> coords = ai.chooseSquare(grid);
        movePlayed(coords.getKey(), coords.getValue());
    }

    /**
     * Gets the text to display in the game scene
     * @return the text to display
     */
    public StringProperty getDisplayText() {
        return displayText;
    }

    /**
     * Gets the current player
     * @return the number of the current player
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }
}
