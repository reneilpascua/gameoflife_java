package a2b_serialized;

import javafx.application.Application;

/**
 * the entry point into the program
 * @author Reneil Pascua
 *
 */
public class Main {
    /**
     * drives the program
     * @param args
     */
    public static void main(String[] args) {
        // set for debug purposes
        GUIofLife.numcells = 50;
        
        Application.launch(a2b_serialized.GUIofLife.class);
    }
    
}
