package jchess;


import java.awt.Color;
import java.util.HashMap;



/**
 * A container to hold many different sets of colors used in a Chess Board.
 * By default, it contains one color set and more can be added.
 * @author Vassily
 */
public class colorSchemeSet {
    
    /*  Each entry Maps a Scheme name to a set of colors.
     *  Each nested Color map maps a Colors enum to a Color.
     *  By default, there is one "default color scheme. 
     *  The rest have to be added externally.
     */
    private HashMap<String,HashMap<Colors,Color>> colorSchemes;
    private String currentScheme;
    
    public colorSchemeSet(){
        colorSchemes = new HashMap();
        currentScheme = "";
        
        //adds default Scheme
        addScheme(
            "default",
            new Color(92, 85, 224, 175),
            new Color(179, 175, 250, 175),
            new Color(240,46,46,175),
            Color.lightGray,
            Color.white
            );
        setCurrentScheme("default");
    
    }
    
    /**
     * Adds a new color scheme that can be used. However, it won't be used until it
     * is set as the Current Scheme.
     * @param Name Name of scheme.
     * @param highlight1 Color used for highlighting moves.
     * @param highlight2 Second color used for highlighting moves.
     * @param alert Alerting color that blinks.
     * @param dark Dark tile color.
     * @param light Light tile color.
     */
    public void addScheme(String Name, Color highlight1, Color highlight2, Color alert, Color dark, Color light){
        HashMap<Colors,Color> workingScheme = new HashMap();
        workingScheme.put(Colors.HIGHLIGHT_1,highlight1);
        workingScheme.put(Colors.HIGHLIGHT_2, highlight2);
        workingScheme.put(Colors.ALERT, alert);
        workingScheme.put(Colors.DARK, dark);
        workingScheme.put(Colors.LIGHT,light);
        colorSchemes.put(Name.toLowerCase(), workingScheme);
    }
    
    /**
     * Sets the current working color Scheme. GetColor Methods will only work on the working color Scheme.
     * @param Name Name of scheme
     * @return Returns true if Name is a valid Scheme, otherwise returns false.
     */
    public boolean setCurrentScheme(String Name){
        Name = Name.toLowerCase();
        if(colorSchemes.keySet().contains(Name)){
            currentScheme = Name;
            return true;
        }
        return false;
    }
    
    /**
     * Gets a color from the current color scheme.
     * @param Name Name of the color.
     * @return Requested color with that name.
     */
    public Color getColor(Colors Name){
        return colorSchemes.get(currentScheme).get(Name);
    }
    
    /**
     * Finds colors, independent of the current color scheme.
     * @param Scheme Name of the scheme.
     * @return The specific color.
     */
    public Color[] getIndColors(String Scheme){
        Color[] colors = new Color[getColorNames().length];
        int j = 0;
        for(Colors i: Colors.values()){
            colors[j] = colorSchemes.get(Scheme).get(i);    
            j++;
        }
        return colors;
    }
    
    /**
     * Finds the scheme currently being used.
     * @return Name of scheme
     */
    public String getCurrentScheme(){
        return currentScheme;
    }
    
    
    public String[] getColorSets(){
        Object[] o = colorSchemes.keySet().toArray();
        String[] vals = new String[o.length];
        int j = 0;
        for(Object i:o){
            vals[j] = i.toString();
            j++;
        }
        return vals;
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
    
    /**
     * color possibilities.
     */
    public enum Colors{
        HIGHLIGHT_1,HIGHLIGHT_2,ALERT,DARK,LIGHT
    }
    
}
