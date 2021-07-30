package tictactoe;

import javafx.util.Pair;

import java.util.ArrayList;

public class AI {

    /**
     * This class is for an AI player. It contains a single public method, which takes in a grid and it will choose the
     * best spot to play its next move
     */

    /**
     * Chooses a place to play a move on the given grid
     * @param nonFlatBoard grid
     * @return a pair with key the index of the column to play, value the row to play
     */
    public Pair<Integer, Integer> chooseSquare(int[][] nonFlatBoard) {

        int index = 0;
        int[] board = new int[9];

        // First of all flatten the board, to make it easier to implement the algorithm
        for (int[] column : nonFlatBoard) {

            for (int row : column) {
                board[index] = row;
                index++;
            }
        }

        int playIndex = minimax(board, 1).getKey(); // Retrieve the index to the spot to play
        return new Pair<Integer, Integer>((int) playIndex / 3, playIndex % 3);
    }

    /**
     * Finds the index with the maximum score and returns it, along with the score if is the AI player's turn, otherwise
     * finds the index of the minimum score and returns it, along with the score
     * @param board grid
     * @param player current player, 1 if AI, 2 if user
     * @return pair containing index and max/min score
     */
    private Pair<Integer, Integer> minimax(int[] board, int player) {
        // ArrayList of pairs containing indexes and the outcome of playing a move there
        ArrayList<Pair<Integer, Integer>> scores = new ArrayList<Pair<Integer, Integer>>();

        for (int i : emptySpaces(board)) {
            scores.add(new Pair<Integer, Integer>(i, playingOutcome(board, i, player))); // Add indexes of empty spaces
            // and outcomes
        }

        if (player == 1) { // If the current player if the AI, find the index with the highest score and return it
            Pair<Integer, Integer> maxScorePair = scores.get(0);

            for (Pair<Integer, Integer> score : scores) {

                if (score.getValue() > maxScorePair.getValue()) {
                     maxScorePair = score;
                }
            }

            return maxScorePair;
        }

        // If the current player is the user, find the index with the lowest score and return it
        Pair<Integer, Integer> minScorePair = scores.get(0);

        for (Pair<Integer, Integer> score : scores) {

            if (score.getValue() < minScorePair.getValue()) {
                minScorePair = score;
            }
        }

        return minScorePair;
    }

    /**
     * Makes the move and returns the outcome of making that move
     * @param board grid
     * @param playIndex index of spot to make the move
     * @param player the current player
     * @return 10 if AI wins, -10 if user wins, 0 if draw
     */
    private int playingOutcome(int[] board, int playIndex, int player) {
        // Make a new array to play that move
        int[] newBoard = new int[9];
        System.arraycopy(board, 0, newBoard, 0, board.length);

        newBoard[playIndex] = player; // Play that move
        int winner = 0;

        if (newBoard[0] == newBoard[1] && newBoard[1] == newBoard[2] && newBoard[0] != 0) {
            winner = newBoard[0];
        }

        if (newBoard[3] == newBoard[4] && newBoard[4] == newBoard[5] && newBoard[3] != 0) {
            winner = newBoard[3];
        }

        if (newBoard[6] == newBoard[7] && newBoard[7] == newBoard[8] && newBoard[6] != 0) {
            winner = newBoard[6];
        }

        if (newBoard[0] == newBoard[3] && newBoard[3] == newBoard[6] && newBoard[0] != 0) {
            winner = newBoard[0];
        }

        if (newBoard[1] == newBoard[4] && newBoard[4] == newBoard[7] && newBoard[1] != 0) {
            winner = newBoard[1];
        }

        if (newBoard[2] == newBoard[5] && newBoard[5] == newBoard[8] && newBoard[2] != 0) {
            winner = newBoard[2];
        }

        if (newBoard[0] == newBoard[4] && newBoard[4] == newBoard[8] && newBoard[0] != 0) {
            winner = newBoard[0];
        }

        if (newBoard[2] == newBoard[4] && newBoard[4] == newBoard[6] && newBoard[2] != 0) {
            winner = newBoard[2];
        }


        if (winner == 1) { // If winner is AI return 10
            return 10;
        }

        else if (winner == 2) { // If winner is Player return -10
            return -10;
        }

        else if (emptySpaces(newBoard).size() == 0) { // If there are no more empty spaces return 0
            return 0;
        }

        else { // If no winner, switch the current player and use recursion to find the eventual worst/best outcome

            if (player == 1) {
                player = 2;
            }

            else {
                player = 1;
            }

            return minimax(newBoard, player).getValue();
        }
    }

    /**
     * Returns the empty spaces in the given board
     * @param board grid
     * @return ArrayList of the indexes of the empty spaces in this board
     */
    private ArrayList<Integer> emptySpaces(int[] board) {
        ArrayList<Integer> emptySpaces = new ArrayList<Integer>();

        for (int i = 0; i < board.length; i++) {

            if (board[i] == 0) {
                emptySpaces.add(i);
            }
        }

        return emptySpaces;
    }

}
