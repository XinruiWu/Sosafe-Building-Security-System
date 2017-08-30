package userInterface;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xinrui on 6/5/17.
 */

//panel used to display state of each sensor
public class StateUnit extends JPanel {
    private JPanel panel1;
    private JPanel canvas;
    private JPanel fireColor;
    private JPanel intrusionColor;
    private JLabel fireText;
    private JLabel intrusionText;

    //initial the panel
    public StateUnit(){
        $$$setupUI$$$();
    }

    //getters for state panel changing information on this panel unit of each sensor
    public JPanel getFireColor() {
        return fireColor;
    }

    public JPanel getIntrusionColor() {
        return intrusionColor;
    }

    public JLabel getFireText() {
        return fireText;
    }

    public JLabel getIntrusionText() {
        return intrusionText;
    }

    public JPanel getCanvas() {
        return canvas;
    }

    //initial the GUI
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setOpaque(false);
        panel1.setPreferredSize(new Dimension(250, 190));
        canvas = new JPanel();
        canvas.setLayout(new BorderLayout(0, 0));
        canvas.setOpaque(false);
        panel1.add(canvas, BorderLayout.CENTER);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.setOpaque(false);
        panel1.add(panel2, BorderLayout.SOUTH);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel3, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Fire System State:");
        panel3.add(label1, BorderLayout.CENTER);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel4, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Intrusion System State:");
        panel4.add(label2, BorderLayout.CENTER);
        fireColor = new JPanel();
        fireColor.setLayout(new BorderLayout(0, 0));
        fireColor.setBackground(new Color(-855568));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(fireColor, gbc);
        fireText = new JLabel();
        fireText.setBackground(new Color(-855568));
        fireText.setForeground(new Color(-11776948));
        fireText.setText("NOT INSTALLED");
        fireColor.add(fireText, BorderLayout.CENTER);
        intrusionColor = new JPanel();
        intrusionColor.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(intrusionColor, gbc);
        intrusionText = new JLabel();
        intrusionText.setText("NOT INSTALLED");
        intrusionColor.add(intrusionText, BorderLayout.CENTER);
    }

    //return the root component
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
