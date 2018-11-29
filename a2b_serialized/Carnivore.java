package a2b_serialized;



import java.util.List;

import javafx.scene.paint.Color;

/**
 * a Carnivore is an organism that
 *  is red
 *  can move
 *  can eat herbis and omnis
 *  must eat within 5 moves (else, dies)
 *  procreates, given
 *      >= 3 empty cells
 *      >= 1 adjacent mates
 *      >= 2 adjacent food
 * @author Reneil Pascua
 *
 */
public class Carnivore extends Organism implements OmniEdible {

    /**
     * births a carnivore into a cell
     */
    Carnivore(Cell birthplace) {
        super(birthplace);
        skin = Color.RED;

        // hunger condition
        MAX_HUNGER = 5;
        
        //conditions for birthing
        birth_EMPTY_REQUIRED=3;
        birth_MATES_REQUIRED=1;
        birth_FOOD_REQUIRED=2;
    }

    /** an edible cell contains an organism that implements CarnivoreEdible */
    public boolean canEat(Cell target) {
        return (target.resident instanceof CarnivoreEdible);
    }
    
    /** mates with Carnivore */
    public int numMates(List<Cell> neighbours) {
        int numMates=0;
        for (Cell neighbour : neighbours) {
            if (neighbour.resident!=null && neighbour.resident instanceof Carnivore)
                numMates++;
        }
        return numMates;
    }
    
    /** shoots a new Carnivore into a target empty neighbour */
    public void shootBabyInto(int x, int y) {
        home.world.cells[x][y].resident
        = new Carnivore(home.world.cells[x][y]);
    }
    
}
