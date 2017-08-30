package userInterface;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xinrui on 6/4/17.
 */

//main panel in the install panel for user to select sensor to install
public class InstallUnit extends JPanel {
    private JPanel panel1;
    private JCheckBox fireSensorCheckBox;
    private JCheckBox intrusionSensorCheckBox;

    //initial the panel
    public InstallUnit(){
        $$$setupUI$$$();
    }

    //return state of fire check box to check if certain sensor need to be installed
    public JCheckBox getFireSensorCheckBox() {
        return fireSensorCheckBox;
    }

    //return state of intrusion check box to check if certain sensor need to be installed
    public JCheckBox getIntrusionSensorCheckBox() {
        return intrusionSensorCheckBox;
    }

    //initial the GUI
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setOpaque(false);
        fireSensorCheckBox = new JCheckBox();
        fireSensorCheckBox.setOpaque(false);
        fireSensorCheckBox.setText("Fire Sensor");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(fireSensorCheckBox, gbc);
        intrusionSensorCheckBox = new JCheckBox();
        intrusionSensorCheckBox.setOpaque(false);
        intrusionSensorCheckBox.setText("Intrusion Sensor");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(intrusionSensorCheckBox, gbc);
    }

    //return the root component
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
