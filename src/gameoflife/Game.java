/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class Game {

    private static final int WIDTH = 100; //Width of the board.
    private static final int HEIGHT = 75; //Height of the board.
    private final Size GRIDSIZE = new Size(WIDTH,HEIGHT); //Board size.
    private static final int SCALE = 10; //Scale multiplier for the board (used for the image).
    private static final int DELAY = 1; //Delay for the update.
    private int[][] map, newMap, neighbors; //Maps used to set neighbors and the positions where the cells are live.
    private List<Coordinate> livecells = new ArrayList(); //List of current live cells.
    private Size searchSize = GRIDSIZE; //Search size for cells. Sets as grid size initally not knowing the positions of cells.
    private final UI ui = new UI(new Size(WIDTH, HEIGHT, SCALE)); //Initializes the UI.
    private String fileName; //Holds the file name of the file picker.
    private final FilePicker filePicker = new FilePicker(this); //Initializes the file picker.
    
    public Game() {}

    /**
     * Starts the game with a black and white image.
     * @param img Black and white image to use.
     */
    
    public void startGame(BufferedImage img) {

        initializeMap(img);

        update();

    }

    /**
     * Takes in a black and white image and creates a map from it.
     * @param img Black and white image to use.
     */
    
    private void initializeMap(BufferedImage img) {
        Size sizeImg = new Size(img.getWidth(), img.getHeight());
        
        map = new int[WIDTH][HEIGHT];
        
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                if ((img.getRGB(x, y) & 0x00FFFFFF) < 65794) {
                    Coordinate coordinate = new Coordinate(x,y);
                    coordinate.centerPoint(GRIDSIZE, sizeImg);
                    livecells.add(coordinate);
                    map[coordinate.getX()][coordinate.getY()]=1;
                }
            }
        }
    }

    /**
     * Game update.
     */
    
    private void update() {
        ui.nextGeneration();

        getNeighbors();
        
        ui.drawMap(livecells, false, fileName);//ui.drawMap(neighbors, searchSize, false, fileName);//ui.drawMap(map, neighbors, searchSize, false, fileName);

        getNewMap();

        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException ex) {
        }

        update();
    }

    /**
     * Gets the neighboring amount of cells for each cell.
     */
    
    private void getNeighbors() {
        neighbors = new int[WIDTH][HEIGHT];
        for (int x = searchSize.getCoordinate().getX() + 1; x < searchSize.getBottomCoordinate().getX() - 1; x++) {
            for (int y = searchSize.getCoordinate().getY() + 1; y < searchSize.getBottomCoordinate().getY() - 1; y++) {
                List<Coordinate> adjCoordinates = new Coordinate(x, y).adjacentCoordinates();
                for (Coordinate coordinate : adjCoordinates) {
                    neighbors[coordinate.getX()][coordinate.getY()] += map[x][y];
                }
            }
        }
    }

    /**
     * Uses the previous map and performs the Game Of Life Rules to generate a new map.
     */
    
    private void getNewMap() {

        newMap = new int[WIDTH][HEIGHT];

        livecells = new ArrayList();

        Size newSize = new Size(new Coordinate(WIDTH / 2, HEIGHT / 2), new Coordinate(WIDTH / 2, HEIGHT / 2));

        for (int x = searchSize.getCoordinate().getX(); x < searchSize.getBottomCoordinate().getX(); x++) {
            for (int y = searchSize.getCoordinate().getY(); y < searchSize.getBottomCoordinate().getY(); y++) {
                if ((map[x][y] == 1 && (neighbors[x][y] == 2 | neighbors[x][y] == 3))|(map[x][y] == 0 && neighbors[x][y] == 3)) {
                    livecells.add(new Coordinate(x, y));
                    newSize.grow(new Coordinate(x, y), GRIDSIZE);
                    newMap[x][y] = 1;
                }
            }
        }

        searchSize = newSize;

        map = newMap;
    }
    
    public void setFileName(String fileName){
        this.fileName = fileName;
    }
    
}
