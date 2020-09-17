package co.edu.unal.tictactoe;

import java.util.Random;

public class TicTacToeGame {
    public char[] gameBoard;
    public static final int BOARD_SIZE = 9;

    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char OPEN_SPOT = ' ';
    private Random randomNumber;

    public TicTacToeGame() {
        gameBoard = new char[BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++){
            gameBoard[i] = OPEN_SPOT;
        }

        randomNumber = new Random();
    }

    /** Clear the board of all X's and O's by setting all spots to OPEN_SPOT. */
    public void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            gameBoard[i] = OPEN_SPOT;
        }
    }

    /**
     * Set the given player at the given location on the game board. The
     * location must be available, or the board will not be changed.
     *
     * @param player
     *            - The HUMAN_PLAYER or COMPUTER_PLAYER
     * @param location
     *            - The location (0-8) to place the move
     */
    public void setMove(char player, int location) {
         gameBoard[location] = player;
        }


    /**
     * Return the best move for the computer to make. You must call setMove() to
     * actually make the computer move to that location.
     *
     * @return The best move for the computer to make (0-8).
     */
    /**
     * Return the best move for the computer to make. You must call setMove() to
     * actually make the computer move to that location.
     *
     * @return The best move for the computer to make (0-8).
     */
    public int getComputerMove() {
        int move;

        // First see if there's a move O can make to win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (gameBoard[i] != HUMAN_PLAYER && gameBoard[i] != COMPUTER_PLAYER) {
                gameBoard[i] = COMPUTER_PLAYER;
                if (checkForWinner() == 3) {
                    gameBoard[i] = OPEN_SPOT;
                    return i;
                }
                gameBoard[i] = OPEN_SPOT;
            }
        }

        // See if there's a move O can make to block X from winning
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (gameBoard[i] != HUMAN_PLAYER && gameBoard[i] != COMPUTER_PLAYER) {
                gameBoard[i] = HUMAN_PLAYER;
                if (checkForWinner() == 2) {
                    gameBoard[i] = OPEN_SPOT;
                    return i;
                }
                gameBoard[i] = OPEN_SPOT;
            }
        }

        // Generate random move
        do {
            move = randomNumber.nextInt(BOARD_SIZE);
        } while (gameBoard[move] == HUMAN_PLAYER
                || gameBoard[move] == COMPUTER_PLAYER);

        return move;
    }

    /**
     * Check for a winner and return a status value indicating who has won.
     *
     * @return Return 0 if no winner or tie yet, 1 if it's a tie, 2 if X won, or
     *         3 if O won.
     */
    public int checkForWinner() {
        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3) {
            if (gameBoard[i] == HUMAN_PLAYER
                    && gameBoard[i + 1] == HUMAN_PLAYER
                    && gameBoard[i + 2] == HUMAN_PLAYER)
                return 2;
            if (gameBoard[i] == COMPUTER_PLAYER
                    && gameBoard[i + 1] == COMPUTER_PLAYER
                    && gameBoard[i + 2] == COMPUTER_PLAYER)
                return 3;
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (gameBoard[i] == HUMAN_PLAYER
                    && gameBoard[i + 3] == HUMAN_PLAYER
                    && gameBoard[i + 6] == HUMAN_PLAYER)
                return 2;
            if (gameBoard[i] == COMPUTER_PLAYER
                    && gameBoard[i + 3] == COMPUTER_PLAYER
                    && gameBoard[i + 6] == COMPUTER_PLAYER)
                return 3;
        }

        // Check for diagonal wins
        if ((gameBoard[0] == HUMAN_PLAYER && gameBoard[4] == HUMAN_PLAYER && gameBoard[8] == HUMAN_PLAYER)
                || (gameBoard[2] == HUMAN_PLAYER
                && gameBoard[4] == HUMAN_PLAYER && gameBoard[6] == HUMAN_PLAYER))
            return 2;
        if ((gameBoard[0] == COMPUTER_PLAYER && gameBoard[4] == COMPUTER_PLAYER && gameBoard[8] == COMPUTER_PLAYER)
                || (gameBoard[2] == COMPUTER_PLAYER
                && gameBoard[4] == COMPUTER_PLAYER && gameBoard[6] == COMPUTER_PLAYER))
            return 3;

        // Check for tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (gameBoard[i] != HUMAN_PLAYER && gameBoard[i] != COMPUTER_PLAYER)
                return 0;
        }

        // If we make it through the previous loop, all places are taken, so
        // it's a tie
        return 1;
    }

}