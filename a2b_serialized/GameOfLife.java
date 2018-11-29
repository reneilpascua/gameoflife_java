package a2b_serialized;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.scene.paint.Color;

/**
 * a game of life is a simulation of a world and its inhabitants,
 * defined by unique rules, properties, and random interactions.
 * @author Reneil Pascua
 *
 */
public class GameOfLife {
    
    /** the environment in which the game occurs */
    World world;
    
    /**
     * starts a game of life by creating a world of the specified size
     * @param size
     */
    public GameOfLife(int size) {
        System.out.println("Game started");
        world = new World(size);
    }
    
    /**
     * moves the game forward by one time-step in which every cell-bound organism is
     * called to take their turn
     */
    public void update() {
        // refresh the cells so they can take a turn
        world.refresh();
        
        for (int i=0; i<world.size; i++) {
            for (int j=0; j<world.size; j++) {
                // try updating a cell's resident, if it hasn't finished its turn
                try {
                    if (!world.cells[i][j].resident.hasFinishedItsTurn) {
                        world.cells[i][j].resident.takeTurn();
                    }
                } catch(NullPointerException e) { //cell has no resident to update
                }
            }
        }
    }

    /**
     * serializes the state of the world into a file in the same directory
     * called saveworld.txt
     */
    public void saveWorld() {
        try {
            FileOutputStream outfile = new FileOutputStream("saveworld.txt");
            ObjectOutputStream objectout = new ObjectOutputStream(outfile);
            objectout.writeObject(world);
            objectout.flush();
            objectout.close();
            
            outfile.close();
            System.out.println("finished saving!");
            
        } catch (IOException i) {
            System.err.println("error caught in output file");
        }
    }
    
    /**
     * attempts to locate a savefile (named "saveworld.txt") from which it loads
     * a world state
     */
    public void loadWorld() {
        try {
            FileInputStream infile = new FileInputStream("saveworld.txt");
            ObjectInputStream objectin = new ObjectInputStream(infile);
            world = (World) objectin.readObject();
            objectin.close();
            
            infile.close();
            System.out.println("finished loading!");
            
        } catch (IOException i) {
            System.err.println("error in opening file saveworld.txt");
            
        } catch (ClassNotFoundException c) {
            System.err.println("error, class not found");
        }
        
        // restore the transient color of the organisms
        recolour();
    }
    
    /**
     * restores the colors of every organism
     * 
     * since javafx is not serializable, an organism's color is not saved
     * with the state of the world. thus, loadWorld() must call restore
     * the colour of each org
     */
    private void recolour() {
        for (int i=0; i<world.size; i++) {
            for (int j=0; j<world.size; j++) {
                
                try {
                    if (world.cells[i][j].resident instanceof Plant)
                        world.cells[i][j].resident.skin = Color.GREEN;
                    else if (world.cells[i][j].resident instanceof Herbivore)
                        world.cells[i][j].resident.skin = Color.YELLOW;
                    else if (world.cells[i][j].resident instanceof Carnivore)
                        world.cells[i][j].resident.skin = Color.RED;
                    else if (world.cells[i][j].resident instanceof Omnivore)
                        world.cells[i][j].resident.skin = Color.BLUE;
                } catch (NullPointerException n) {
                    // no resident, no coloring needed
                }
            }
        }
    }
    
}
