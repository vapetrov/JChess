/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jchess.pieceTypes;

import java.util.ArrayList;
import java.util.Collection;
import jchess.Flags;
import jchess.PColor;
import jchess.PType;
import jchess.boardWrapper;
import jchess.genericPiece;
import jchess.pieceInt;

/**
 *
 * @author Vassily
 */
public class pieceRook extends genericPiece implements pieceInt {
    // Rook game piece

    private boolean enPassant = false;
    private boolean promote = false;
    private final int[][] possible = {{0,1},{0,-1},{1,0},{-1,0}};
   
    
    public pieceRook(PColor color, boardWrapper board) {
        super(color, board);      
    }

    public Collection getMoves() {
        return LinearLogic(possible,this);
    }
    
    
   
    
    public int getAttacks() {
        //calculates possible Attacks
        return 0; //todo
    }

    public Collection getFlags() {
       ArrayList<Flags> flags = new ArrayList<>();
        if(!hasMoved){
            flags.add(Flags.notMoved);
        }
        return flags;
    }
    public PType getType(){
        if(color == PColor.Black){
            return PType.rook_black;
        }
            return PType.rook_white;
    }
}