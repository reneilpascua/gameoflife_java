package a2b_serialized;



import java.util.List;

import javafx.scene.paint.Color;

/**
 * a Herbivore is an organism that
 *  is yellow
 *  can move
 *  can eat plants
 *  must eat within 5 moves (else, dies)
 *  procreates, given
 *      >= 2 empty cells
 *      >= 1 adjacent mates
 *      >= 2 adjacent food
 * @author Reneil Pascua
 *
 */
public class Herbivore extends Organism implements CarnivoreEdible, OmniEdible {
    
    /** 
     * births a herbivore into a cell 
     */
    Herbivore(Cell birthplace) {
        super(birthplace);
        skin = Color.YELLOW;

        // hunger condition
        MAX_HUNGER = 5;
        
        //conditions for birthing
        birth_EMPTY_REQUIRED=2;
        birth_MATES_REQUIRED=1;
        birth_FOOD_REQUIRED=2;
    }
    
    /** an edible cell contains an organism that implements HerbivoreEdible */
    public boolean canEat(Cell target) {
        return (target.resident instanceof HerbivoreEdible);
    }
    
    /** mates with Herbivore */
    public int numMates(List<Cell> neighbours) {
        int numMates=0;
        for (Cell neighbour : neighbours) {
            if (neighbour.resident!=null && neighbour.resident instanceof Herbivore)
                numMates++;
        }
        return numMates;
    }
    
    /** shoots a new Herbivore into a target empty neighbour */
    public void shootBabyInto(int x, int y) {
        home.world.cells[x][y].resident
        = new Herbivore(home.world.cells[x][y]);
    }

}
