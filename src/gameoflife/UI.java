/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;

/**
 *
 * @author User
 */
public class UI {

    private JFrame jFrame; //JFrame for the game.
    private JLabel jImage; //JLabel containing image of the game.
    private final Size size; //Size of map.
    private int Generation = 0; //Generation of game.
    private static final String PATH = "C:/Users/User/Desktop/"; //Path to save file.

    public UI(Size size) {
        this.size = size;

        createFrame();
    }

    /**
     * Creates the JFrame.
     */
    
    private void createFrame() {
        jFrame = new JFrame("Game Of Life - Generation: 0");
        jFrame.setSize(size.getScaledWidth(), size.getScaledHeight());
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jImage = new JLabel(new ImageIcon(new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_RGB).getScaledInstance(size.getScaledWidth(), size.getScaledHeight(), 100)));
        jFrame.add(jImage);
        jFrame.setVisible(true);
    }

    /**
     * Adds 1 to the current generation.
     */
    
    public void nextGeneration(){
        Generation++;
    }
    
    /**
     * Draws Live Cells On A Black Background and Displays It On The JFrame.
     * @param Coordinates List of Coordinates of Live Cells.
     * @param DrawImage True To Save Map To A File.
     * @param FileName File Name For To Save Image.
     */
    
    public void drawMap(List<Coordinate> Coordinates, boolean DrawImage, String FileName) {
        jFrame.setTitle("Game Of Life - Generation: " + Generation);

        BufferedImage newImage = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_RGB);

        Coordinates.forEach((coordinate) -> {
            newImage.setRGB(coordinate.getX(), coordinate.getY(), Color.WHITE.getRGB());
        });

        jImage.setIcon(new ImageIcon(newImage.getScaledInstance(size.getScaledWidth(), size.getScaledHeight(), 100)));

        if (DrawImage) {
            outputImage(newImage, PATH, FileName + "_n");
        }
    }

     /**
     * Draws Number of Neighbors of Each Point.
     * @param heat Map of neighbors.
     * @param scale Region Of Map To Output.
     * @param DrawImage True To Save Map To A File.
     * @param FileName File Name For To Save Image.
     */
    
    public void drawMap(int[][] heat, Size scale, boolean DrawImage, String FileName) {
        jFrame.setTitle("Game Of Life - Generation: " + Generation);

        BufferedImage newImage = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = scale.getCoordinate().getX(); x < scale.getBottomCoordinate().getX(); x++) {
            for (int y = scale.getCoordinate().getY(); y < scale.getBottomCoordinate().getY(); y++) {
                newImage.setRGB(x, y, new Color(heat[x][y] * 31, heat[x][y] * 31, heat[x][y] * 31).getRGB());
            }
        }

        jImage.setIcon(new ImageIcon(newImage.getScaledInstance(size.getScaledWidth(), size.getScaledHeight(), 100)));

        if (DrawImage) {
            outputImage(newImage, PATH, FileName + "_h");
        }
    }

    /**
     * Draws Cells that Will be Born and Cells that will Continue to Live On.
     * @param map Map of current live cells.
     * @param heat Map of neighbors.
     * @param scale Region with Information.
     * @param DrawImage True To Save Map To A File.
     * @param FileName File Name For To Save Image.
     */
    
    public void drawMap(int[][] map, int[][] heat, Size scale, String FileName, boolean DrawImage) {
        jFrame.setTitle("Game Of Life - Generation: " + Generation);

        BufferedImage newImage = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = scale.getCoordinate().getX(); x < scale.getBottomCoordinate().getX(); x++) {
            for (int y = scale.getCoordinate().getY(); y < scale.getBottomCoordinate().getY(); y++) {
                if (map[x][y] == 1 && (heat[x][y] == 2 | heat[x][y] == 3)) {
                    newImage.setRGB(x, y, Color.RED.getRGB());
                } else if (map[x][y] == 0 && heat[x][y] == 3) {
                    newImage.setRGB(x, y, Color.BLUE.getRGB());
                } else if (map[x][y] == 1){
                    newImage.setRGB(x, y, Color.GREEN.getRGB());
                }
            }
        }

        jImage.setIcon(new ImageIcon(newImage.getScaledInstance(size.getScaledWidth(), size.getScaledHeight(), 100)));

        if (DrawImage) {
            outputImage(newImage, PATH, FileName + "_l");
        }
    }

    /**
     * Save the buffered image to a file.
     * @param image The image to save.
     * @param Path The path to the file.
     * @param Name The file name.
     */
    
    public void outputImage(BufferedImage image, String Path, String Name) {
        try {
            File outputfile;

            new File(Path + Name + "/").mkdirs();

            if (Generation < 10) {
                outputfile = new File(Path + Name + "/" + Name + "_000" + Generation + ".png");
            } else if (Generation < 100) {
                outputfile = new File(Path + Name + "/" + Name + "_00" + Generation + ".png");
            } else if (Generation < 1000) {
                outputfile = new File(Path + Name + "/" + Name + "_0" + Generation + ".png");
            } else {
                outputfile = new File(Path + Name + "/" + Name + +Generation + ".png");
            }

            BufferedImage bImage = new BufferedImage(size.getScaledWidth(), size.getScaledHeight(), BufferedImage.TYPE_INT_RGB);

            Graphics2D bImageGraphics = bImage.createGraphics();

            bImageGraphics.drawImage(image.getScaledInstance(size.getScaledWidth(), size.getScaledHeight(), 100), null, null);

            ImageIO.write(bImage, "png", outputfile);
        } catch (IOException e) {
        }
    }

}
