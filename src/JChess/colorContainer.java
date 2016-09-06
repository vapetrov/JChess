
package jchess;

import java.awt.Color;
import java.util.HashMap;


/**
 *
 * @author Vassily
 */
public class colorContainer {
    private HashMap<Colors,Color> colorSet;

    public colorContainer() {
        this(
            new Color(92, 85, 224, 175),
            new Color(179, 175, 250, 175),
            new Color(240,46,46,175),
            Color.lightGray,
            Color.white
            );
    }
    
    public colorContainer(Color highlight1, Color highlight2, Color alert, Color dark, Color light){
        colorSet = new HashMap();
        colorSet.put(Colors.HIGHLIGHT_1,highlight1);
        colorSet.put(Colors.HIGHLIGHT_2, highlight2);
        colorSet.put(Colors.ALERT, alert);
        colorSet.put(Colors.DARK, dark);
        colorSet.put(Colors.LIGHT,light);
    }
    
    public String[] getColorNames(){
        String[] val = new String[Colors.values().length];
        int n = 0;
        for(Colors i : Colors.values()){
            val[n] = i.toString();
            n++;
        }
        return val;
    }
    
    public Color getColor(Colors type){
        return colorSet.get(type);
    }
    
    public void setColor(Colors type, Color color){
        colorSet.put(type, color);
    }
    
    public enum Colors{
        HIGHLIGHT_1,HIGHLIGHT_2,ALERT,DARK,LIGHT
    }
    
}
