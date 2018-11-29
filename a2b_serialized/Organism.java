package a2b_serialized;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

/**
 * An organism lives on the world, on 1 specific cell at a time, 
 * and may do at most 1 thing at a time.
 * It is a functional part of the game of life which can be defined
 * to respond to certain events / stimuli
 * @author Reneil Pascua
 *
 */
public abstract class Organism implements Serializable {

    /** the cell on which the organism resides in the current turn */
    Cell home;
    /** lets the game know if the organism has done something this turn */
    boolean hasFinishedItsTurn = true;
    /** the color of the organism as represented in javafx */
    transient Color skin;
    
    // to address hunger and starving to death
    int hunger = 0;
    int MAX_HUNGER;
    
    // birthing conditions
    int birth_EMPTY_REQUIRED;
    int birth_MATES_REQUIRED;
    int birth_FOOD_REQUIRED;
    
    /**
     * births an organism into a cell
     * @param birthplace
     */
    Organism(Cell birthplace) {
        home = birthplace;
    }
    
    /** 
     * tells an organism to "do something" when its turn comes
     * generally includes birthing or moving
     * if an organism gives birth, it is too tired to move
     * also checks whether an organism has starved to death
     */
    public void takeTurn() {
        
        // observe your neighbours
        List<Cell> neighbours = home.getNeighbours();
        
        if (isFertile(neighbours)) {
            birth(neighbours);
            // doesn't eat
            hunger++;
        } else {
            // !! handles moving, eating, and hunger increment
            move(neighbours);
        }
        hasFinishedItsTurn=true;
        
        // check if starved to death
        if (hunger == MAX_HUNGER) {
            die();
        }
    }
    
//////////////////////////
/////// M O V E M E N T //
//////////////////////////
    
    protected void move(List<Cell> neighbours) {
        List<Integer> movableCells = getMovableCells(neighbours);
        // no movable cells? then don't move.
        if (movableCells.size() == 0) {return;}
        
        // choose the index of a random movable neighbour
        int thisneighbour = RandomGenerator.nextNumber(movableCells.size());
        int moveIndex = movableCells.get(thisneighbour);
        
        // will this be able to eat at the chosen cell?
        if (canEat(neighbours.get(moveIndex))) {
            hunger = 0;
        } else {hunger++;}
        
        // get the coordinates of where this wants to move
        int newX = neighbours.get(moveIndex).location[0];
        int newY = neighbours.get(moveIndex).location[1];
        
        // the actual movement (overwrites resident if there is one)
        home.world.cells[newX][newY].resident = this;
        this.home.resident = null;
        this.home = home.world.cells[newX][newY];
    }
    private List<Integer> getMovableCells(List<Cell> neighbours) {
        List<Integer> movableCells = new ArrayList<>();
        for (int i=0; i<neighbours.size(); i++) {
            if (canMoveInto(neighbours.get(i)))
                movableCells.add(i);
        }
        return movableCells;
    }
    private boolean canMoveInto(Cell target) {
        return (target.resident == null     ||     canEat(target));
    }
    
    /** defines if a target cell contains something edible */
    public abstract boolean canEat(Cell target);
    
//////////////////////////
/////// B I R T H I N G //
//////////////////////////
    
    private void birth(List<Cell> neighbours) {
        List<Integer> birthableCells = getEmptyNeighbours(neighbours);
        
        // choose one index randomly
        int thisneighbour = RandomGenerator.nextNumber(birthableCells.size());
        int birthIndex = birthableCells.get(thisneighbour);
        
        // get the coordinates of where you want to shoot the baby
        int newX = neighbours.get(birthIndex).location[0];
        int newY = neighbours.get(birthIndex).location[1];
        
        // get the neighbour on this index and make a new plant there
        shootBabyInto(newX, newY);
    }
    private boolean isFertile(List<Cell> neighbours) {
        return (    getEmptyNeighbours(neighbours).size()   >= birth_EMPTY_REQUIRED
                &&  numMates(neighbours)                    >= birth_MATES_REQUIRED
                &&  numFood(neighbours)                     >= birth_FOOD_REQUIRED);
    }
    private List<Integer> getEmptyNeighbours(List<Cell> neighbours) {
        List<Integer> empties = new ArrayList<Integer>();
        for (int i=0; i<neighbours.size(); i++) {
            if (neighbours.get(i).resident == null) {
                empties.add(i);
            }
        }
        return empties;
    }
    protected abstract int numMates(List<Cell> neighbours);
    private int numFood(List<Cell> neighbours) {
        int numFood=0;
        for (Cell neighbour : neighbours) {
            if (neighbour.resident!=null && canEat(neighbour))
                numFood++;
        }
        return numFood;
    }
    
    /** shoots a baby (specific to subclass) into a target cell */
    public abstract void shootBabyInto(int x, int y);
       
    /** dies by leaving its home cell and going to garbage collection hell */
    public void die() {
        home.resident = null;
        this.home = null;
    }

}
