package jchess;

import com.rits.cloning.Cloner;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jchess.pieceTypes.*;

/**
 * A chessboard with movable pieces and move validation.
 *
 * @author Vassily
 */
public class chessBoard {

    //main board where all pieces are stored
    private boardWrapper board;
    private PColor turn;
    private boolean freeMove;
    
    public chessBoard() {
        freeMove = false;
        turn = PColor.White;
        board = new boardWrapper();
        populateBoard();
        // add different game types?

    }
    
    /**
     * Used for simulations.
     * @param board premade board.
     */
    public chessBoard(boardWrapper board) {
        super();
        this.board = board;
    }
    
    

    /**
     * Moves a piece from one position to another, removing anything in the way.
     * Moves are validated.
     *
     * @param first Location of piece to move.
     * @param second Destination of piece.
     * @return true if it moved, false if it was invalid.
     */
    public Status movePiece(Point first, Point second) {
        pieceInt origin = board.getValue(first);
        pieceInt location = board.getValue(second);
        // for interacting with the board
        // this method moves and validates pieces
        if (isValidMove(first, second)) {
            // moves the piece, destroying anything in it's way
            board.setValue(second, origin);
            board.setValue(first, null);
            
            // Returns pieces to original position if it causes a check.
            if(isInCheck(turn) && !freeMove){
                board.setValue(first, origin);
                board.setValue(second, location);
                return Status.createsCheck;
            }
         
            
            // eleminates all set specials for pawns of the opposite color
            PType propType;
            if(turn == PColor.White){
                propType = PType.pawn_black;
            }else{
                propType = PType.pawn_white;
            }
            for(int i=1;i<=8;i++){
                for(int j=1;j<=8;j++){
                    pieceInt part = board.getValue(new Point(i,j));
                    if(part != null && part.getType() ==  propType){
                        part.setSpecial(false); // sets all pawn enPassants to false.
                    }
                }
            }
            
            int movDir = second.x-first.x;
            
            // sets the special for any pawn that moved two squares
            if(origin.getType() == PType.pawn_black || origin.getType() == PType.pawn_white){
                if(origin.getFlags().contains(Flags.notMoved)){
                    if(Math.abs(first.y-second.y) == 2){ // x distance moved is 2
                        origin.setSpecial(true); //sets enPassant
                        
                    }
                }
                
                if(Math.abs(first.x-second.x) == 1 && location == null){ //activates if a pawn attacks on an enPassant
                    board.setValue(new Point(first.x+movDir,first.y), null); //removes the piece that it didn't step on
                }
                
                if(second.y == 8 || second.y == 1){
                    board.setValue(second, new pieceQueen(turn, board)); // pawn promotion
                }
                
            }
            
            // moves rook if the king did castled
            if(origin.getType() == PType.king_black || origin.getType() == PType.king_white){
                System.out.println(first.x-second.x);
                 if(first.x-second.x == 2){ //move left
                     board.setValue(new Point(4,second.y), board.getValue(new Point(1,second.y))); //moves left rook
                     board.setValue(new Point(1,second.y),null);
                 }
                 if(first.x-second.x == -2){ //move right
                     board.setValue(new Point(6,second.y), board.getValue(new Point(8,second.y))); //moves left rook
                     board.setValue(new Point(8,second.y),null);
                 }
            }
            
            
            
            origin.moved();
            if(turn == PColor.White){
                turn = PColor.Black;
            }else{
                turn = PColor.White;
            }
            return Status.Success;
            // TODO add pawn promotion, castling
        }
        return Status.invalid;
    }

