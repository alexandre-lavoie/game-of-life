/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

/**
 *
 * @author User
 */
public class Size {
    
    private int Width = 0;
    private int Height = 0;
    private int Scale = 1;
    private Coordinate coordinate = new Coordinate(0,0);
    private Coordinate coordinateBottom = new Coordinate(0,0);
    
    public Size(int Width, int Height){
        this.Width = Width;
        this.Height = Height;
        this.coordinateBottom = new Coordinate(Width,Height);
    }
    
    public Size(int Width, int Height, int Scale){
        this.Width = Width;
        this.Height = Height;
        this.Scale = Scale;
    }
    
    public Size(Coordinate coordinate, Coordinate coordinateBottom){
        this.coordinate = coordinate;
        this.coordinateBottom = coordinateBottom;
    }
     
    public int getWidth(){
        return Width;
    }
    
    public int getScaledWidth(){
        return Width*Scale;
    }
    
    public int getScaledHeight(){
        return Height*Scale;
    }
    
    public int getHeight(){
        return Height;
    }
    
    public int getScale(){
        return Scale;
    }
    
    public Coordinate getCoordinate(){
        return coordinate;
    }
    
    public Coordinate getBottomCoordinate(){
        return coordinateBottom;
    }
    
    /**
     * Grows the size to contain the coordinates.
     * @param position The position to contain in the size.
     * @param border The size of the map.
     */
    
    public void grow(Coordinate position, Size border){
        if(position.getX()<=coordinate.getX()&&position.getX()-2>-1){
            coordinate.setX(position.getX()-2);
        }
        
        if(position.getY()<=coordinate.getY()&&position.getY()-2>-1){
            coordinate.setY(position.getY()-2);
        }
        
        if(position.getX()>=coordinateBottom.getX()-1&&position.getX()+2<border.Width){
            coordinateBottom.setX(position.getX()+2);
        }
        
        if(position.getY()>=coordinateBottom.getY()-1&&position.getY()+2<border.Height){
            coordinateBottom.setY(position.getY()+2);
        }
    }
    
}
