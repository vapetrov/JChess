package jchess;


import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;


/**
 *      A render for a ComboBox. It is designed to render a selection of colorSchemes which
 * would be stored in a colorShemeSet. Normally, it would be used to select the colors
 * used in the chessboard, however it can be used as a general class for selecting groups
 * of colors. 
 * @author Vassily
 */
public class comboColorMaker extends JPanel implements ListCellRenderer {

    private colorSchemeSet colorSet;
    private String setName;
    private HashMap<String,BufferedImage> imageStore;

    public comboColorMaker(colorSchemeSet colorSet) {
        imageStore = new HashMap();
        setOpaque(true);
        setName = null;
        this.colorSet = colorSet;
    }

    @Override
    /**
     * Simply sets the name of the current JPanel box being edited.
     */
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setName = (String) value;
        return this;            
    }

    @Override
    /**
     * Draws to the panel from the buffer.
     * Also calls to redraw the buffer if necessary.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(imageStore.get(setName+getWidth()+getHeight())==null){
            imageStore.put(setName+getWidth()+getHeight(), renderImage());
        }
        g.drawImage(imageStore.get(setName+getWidth()+getHeight()), 0, 0, null);
    }

    
    /**
     * Actually renders the JPanel and stores it into a BufferedImage which will then be
     * read. It will render whichever setName is currently set.
     * @return The rendered image.
     */
    private BufferedImage renderImage() {
        BufferedImage temp = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_4BYTE_ABGR_PRE);
        Graphics g = temp.createGraphics(); 
        
        //Divratio  determines how much space the colors will take up
        // 1.0 means that 100% of the width will be taken up, 0.5 will be 50%, etc.
        // 0.62 seems to be a good value.
        double divRatio = 0.62;
        
        divRatio = (1 / divRatio);
        int sqWidth = (int) (getWidth()/divRatio / colorSet.getColorNames().length); //color box width
        Color[] colors = colorSet.getIndColors(setName);
        int nameLength = colorSet.getColorNames().length;
        for (int i = 0; i < nameLength; i++) {
            // Fills up a rectange with a black stripe separator after each one
            g.setColor(colors[i]);
            g.fillRect(i * sqWidth, 0, sqWidth, getHeight());
            g.setColor(Color.BLACK);
            g.drawLine(i * sqWidth+sqWidth-1, 0, i*sqWidth+sqWidth-1, getHeight());        
        }
        
        //lettering
        int textWidth = g.getFontMetrics(g.getFont()).stringWidth(setName);
        int textHeight = g.getFontMetrics(g.getFont()).getHeight();
        int textPosX = ((getWidth() - sqWidth * nameLength) - textWidth)/2 + sqWidth * nameLength;
        int textPosY = (int) ((getHeight()-textHeight)/divRatio+textHeight);
        g.setColor(Color.BLACK);
        g.drawString(setName, textPosX, textPosY);
        g.dispose(); //important
        return temp;
    }
}