    /**
     * Validates the move.
     *
     * @param first Location of piece.
     * @param second Destination of piece.
     * @return Returns whether the move is valid or not.
     */
    public boolean isValidMove(Point first, Point second) {
        return getMoves(first).contains(second) || freeMove;
    }

    
    /**
     * Sets whether pieces can move anywhere.
     * @param state True for freeMove, false otherwise.
     */
    public void setFreeMove(boolean state){
        freeMove = state;
    }
    
    
    /**
     * Places default pieces in their places at the start of the game. Shouldn't
     * be ran more than once, really.
     */
    private void populateBoard() {

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                board.setValue(new Point(i, j), null); // clear the board, just in case
            }
        }

        // default order of pieces
        pieceInt[] top = {
            new pieceRook(PColor.White, board), new pieceKnight(PColor.White, board), new pieceBishop(PColor.White, board),
            new pieceQueen(PColor.White, board), new pieceKing(PColor.White, board),
            new pieceBishop(PColor.White, board), new pieceKnight(PColor.White, board), new pieceRook(PColor.White, board)
        };
        // set the top white row
        for (int x = 1; x <= 8; x++) {
            try {
                board.setValue(new Point(x, 1), (pieceInt) top[x - 1].getClass().getConstructor(PColor.class, boardWrapper.class).newInstance(PColor.Black, board));
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException ex) {
                Logger.getLogger(chessBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //set pawns
        for (int x = 1; x <= 8; x++) {
            board.setValue(new Point(x, 2), new piecePawn(PColor.Black, board));
        }
        for (int x = 1; x <= 8; x++) {
            board.setValue(new Point(x, 7), new piecePawn(PColor.White, board));
        }

        //set bottom black row
        for (int x = 8; x >= 1; x--) {
            try {
                board.setValue(new Point(x, 8), (pieceInt) top[x - 1].getClass().getConstructor(PColor.class, boardWrapper.class).newInstance(PColor.White, board));
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException ex) {
                Logger.getLogger(chessBoard.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
    }

    
    /**
     * Color whose turn it is.   
     * @return Current turn color.
     */
    public PColor getTurn(){
        return turn;
    }
    
    public boolean notEmpty(Point p){
        return board.getValue(p) != null;
    }
    
    
    public Collection<Point> getMoves(Point p){
        if(board.getValue(p).getColor() == turn){
            return board.getValue(p).getMoves();
        }
        return new ArrayList();
         
    }   
    
    public PType getType(Point p){
        if(notEmpty(p)){
            return board.getValue(p).getType();
        }
        return null;   
    }
    
    /**
     * Checks if the king of the specified color is in check right now.
     * @param color color of king to check.
     * @return True if is in check, otherwise false.
     */
    public boolean isInCheck(PColor color){
        PType type = null;
        if(color == PColor.White){
            type = PType.king_white;
        }else{
            type = PType.king_black;
        }
        
        Point king = null;
        for(int x=1;x<=8;x++){
            for(int y=1;y<=8;y++){
                if(board.getValue(new Point(x,y)) != null && board.getValue(new Point(x,y)).getType() == type){
                    king = new Point(x,y);
                    break;
                }
            }
        }
        return findAttackers(color,king).length > 1;
    }
    
    public Point[] checkLocationSim(Point first, Point second){
        Cloner cloner=new Cloner();
        chessBoard simulator = new chessBoard(cloner.deepClone(board));
        simulator.setFreeMove(true);
        simulator.movePiece(first, second);
        return simulator.findCheckLocation(turn);
    }
    
    /**
     * Finds  any piece that can attack <i>location</i>, assuming that the color of the tile is <i>color</i>,
     * even if that isn't the case.
     * @param color Assumption of the color of the tile.
     * @param location Tile to check.
     * @return A list of points that can attack the location, including the location itself.
     */
    public Point[] findAttackers(PColor color,Point location){
        PType pawnType = PType.pawn_white;
        if (color == PColor.White){
            pawnType = PType.pawn_black;
        }
        
        Point[] pos = new Point[64];
        pos[0] = location;
        int filled = 1;
        
        for(int x=1;x<=8;x++){
            for(int y=1;y<=8;y++){
                if(board.getValue(new Point(x,y)) != null && board.getValue(new Point(x,y)).getColor() != color){
                    // Special case: the peice can't be a pawn because otherwise it will try to move down one.
                    // See below.
                    if(board.getValue(new Point(x,y)).getType() != pawnType && board.getValue(new Point(x,y)).getMoves().contains(location)){
                        // points are added to the list only if they can attack location.
                        pos[filled] = new Point(x,y);
                        filled++;
                    }        
                }
            }
        }
        
        
        /* Special case: Since location can be an emty square, pawns won't target that square, 
        * So that condition has to be tested for manually.
        */
        if(color == PColor.White){
            Point area1 = new Point(location.x+1,location.y-1);
            Point area2 = new Point(location.x-1,location.y-1);
            if(board.getValue(area1) != null && board.getValue(area1).getColor() == PColor.Black){
                pos[filled] = new Point(area1);
                filled++;
            }
            if(board.getValue(area2) != null && board.getValue(area2).getColor() == PColor.Black){
                pos[filled] = new Point(area2);
                filled++;
            }
        }else{
            Point area1 = new Point(location.x+1,location.y+1);
            Point area2 = new Point(location.x-1,location.y+1);
            if(board.getValue(area1) != null && board.getValue(area1).getColor() == PColor.White){
                pos[filled] = new Point(area1);
                filled++;
            }
            if(board.getValue(area2) != null && board.getValue(area2).getColor() == PColor.White){
                pos[filled] = new Point(area2);
                filled++;
            }
        }
        

        return Arrays.copyOf(pos, filled);  
    }
    
    
    /** Finds the location at Which a king of the specified color is in check.
     * @param color Color of king.
     * @return Locations of pieces which put the king in check, including the location of the king.
     */
    public Point[] findCheckLocation(PColor color){
        PType type = null;
        if(color == PColor.White){
            type = PType.king_white;
        }else{
            type = PType.king_black;
        }
        
        Point king = null;
        for(int x=1;x<=8;x++){
            for(int y=1;y<=8;y++){
                if(board.getValue(new Point(x,y)) != null && board.getValue(new Point(x,y)).getType() == type){
                    king = new Point(x,y);
                    break;
                }
            }
        }
        return findAttackers(color,king);
    }
        
    
    
    
    // Debug methods below. Not particularly useful for anything else.
    
    /**
     * Prints out the board. Mostly useful for debugging.
     */
    public void printBoard() {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (board.getValue(new Point(j, i)) != null) {
                    System.out.print(board.getValue(new Point(j, i)).getColor().toString().substring(0, 1) + "-" + board.getValue(new Point(j, i)).toString().substring(5, 7).toUpperCase());
                } else {
                    System.out.print(" ** ");
                }
                System.out.print("  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Prints out possible moves. Debug method.
     *
     * @param p Location of piece.
     */
    public void printMoves(Point p) {
        int x = p.x;
        int y = p.y;
        List moves = (List) board.getValue(p).getMoves();
        for (int i = 0; i < moves.size(); i++) {
            System.out.println("(" + moves.get(i) + ")");
        }


    }
}