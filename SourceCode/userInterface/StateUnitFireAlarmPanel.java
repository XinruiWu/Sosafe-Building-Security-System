package userInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;

/**
 * Created by xinrui on 6/5/17.
 */

//a panel used to display alarm image of fire event
public class StateUnitFireAlarmPanel extends JPanel{

    private BufferedImage fireImage;

    //load the image
    public StateUnitFireAlarmPanel(){
        try{
            DataInputStream inputStream = new DataInputStream(StateUnitFireAlarmPanel.class.getResourceAsStream("fire.png"));
            fireImage = ImageIO.read(inputStream);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(fireImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
