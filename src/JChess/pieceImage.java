
package jchess;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * Loads pieces from a resources folder. Also allows rescaling of the pieces in place.
 * @author Vassily
 */
public class pieceImage {
    private final BufferedImage[] PIECES;
    private BufferedImage[] piecesScaled;
    private String[] chessNames;
    private int currentScale;
    
    public pieceImage() {
        
        String imageLocation = "resources/default/";
        
        currentScale = 0;
        PType[] states = PType.values();
        chessNames = new String[states.length];

        for (int i = 0; i < states.length; i++) {
           chessNames[i] = states[i].name();
         }
        
        
        BufferedImage[] piecesUnscaled = new BufferedImage[12];
        
        for(int i=0; i<piecesUnscaled.length;i++){
            try {      
                InputStream in = getClass().getResourceAsStream(imageLocation+chessNames[i]+".png");
                piecesUnscaled[i] = ImageIO.read(in);
          
            } catch (IOException | IllegalArgumentException ex) {
            System.out.println("couldn't find: "+"resources/"+chessNames[i]+".png");
            }
        }
        piecesScaled = piecesUnscaled; // they will be scaled later
        PIECES = piecesUnscaled.clone();
    }
    
    /**
     * Scales all pieces to a set height, for use with getImage. Will not work if pieces failed to load.
     * @param height Height of piece in px to scale to.
     */
    public void rescale(int height){
        if(currentScale != height){
            for(int i=0;i<PIECES.length;i++){
                piecesScaled[i] = toBufferedImage(PIECES[i].getScaledInstance(-1, height, Image.SCALE_AREA_AVERAGING)); 
            }  
        }
        currentScale = height;
    }
    
    /**
     * Gets a piece of a certain type. Only works if pieces were loaded correctly.
     * @param type Type of piece.
     * @return Image containing the scaled piece.
     */
    public BufferedImage getImage(PType type){
        int pos = -1; // causes it to throw an error if the pieces is not in the list.
        for(int i = 0; i < chessNames.length; i++) {
            if(chessNames[i].equals(type.name())) {
                pos = i;
                break;
            }
        }
        return piecesScaled[pos];   
    }
    
    /**
    * Converts a given Image into a BufferedImage
    * From http://stackoverflow.com/a/13605411
    * @param img The Image to be converted
    * @return The converted BufferedImage
    */
    public static BufferedImage toBufferedImage(Image img)
    {
      if (img instanceof BufferedImage)
      {
           return (BufferedImage) img;
       }

      // Create a buffered image with transparency
      BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
      
       // Draw the image on to the buffered image
       Graphics2D bGr = bimage.createGraphics();
       bGr.drawImage(img, 0, 0, null);
       bGr.dispose();
       
       // Return the buffered image
       return bimage;
    }
    
}
