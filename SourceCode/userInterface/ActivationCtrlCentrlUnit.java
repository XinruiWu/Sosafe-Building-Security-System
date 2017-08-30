package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by xinrui on 6/5/17.
 */

//JPanel used to set working schedule for all sensers
public class ActivationCtrlCentrlUnit extends JPanel {
    private JCheckBox activateCheckBox;
    private JPanel panel1;
    private JTextField from;
    private JTextField to;
    private JComboBox systemComboBox;
    private ConfigurePanel configurePanel;

    //initial the JPanel
    public ActivationCtrlCentrlUnit() {
        $$$setupUI$$$();
        from.setText("");
        to.setText("");
        //if checkbox checked from centre controller, set all sensors checked
        activateCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activateCheckBox.isSelected()) {
                    configurePanel.checkActivationCtrlUnit();
                }
            }
        });

        /**get input of the start time of working schedule
         * and enforce the input to be four digit betwenn 0000 and 2359
         */
        from.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = from.getText();
                if (input.equals("")) {
                    return;
                }
                if (input.length() != 4) {
                    JOptionPane.showMessageDialog(from, "Please input 4 digit between 0000 and 2359");
                    from.setText("");
                    return;
                }
                char[] temp = input.toCharArray();
                for (int i = 0; i < 4; i++) {
                    if (!Character.isDigit(temp[i])) {
                        JOptionPane.showMessageDialog(from, "Please input 4 digit between 0000 and 2359");
                        from.setText("");
                        return;
                    }
                }
                int check = Integer.parseInt(input);
                if (check < 0 || check > 2359) {
                    JOptionPane.showMessageDialog(from, "Please input 4 digit between 0000 and 2359");
                    from.setText("");
                    return;
                }
            }
        });

        /**get input of the end time of working schedule
         * and enforce the input to be four digit betwenn 0000 and 2359
         */
        to.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = to.getText();
                if (input.equals("")) {
                    return;
                }
                if (input.length() != 4) {
                    JOptionPane.showMessageDialog(to, "Please input 4 digit between 0000 and 2359");
                    to.setText("");
                    return;
                }
                char[] temp = input.toCharArray();
                for (int i = 0; i < 4; i++) {
                    if (!Character.isDigit(temp[i])) {
                        JOptionPane.showMessageDialog(to, "Please input 4 digit between 0000 and 2359");
                        to.setText("");
                        return;
                    }
                }
                int check = Integer.parseInt(input);
                if (check < 0 || check > 2359) {
                    JOptionPane.showMessageDialog(to, "Please input 4 digit between 0000 and 2359");
                    to.setText("");
                    return;
                }
            }
        });
    }

    //build connection between configurePanel and the centre controller
    public void setConfigurePanel(ConfigurePanel configurePanel) {
        this.configurePanel = configurePanel;
    }

    //getter for activateCheckBox
    public JCheckBox getActivateCheckBox() {
        return activateCheckBox;
    }

    //getter for ComboBox designed for selecting fire system or breakin system
    public JComboBox getSystemComboBox() {
        return systemComboBox;
    }

    //getter for starting time for the system
    public JTextField getFrom() {
        return from;
    }

    //getter for end time for the system
    public JTextField getTo() {
        return to;
    }

    //initial the GUI
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        systemComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Fire System");
        defaultComboBoxModel1.addElement("Intrusion System");
        systemComboBox.setModel(defaultComboBoxModel1);
        panel1.add(systemComboBox);
        activateCheckBox = new JCheckBox();
        activateCheckBox.setText("Activate");
        panel1.add(activateCheckBox);
        final JLabel label1 = new JLabel();
        label1.setText("From");
        panel1.add(label1);
        from = new JTextField();
        from.setPreferredSize(new Dimension(40, 28));
        panel1.add(from);
        final JLabel label2 = new JLabel();
        label2.setText("To");
        panel1.add(label2);
        to = new JTextField();
        to.setPreferredSize(new Dimension(40, 28));
        panel1.add(to);
    }

    //getter for root component
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
