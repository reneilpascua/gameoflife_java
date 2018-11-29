package a2b_serialized;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The environment in which a game of life takes place
 * The world keeps track of the game's state per turn
 * @author Reneil Pascua
 *
 */
public class World implements Serializable {
    
    /** the size of the world (a square area of "size" X "size") */
    int size;
    /** an array of cells indexed by their position in world */
    Cell[][] cells;
    
    /**
     * instantiates a world given a size.
     * forms the cells, then lets the cells know their neighbours
     * then randomly spawn organisms in the whole world
     * @param worldsize
     */
    public World(int worldsize) {
        size = worldsize;
        terraform();
        spawnOrgs();
    }
    
    /**
     * discretizes the world into "size" X "size" number of cells
     */
    private void terraform() {
        cells = new Cell[size][size];
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                cells[i][j] = new Cell(i, j, this);
            }
        }
        System.out.println("World created");
    }
    
    /**
     * randomly spawns organisms onto each cell
     */
    private void spawnOrgs() {
        
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                // use pseudo-RNG
                int spawnRNG = RandomGenerator.nextNumber(99);
                if (spawnRNG >= 80) {
                    cells[i][j].resident = new Herbivore(cells[i][j]);
                } else if (spawnRNG >= 60) {
                    cells[i][j].resident = new Plant(cells[i][j]);
                } else if (spawnRNG >= 50) {
                    cells[i][j].resident = new Carnivore(cells[i][j]);
                } else if (spawnRNG >= 45) {
                    cells[i][j].resident = new Omnivore(cells[i][j]);
                }
            }
        }

    }
    

    
    /**
     * given coordinates, builds a list of neighbours for the cell
     * situated in the given coordinates
     * @param x
     * @param y
     * @return
     */
    //TODO: delete this
    private List<Cell> buildNeighbours(int x, int y) {
        List<Cell> neighbours = new ArrayList<Cell>();
        for (int i=-1; i<=1; i++) {
            for (int j=-1; j<=1; j++) {
                if (i==0 && j==0) {continue;}
                try {
                    neighbours.add(cells[x+i][y+j]);
                
                // no neighbour there? array out of bounds? don't add.
                } catch (Exception n) {}
            }
        }
        return neighbours;
    }

    /**
     * decrees each cell to refresh its organism so that it may make a move
     */
    public void refresh() {
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                cells[i][j].refreshOrg();
            }
        }
    }
}
