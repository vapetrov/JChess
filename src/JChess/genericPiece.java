/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jchess;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Piece from which all other chess pieces branch off.
 *
 * @author Vassily
 */
public abstract class genericPiece {

   
    protected final PColor color; // color of piece
    protected boardWrapper board;
    protected boolean hasMoved;
   
    
    public genericPiece(PColor color, boardWrapper board) {
        hasMoved = false;
        this.color = color;
        this.board = board;
        

    }
    
    /**
     * Method should be called after the piece has moved once.
     * Calling this function removes the notMoved flag from getFlags().
     */
    public void moved(){
        hasMoved = true;
    }
    
    /**
     * Sets special properties.
     * Currently only for pawn.
     */
    public void setSpecial(boolean state){
        
    }
    
    /**
     * This is logic which can be applied to any piece which moves in a line.
     * Basically anything but the pawn, knight, and king.
     * @param possible List of directions in which to try moving. See accompanying pieces.
     * @param piece The piece that called this method.
     * @return A collection of possible moves.
     */
    protected Collection LinearLogic(int[][] possible, pieceInt piece){
        Point pos = board.locationOf(piece);
        ArrayList<Point> possibilities = new ArrayList<>(); // Possible moves are stored
        
        for(int[] i: possible){
              for(int j = 1; j <= 7 ; j++){
                  Point loc = new Point(pos.x+j*i[0],pos.y+j*i[1]);
                  if(loc.x == 0){
                      loc.setLocation(loc.x, pos.y);
                  }
                  if(loc.y == 0){
                      loc.setLocation(pos.x, loc.y);
                  }
                  if (!board.inBounds(loc)){
                      break;
                  }else if(board.getValue(loc) == null){
                      possibilities.add(loc);
                  }else if(board.getValue(loc).getColor() == color){
                      break;
                  }else if (board.getValue(loc).getColor() != color){
                      possibilities.add(loc);
                      break;
                  }
             }
        }
        return possibilities;  
    }
    
    
    
    // generic piece with string override
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PColor getColor() {
        return color;
    }
    
}
