package userInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;

/**
 * Created by xinrui on 6/5/17.
 */

//a panel used to show alarm image for breakin event
public class StateUnitIntrusionAlarmPanel extends JPanel{

    private BufferedImage intrusionImage;

    //load the image
    public StateUnitIntrusionAlarmPanel(){
        try{
            DataInputStream inputStream = new DataInputStream(StateUnitIntrusionAlarmPanel.class.getResourceAsStream("intrusion.png"));
            intrusionImage = ImageIO.read(inputStream);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(intrusionImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
