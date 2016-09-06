package jchess;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The Tools contains general static methods that don't fit anywhere else.
 *
 * @author Vassily
 */
public class Tools {

    /**
     * Given a hexadecimal color String, It returns a Color object.
     *
     * @param color hex color.
     * @return Color.
     */
    public static Color hex(String color) {
        return new Color(
                Integer.valueOf(color.substring(1, 3), 16),
                Integer.valueOf(color.substring(3, 5), 16),
                Integer.valueOf(color.substring(5, 7), 16));
    }

    public static Point flip(Point p) {
        return new Point(-p.x + 9, -p.y + 9);
    }

    public static int flip(int p) {
        return -p + 9;
    }

    public static void loadColorSettings(colorSchemeSet colors) {

        String resourceName = "resources/colors.json";
        boolean isValid = true;

        Set typeNames = new HashSet();
        for (colorSchemeSet.Colors i : colorSchemeSet.Colors.values()) {
            typeNames.add(i.toString());
        }
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            InputStream in = Tools.class.getResourceAsStream(resourceName);
            obj = parser.parse(new InputStreamReader(in));
        } catch (IOException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, "Config file couldn't be loaded. Make sure it exists.", ex);
            isValid = false;
        } catch (ParseException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, "Error parsing config file.", ex);
            isValid = false;
        }
        HashMap file = (HashMap) obj;


        // first step verifies that the config file is correct
        try {
            if (isValid) {
                for (Object i : file.keySet()) { //iterates over all color sets
                    HashMap props = (HashMap) file.get(i);
                    if (!props.keySet().equals(typeNames)) {
                        Logger.getLogger(Tools.class.getName()).log(Level.SEVERE,
                                "Error loading " + resourceName + ": \n"
                                + i.toString() + "\n"
                                + "must contain the following colors: \n"
                                + typeNames.toString(), file);
                        isValid = false;
                        break;
                    }
                    for (Object j : props.keySet()) { // iterates over list of colors in a colorset
                        ArrayList colorList = (ArrayList) props.get(j);
                        if (colorList.size() != 3 && colorList.size() != 4) {
                            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE,
                                    "Error loading " + resourceName + ": \n"
                                    + i.toString() + "." + j.toString() + "\n"
                                    + "must be 3 or 4 digits \n", file);
                            isValid = false;
                            break;
                        }
                        for (Object k : colorList) { //iterates over each color in the color set
                            Long preset = new Long((Long) k);
                            int value = preset.intValue();
                            if (value < 0 || value > 255) {
                                Logger.getLogger(Tools.class.getName()).log(Level.SEVERE,
                                        "Error loading " + resourceName + ": \n"
                                        + i.toString() + "." + j.toString() + "\n"
                                        + "numbers must be between 255 and 0 \n", file);
                                isValid = false;
                                break;
                            }
                        }
                    }
                }
            }
        } catch (ClassCastException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, "Invalid config file.", ex);
        }
        
        // colors will only be added if the file was valid
        if(!isValid){
            return;
        }
        
        for (Object i : file.keySet()) { //iterates over all color sets
                    HashMap props = (HashMap) file.get(i);
                    HashMap<String,Color> filler = new HashMap<>();
                     for (Object j : props.keySet()) { // iterates over list of colors in a colorset
                         filler.put(j.toString(), setToColor((ArrayList)props.get(j)));  
                     }
                     colors.addScheme(i.toString(),
                             filler.get("HIGHLIGHT_1"),
                             filler.get("HIGHLIGHT_2"),
                             filler.get("ALERT"),
                             filler.get("DARK"),
                             filler.get("LIGHT")
                     );
        }
    }
    
    
    private static Color setToColor(ArrayList values){
        int[] params = new int[values.size()];
        int pos = 0;
        for(Object i:values){
            Long preset = new Long((Long) i);
            params[pos] = preset.intValue();
            pos++;
        }
        if(params.length == 3){
            return new Color(params[0], params[1], params[2]);
        }
        if(params.length == 4){
            return new Color(params[0], params[1], params[2], params[3]);
        }
        return null;
    }
    
}
