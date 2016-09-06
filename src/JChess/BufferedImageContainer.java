package jchess;


import java.awt.image.BufferedImage;



/**
 * This contains a BufferedImage so that it can be passed to a JPanel, and modified in place.
 * NewImage() must be called before the image is drawn to.
 * @author Vassily
 */
public class BufferedImageContainer {
    private BufferedImage image;
    
    public BufferedImageContainer(){
        // Dimensions are unkown, make it minimum size.
        // Calling newImage() is required before working with the container.
        image = new BufferedImage(1,1,BufferedImage.TYPE_4BYTE_ABGR);
    }
    
    public BufferedImage get(){
        return image;
    }
    
    public void newImage(int width,int height,int type){
        image = new BufferedImage(width,height,type);
    }
    
    public void newImage(int width,int height){
        newImage(width,height,BufferedImage.TYPE_4BYTE_ABGR);
    }
    
    
}
