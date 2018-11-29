package a2b_serialized;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * creates a graphic representation of a game of life simulation
 * @author Reneil Pascua
 *
 */
public class GUIofLife extends Application {

    /** the game to be visualized */
    GameOfLife game;
    
    /** main scene */
    Scene scene;
    
    Button save;
    Button load;
    
    /** an array to keep track of the rectangles which represent the cells */ 
    Rectangle[][] graphicCells;
    
    /** organizes the layout of the world into a grid */
    GridPane grid = new GridPane();
    GridPane buttons = new GridPane();
    
    /** CONSTANTS */
    final int padding = 1;
    static int numcells = 50;
    static int cellsize = 10;
    final int window_padded = numcells*cellsize + (numcells+4)*padding;
    
    /**
     * starts a game of life, creates the grid and colors the cells.
     * sets the scene to respond to clicks
     */
    public void start(Stage stg) throws Exception {
        game = new GameOfLife(numcells);
        makeGrid();
        colorCells();
        
        grid.setAlignment(Pos.CENTER);
        
        scene = new Scene(grid, window_padded + 100, window_padded + 100);
        scene.setFill(Color.BLACK);
        //scene.setOnMouseClicked(this::next);
        
        stg.setScene(scene);
        stg.show();
    }
    
    /**
     * populates the gridpane and guicells array with rectangles which represent cells
     * NOTE: gridpane.add(child, col, row, colspan, rowspan)
     * but to match Dennis' JAR, switch col and row
     */
    private void makeGrid() {
        graphicCells = new Rectangle[numcells][numcells];
        Rectangle t;
        
        for (int i=0; i<numcells; i++) {
            for (int j=0; j<numcells; j++) {
                t = new Rectangle(cellsize, cellsize, Color.WHITE);
                t.setStroke(Color.BLACK);
                t.setStrokeWidth(1.0D);
                // bind click to next() method
                t.setOnMouseClicked(this::next);
                graphicCells[i][j] = t;
                //switched j and i to match Dennis
                grid.add(t, i, j, 1, 1);
            }
        }
        
        save = new Button("SAVE GAME");
        load = new Button("LOAD GAME");
        save.setOnAction(this::save);
        load.setOnAction(this::load);
        
        
        buttons.add(save, 0, 0);
        buttons.add(load, 1, 0);
        grid.add(buttons, 0, 51, 50, 3);
        buttons.setAlignment(Pos.CENTER);
        buttons.setHgap(20);
        buttons.setVgap(20);
        
    }
    
    /**
     * reads the state of the world and sets the color of the rectangles accordingly
     */
    private void colorCells() {
        for (int i=0; i<numcells; i++) {
            for (int j=0; j<numcells; j++) {
                try {
                    graphicCells[i][j].setFill
                    (game.world.cells[i][j].resident.skin);
                } catch(NullPointerException n) { // game.world.cells[i][j] no resident
                    graphicCells[i][j].setFill(Color.WHITE);
                }
            }
        }
        System.out.println("cells colored");
    }
    
    /**
     * clicking on any rectangle (ie. the world) will trigger this method.
     * updates the game and colors the cells based on the update.
     * @param m
     */
    private void next(MouseEvent m) {
        System.out.println("\n\nworld clicked");
        game.update();
        colorCells();
        System.out.println("finished graphic update");
    }

    private void save(ActionEvent a) {
        System.out.println("\n\nsaving...");
        game.saveWorld();
    }
    
    private void load(ActionEvent a) {
        System.out.println("\n\nloading...");
        game.loadWorld();
        colorCells();
    }
}
