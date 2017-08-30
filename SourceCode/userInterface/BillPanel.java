package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import backend.*;

/**
 * Created by xinrui on 6/5/17.
 */

//JPanel used to display content of Bill Tab
public class BillPanel extends JPanel {
    private JPanel panel1;
    private BillPanelUnit billPanelUnit1;
    private JButton confirmButton;
    private JTextPane billDetail;
    private SoSafeSystem soSafeSystem;

    //initial the Panel
    public BillPanel() {

        $$$setupUI$$$();

        //use confirm button to submit request of bills of which month and which system to show.
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int system;
                if (billPanelUnit1.getSystemComboBox().getSelectedItem().toString().equals("Both")) {
                    system = 2;
                } else if (billPanelUnit1.getSystemComboBox().getSelectedItem().toString().equals("Fire System")) {
                    system = 1;
                } else {
                    system = 0;
                }
                billDetail.setText("");
                //retrieve information from backend
                billDetail.setText(soSafeSystem.displayBill(Integer.parseInt(billPanelUnit1.getMonthComboBox().getSelectedItem().toString()), system));
            }
        });
    }

    //build connection between configurePanel and the centre controller
    public void setSoSafeSystem(SoSafeSystem soSafeSystem) {
        this.soSafeSystem = soSafeSystem;
    }

    //initial the GUI
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel1.add(panel2, BorderLayout.WEST);
        billPanelUnit1 = new BillPanelUnit();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel2.add(billPanelUnit1.$$$getRootComponent$$$(), gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel1.add(panel3, BorderLayout.SOUTH);
        confirmButton = new JButton();
        confirmButton.setText("Confirm");
        panel3.add(confirmButton, BorderLayout.EAST);
        billDetail = new JTextPane();
        billDetail.setEditable(false);
        billDetail.setEnabled(true);
        billDetail.putClientProperty("JEditorPane.honorDisplayProperties", Boolean.TRUE);
        panel1.add(billDetail, BorderLayout.CENTER);
    }

    //get the root component
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
