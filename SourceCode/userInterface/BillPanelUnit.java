package userInterface;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xinrui on 6/5/17.
 */

//JPanel for user to choose bill month and system
public class BillPanelUnit extends JPanel {
    private JComboBox monthComboBox;
    private JComboBox SystemComboBox;
    private JPanel billUnit;

    //initial the class
    public BillPanelUnit(){
        $$$setupUI$$$();
    }

    public JComboBox getMonthComboBox() {
        return monthComboBox;
    }

    public JComboBox getSystemComboBox() {
        return SystemComboBox;
    }

    //initial the GUI
    private void $$$setupUI$$$() {
        billUnit = new JPanel();
        billUnit.setLayout(new GridBagLayout());
        billUnit.setPreferredSize(new Dimension(160, 100));
        final JLabel label1 = new JLabel();
        label1.setText("Month");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        billUnit.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("System");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        billUnit.add(label2, gbc);
        monthComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("1");
        defaultComboBoxModel1.addElement("2");
        defaultComboBoxModel1.addElement("3");
        defaultComboBoxModel1.addElement("4");
        defaultComboBoxModel1.addElement("5");
        defaultComboBoxModel1.addElement("6");
        defaultComboBoxModel1.addElement("7");
        defaultComboBoxModel1.addElement("8");
        defaultComboBoxModel1.addElement("9");
        defaultComboBoxModel1.addElement("10");
        defaultComboBoxModel1.addElement("11");
        defaultComboBoxModel1.addElement("12");
        monthComboBox.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        billUnit.add(monthComboBox, gbc);
        SystemComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Both");
        defaultComboBoxModel2.addElement("Fire System");
        defaultComboBoxModel2.addElement("Intrusion System");
        SystemComboBox.setModel(defaultComboBoxModel2);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        billUnit.add(SystemComboBox, gbc);
    }

    //get the root component
    public JComponent $$$getRootComponent$$$() {
        return billUnit;
    }
}
