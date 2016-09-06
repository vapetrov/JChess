package jchess;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


/**
 * A class for drawing the board. Move possibilities and pieces are drawn through BufferedImageContainers.
 * @author Vassily
 */
public class boardComponent extends JPanel {
    BufferedImageContainer pieceOverlay;
    BufferedImageContainer moveOverlay;
    ImageObserver noObserve = null; // it doesen't need to be observed
    colorSchemeSet colors;
    
    public boardComponent() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
    
    /**
     * Draw init.
     * @param highlight An image container with the moves and other shading drawn.
     * @param piece  An image container with pieces drawn.
     * @param colors colorSet of the board.
     */
    public boardComponent(BufferedImageContainer highlight,BufferedImageContainer piece, colorSchemeSet colors) {
        super();
        this.colors = colors;
        pieceOverlay = piece; 
        moveOverlay = highlight;
    }
    
    
    public Dimension getPreferredSize() {
        return new Dimension(760,760);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        
        Color darkColor = colors.getColor(colorSchemeSet.Colors.DARK);
        Color lightColor = colors.getColor(colorSchemeSet.Colors.LIGHT);
        g.setColor(lightColor);
        
        int height = getHeight()/8;
        int width = getWidth()/8;
        
        
        // Does not maintain squareness. Each tile may be square or rectangular.
        //The JFrame is responsible for maintaing correct size.
        
        for(int j=0;j < height*8;j+= height){
            for(int i=0; i < width*8; i+= width){
                g.fillRect(i, j, width, height);
                if(g.getColor().equals(darkColor)){
                    g.setColor(lightColor);
                }else{
                    g.setColor(darkColor);
                }      
            }
            if(g.getColor().equals(darkColor)){
                    g.setColor(lightColor);
                }else{
                    g.setColor(darkColor);
                }
        }
        
        g.drawImage(moveOverlay.get(),0,0,null);
        g.drawImage(pieceOverlay.get(),0,0,null);
        
    }  
}
