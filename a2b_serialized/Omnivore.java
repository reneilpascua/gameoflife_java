package a2b_serialized;



import java.util.List;

import javafx.scene.paint.Color;

/**
 * an Omnivore is an organism that
 *  is blue
 *  can move
 *  can eat carnis, herbis, and plants
 *  must eat within 5 moves (else, dies)
 *  procreates, given
 *      >= 3 empty cells
 *      >= 1 adjacent mates
 *      >= 1 adjacent food
 * @author Reneil Pascua
 *
 */
public class Omnivore extends Organism implements CarnivoreEdible {

    /**
     * births a carnivore into a cell
     */
    Omnivore(Cell birthplace) {
        super(birthplace);
        skin = Color.BLUE;

        // hunger condition
        MAX_HUNGER = 5;
        
        //conditions for birthing
        birth_EMPTY_REQUIRED=3;
        birth_MATES_REQUIRED=1;
        birth_FOOD_REQUIRED=1;
    }

    /** an edible cell contains an organism that implements CarnivoreEdible */
    public boolean canEat(Cell target) {
        return (target.resident instanceof OmniEdible);
    }
    
    /** mates with Omnivore */
    public int numMates(List<Cell> neighbours) {
        int numMates=0;
        for (Cell neighbour : neighbours) {
            if (neighbour.resident!=null && neighbour.resident instanceof Omnivore)
                numMates++;
        }
        return numMates;
    }
    
    /** shoots a new Omnivore into a target empty neighbour */
    public void shootBabyInto(int x, int y) {
        home.world.cells[x][y].resident
        = new Omnivore(home.world.cells[x][y]);
    }
    
}
