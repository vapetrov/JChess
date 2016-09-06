package jchess;

import java.awt.Point;
import java.lang.reflect.Array;

/**
 * A helper class for handling transactions between the chess board.
 *
 * @author Vassily Petrov
 * @version 1.0
 *
 */
public class boardWrapper extends java.lang.Object {

    private pieceInt[] board;

    boardWrapper() {
        board = new pieceInt[64];
    }

    /* This is really only so positioned() can create a new boardWrapper.
     * 
     * Food for thought: Even though this is private, positioned() can access it when creating a new boardWrapper Object,
     * just because it is in the same class? Strange.
     */
    private boardWrapper(pieceInt[] newBoard) {
        board = newBoard;
    }

    /**
     * Sets a piece onto the board.
     *
     * @param p Location to set at, where x and y are between 1 and 8.
     * @param piece piece object to be placed onto the board
     */
    public void setValue(Point p, pieceInt piece) {
        int x = p.x;
        int y = p.y;
        if (inBounds(p)) {
            Array.set(board, positionOf(p), piece);
        }
        // throw exception?
    }

    /**
     * gets the piece at a given location on the board.
     *
     * @param p Value coordinate.
     * @return Piece at the given location, null if empty
     */
    public pieceInt getValue(Point p) {
        if (inBounds(p)) {
            return board[positionOf(p)];
        }
        return null;
        // throw exception?
    }

    /**
     * Number of Pieces on the board, not counting empty spaces.
     *
     * @return Number of pieces.
     */
    public int size() {
        int counter = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] != null) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Finds the location of a particular piece object on the board.
     *
     * @param location Piece that needs to be found.
     * @return [X,Y] coordinate pair
     */
    public Point locationOf(pieceInt location) {
        int x = 0;
        int y = 0;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (this.getValue(new Point(i, j)) != null && this.getValue(new Point(i, j)) == (location)) { // == not equals() here, because we need to check if its the same refference, not same type of object
                    return new Point(i, j);
                }
            }
        }
        System.out.println("couldn't find: " + location);
        return null;
        // Throw exception?
    }

    /**
     * Indicates Whether a given piece has moved before in the game.
     *
     * @param piece Piece to check.
     * @return Whether the piece has moved in this game or not.
     */
    public boolean hasMoved(pieceInt piece) {

        if (piece.getFlags().contains(Flags.notMoved)) {
            return false;
        }
        return true;
    }

    /**
     * Finds the location of a piece in the array given X Y coordinates.
     *
     * @param p Location to use.
     * @return position in array.
     * @see #dump()
     */
    public static int positionOf(Point p) {
        return p.x + 8 * (p.y - 1) - 1;
    }

    /**
     * Checks whether the point is inside a valid chessboard.
     *
     * @param p Point to check.
     * @return True if it is in Bounds, false otherwise.
     */
    public static boolean inBounds(Point p) {
        int x = p.x;
        int y = p.y;
        if (x > 8 || x < 1 || y > 8 || y < 1) {
            return false;
        }
        return true;
    }

    /* The following methods should only be used for debugging */
    /**
     * The board itself. Should only be used for debugging.
     *
     * @return The board itself.
     */
    public pieceInt[] dump() {
        return board;
    }

    /**
     * Finds X Y coordinates giver position in array. Should only be used for
     * debugging.
     *
     * @param pos Position in array.
     * @return Array of X Y coordinate pair.
     */
    public static int[] positionOfReverse(int pos) {
        int y = (pos) / 8;
        int x = pos - (8 * y) + 1;
        y++; // There are only y rows before the coord, but there is a total of y+1 rows
        return new int[]{x, y};
    }

}
