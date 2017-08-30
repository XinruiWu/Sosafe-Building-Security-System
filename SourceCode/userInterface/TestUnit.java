package userInterface;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xinrui on 6/7/17.
 */

//a panel used to choose room number and event type for test purpose
public class TestUnit extends JPanel {
    private JComboBox roomNO;
    private JComboBox eventType;
    private JPanel testUnit;

    //initial the panel
    public TestUnit(){
        $$$setupUI$$$();
    }

    //getters to pass information to state panel and backend to test the sensor
    public JComboBox getRoomNO() {
        return roomNO;
    }

    public JComboBox getEventType() {
        return eventType;
    }

    //initial the GUI
    private void $$$setupUI$$$() {
        testUnit = new JPanel();
        testUnit.setLayout(new GridBagLayout());
        final JLabel label1 = new JLabel();
        label1.setText("Room NO.");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        testUnit.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Event Type");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        testUnit.add(label2, gbc);
        roomNO = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("0");
        defaultComboBoxModel1.addElement("1");
        defaultComboBoxModel1.addElement("2");
        defaultComboBoxModel1.addElement("3");
        defaultComboBoxModel1.addElement("4");
        defaultComboBoxModel1.addElement("5");
        roomNO.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        testUnit.add(roomNO, gbc);
        eventType = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Fire");
        defaultComboBoxModel2.addElement("Intrusion");
        eventType.setModel(defaultComboBoxModel2);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        testUnit.add(eventType, gbc);
    }

    //return the root component
    public JComponent $$$getRootComponent$$$() {
        return testUnit;
    }
}
