package a2b_serialized;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The cell is the building block of the world.
 * A cell belongs to a world, and can have a resident organism.
 * It also keeps track of its neighbours.
 * @author Reneil Pascua
 *
 */
public class Cell implements Serializable {

    /** the cell's coordinates */
    final int[] location = new int[2];
    /** the world in which this cell belongs */
    World world;
    /** the organism living in the cell */
    Organism resident;
    
    /**
     * instantiates a cell into coordinate (x,y) of a world.
     * @param x
     * @param y
     * @param world
     */
    Cell(int x, int y, World world) {
        location[0] = x;
        location[1] = y;
        this.world = world;
    }
    
    /**
     * if the cell has a resident, refreshes it so it may make a move  
     */
    public void refreshOrg() {
        if (resident == null) {
            return; // do nothing
        } else {
            resident.hasFinishedItsTurn = false;
        }
    }
    
    /**
     * gets a list of neighbours
     * 
     * to be used by the resident to give information to identify
     * number of mates, food, which cells are empty
     */
    //TODO: integrate this into everything to do with neighbours
    public List<Cell> getNeighbours() {
        List<Cell> neighbours = new ArrayList<>();
        for (int i=-1; i<=1; i++) {
            for (int j=-1; j<=1; j++) {
                if (i==0 && j==0) {continue;}
                try {
                    neighbours.add(world.cells[location[0]+i][location[1]+j]);
                
                // no neighbour there? array out of bounds? don't add.
                } catch (Exception e) {}
            }
        }
        return neighbours;
    }
}
