package userInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;

/**
 * Created by xinrui on 6/4/17.
 */

//use this class to import the building image to the system
public class BuildingLayout extends JPanel {

    private BufferedImage image;

    //import the image
    public BuildingLayout(){
        try{
            DataInputStream inputStream = new DataInputStream(StateUnitIntrusionAlarmPanel.class.getResourceAsStream("buildingLayout.png"));
            image = ImageIO.read(inputStream);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
