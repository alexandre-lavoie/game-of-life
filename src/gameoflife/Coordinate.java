/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class Coordinate {

    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    /**
     * Centers the point according to the image size and the map size.
     * @param sizeGrid Size of map.
     * @param sizeImage Size of image.
     */
    
    public void centerPoint(Size sizeGrid, Size sizeImage){
        x += sizeGrid.getWidth()/2 - sizeImage.getWidth()/2;
        y += sizeGrid.getHeight()/2 - sizeImage.getHeight()/2;
    }
    
    /**
     * Gets points adjacent in all directions to the current point.
     * @return List of adjacent points.
     */
    
    public List<Coordinate> adjacentCoordinates() {
        List<Coordinate> adjCoordinates = new ArrayList();

        for (int sx = -1; sx < 2; sx++) {
            for (int sy = -1; sy < 2; sy++) {
                if (sx == 0 && sy == 0) {
                } else {
                    adjCoordinates.add(new Coordinate(x + sx, y + sy));
                }
            }
        }

        return adjCoordinates;
    }
    
}
