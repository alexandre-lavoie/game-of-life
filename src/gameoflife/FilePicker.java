/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author User
 */
public class FilePicker {

    private JFileChooser fc; // The file chooser.
    private JButton open; // The submit button.

    public FilePicker(Game game) {
        createFrame();

        if (fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION);

        game.setFileName(fc.getSelectedFile().getName().substring(0, fc.getSelectedFile().getName().lastIndexOf(".")));
        
        getFile(game);
    }

    /**
     * Creates the file picker.
     */
    private void createFrame() {
        fc = new JFileChooser();
        open = new JButton();
        fc.setDialogTitle("Pick a Game Of Life Image");
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", ImageIO.getReaderFileSuffixes()));
    }

    /**
     * Reads the picked image file and sets it as the game map.
     *
     * @param game The game controller with the map.
     */
    private void getFile(Game game) {

        BufferedImage img;

        try {

            img = ImageIO.read(new File(fc.getSelectedFile().getAbsolutePath()));

            game.startGame(img);
        } catch (IOException e) {
        }
    }
}
