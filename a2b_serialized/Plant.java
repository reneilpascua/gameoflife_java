package a2b_serialized;



import java.util.List;

import javafx.scene.paint.Color;

/**
 * a Plant is an organism that
 *  is green
 *  can't move
 *  doesn't eat
 *  doesn't starve to death
 *  procreates, given
 *      >= 3 empty cells
 *      >= 2 adjacent mates
 *      >= 0 adjacent food
 * @author Reneil Pascua
 *
 */
public class Plant extends Organism implements HerbivoreEdible, OmniEdible {
    
    /**
     * births a plant into a cell
     */
    Plant(Cell birthplace) {
        super(birthplace);
        skin = Color.GREEN;

        //plants will never reach this (they're designed to not get hungry)
        MAX_HUNGER = -1;
        
        //conditions for birthing
        birth_EMPTY_REQUIRED=3;
        birth_MATES_REQUIRED=2;
        birth_FOOD_REQUIRED=0;
    }
    
    /** mates with Plant */
    public int numMates(List<Cell> neighbours) {
        int numMates=0;
        for (Cell neighbour : neighbours) {
            if (neighbour.resident!=null && neighbour.resident instanceof Plant)
                numMates++;
        }
        return numMates;
    }
    
    /** shoots a new Plant into a target empty neighbour */
    public void shootBabyInto(int x, int y) {
        home.world.cells[x][y].resident
        = new Plant(home.world.cells[x][y]);
    }
    
    /** can't eat */
    public boolean canEat(Cell target) {return false;}
    
    /** doesn't starve to death */
    @Override
    public void die() {}
    
    /** can't move */
    @Override
    public void move(List<Cell> neighbours) {}
}
