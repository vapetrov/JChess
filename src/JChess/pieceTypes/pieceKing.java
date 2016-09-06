/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jchess.pieceTypes;

import java.awt.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
public class pieceKing extends genericPiece implements pieceInt {
    // King game piece

    private boolean enPassant = false;
    private boolean promote = false;

    public pieceKing(PColor color, boardWrapper board) {
        super(color, board);
    }

    public List getMoves() {
        List<Point> possible = new ArrayList(); // Possible moves are stored
        Point pos = board.locationOf(this);
        int x = pos.x;
        int y = pos.y;
        
        // adds possiblilities
        for(int[] i: new int[][]{{0,1},{0,-1},{1,0},{-1,0},{-1,-1},{1,1},{-1,1},{1,-1}}){
            if (board.getValue(new Point(x+i[0], y+i[1])) == null || board.getValue(new Point(x+i[0], y+i[1])).getColor() != this.getColor() ){
                possible.add(new Point(x+i[0], y+i[1]));
            }   
        }
        
        // removes out of bounds positions
        for(int p = possible.size()-1; p >= 0; p--){
            if(!board.inBounds(possible.get(p))){
                possible.remove(p);
            }
        }
        
        if(!hasMoved){
            if(board.getValue(new Point(x-1,y)) == null &&
               board.getValue(new Point(x-2,y)) == null &&    
               board.getValue(new Point(x-3,y)) == null &&    
               board.getValue(new Point(x-4,y)) != null &&
               board.getValue(new Point(x-4,y)).getFlags().contains(Flags.notMoved)
            ){
               possible.add(new Point(x-2,y));       
            }
            if(board.getValue(new Point(x+1,y)) == null &&
               board.getValue(new Point(x+2,y)) == null &&       
               board.getValue(new Point(x+3,y)) != null &&
               board.getValue(new Point(x+3,y)).getFlags().contains(Flags.notMoved)
            ){
               possible.add(new Point(x+2,y));       
            }
            
            
        }  
        
        
        return possible;
    }

    public int getAttacks() {
        //calculates possible Attacks
        return 0; //todo
    }

    public Collection getFlags() {
        //returns value of local variables
        ArrayList<Flags> flags = new ArrayList<>();
        if(!hasMoved){
            flags.add(Flags.notMoved);
        }
        return flags;
    }
    public PType getType(){
        if(color == PColor.Black){
            return PType.king_black;
        }
            return PType.king_white;
    }
}