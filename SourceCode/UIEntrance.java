import javax.swing.*;
import backend.*;
import userInterface.InstallPanel;
import userInterface.MainPanel;

/**
 * Created by xinrui on 6/7/17.
 */

//Main entrance for the application
public class UIEntrance {

    public static void main(String[] args) {
        SoSafeSystem soSafeSystem = SoSafeSystem.getInstance();
        JFrame frame = new JFrame("SoSafeSystem");
        MainPanel mainPanel = new MainPanel(soSafeSystem);
        frame.setContentPane(mainPanel.$$$getRootComponent$$$());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
