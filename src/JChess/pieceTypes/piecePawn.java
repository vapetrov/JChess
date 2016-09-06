package jchess.pieceTypes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import jchess.Flags;
import jchess.PColor;
import jchess.PType;
import jchess.boardWrapper;
import jchess.genericPiece;
import jchess.pieceInt;

/**
 * Pawn game piece
 *
 * @author Vassily
 */
public class piecePawn extends genericPiece implements pieceInt {

    private boolean enPassant = true; // should be set by chessBoard!!!
    private boolean promote = false; // should be set by chessBoard!!!
    
    
    public piecePawn(PColor color, boardWrapper board) {
        super(color, board);
    }

    
    @Override
    public void setSpecial(boolean state){
        enPassant = state;
    }
    
    /**
     * Finds possible moves the piece could take.
     *
     * @return An ArrayList containing Pairs of coordinates for possible moves.
     */
    @Override
    public Collection getMoves() {
        ArrayList<Point> possible = new ArrayList(); // Possible moves are stored
        Point pos = board.locationOf(this);
        int x = pos.x;
        int y = pos.y;
        
         
        
        int dir;
        // defines which way a pawn moves -1 (up) for white and 1 (down) for black
        if(color == PColor.White){
            dir = -1; //white
        }else{
            dir = 1; //black
        }
        
        Point Ydir = new Point(x, y + dir);
        Point XminYdir = new Point(x - 1,y + dir);
        Point XplusYdir = new Point(x + 1,y + dir);
        Point Xmin = new Point(x - 1,y);
        Point Xplus = new Point(x + 1, y);
        
        
        // see if the pawn can be moved up one
        if (board.getValue(Ydir) == null) {
            possible.add(Ydir);
            if (!board.hasMoved(this) && board.getValue(new Point(x, y + dir + dir)) == null) {
                // jump 2 spaces from start
                possible.add(new Point(x, y + dir + dir));
            }
        }
        // check for possibility of attacking left
        if (board.getValue(XminYdir) != null && !board.getValue(XminYdir).getColor().equals(color)) {
            possible.add(XminYdir);
        }
        // check for possibility of attacking right
        if (board.getValue(XplusYdir) != null && !board.getValue(XplusYdir).getColor().equals(color)) {
            possible.add(XplusYdir);
        }


        // attack left with an enPassant
        if (board.getValue(XminYdir) == null
                && board.getValue(Xmin) != null
                && !board.getValue(Xmin).getColor().equals(color)
                && board.getValue(Xmin).getFlags().contains(Flags.enPassant)) {
            possible.add(XminYdir);
        }

        // attack right with an enPassant
        if (board.getValue(XplusYdir) == null
                && board.getValue(Xplus) != null
                && !board.getValue(Xplus).getColor().equals(color)
                && board.getValue(Xplus).getFlags().contains(Flags.enPassant)) {
            possible.add(XplusYdir);
        }




        return possible;
    }

    public int getAttacks() {
        //calculates possible Attacks
        return 0; //todo
    }


    @Override
    public Collection getFlags() {
        ArrayList flags = new ArrayList();
        if (enPassant) {
            flags.add(Flags.enPassant);
        }
        if(!hasMoved){
            flags.add(Flags.notMoved);
        }
        return flags;
    }
    
    @Override
    public PType getType(){
        if(color == PColor.Black){
            return PType.pawn_black;
        }
            return PType.pawn_white;
    }
}