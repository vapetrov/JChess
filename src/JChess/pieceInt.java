/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jchess;


import java.awt.Point;
import java.util.Collection;


/**
 * Interface for all piece interactions.
 *
 * @author Vassily
 */
public interface pieceInt {
   
    public void moved();
    
    public Collection<Point> getMoves();

    public void setSpecial(boolean state);
    
    public PColor getColor();

    public Collection getFlags();
    
    public PType getType();
}
