package userInterface;

import javax.swing.*;
import java.awt.*;
import backend.*;

/**
 * Created by xinrui on 6/5/17.
 */

//integrated panels to this panel to set up GUI for user
public class MainPanel extends JPanel {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private InstallPanel installPanel1;
    private ConfigurePanel configurePanel1;
    private BillPanel billPanel1;
    private StatePanel statePanel1;
    private FakeTimer fakeTimer1;
    private SoSafeSystem soSafeSystem;

    //initial components
    public MainPanel(SoSafeSystem soSafeSystem) {
        this.soSafeSystem = soSafeSystem;
        $$$setupUI$$$();
        installPanel1.setSoSafeSystem(soSafeSystem);
        installPanel1.setStatePanel(statePanel1);
        installPanel1.setFakeTimer(fakeTimer1);
        configurePanel1.setSoSafeSystem(soSafeSystem);
        configurePanel1.setStatePanel(statePanel1);
        configurePanel1.setFakeTimer(fakeTimer1);
        billPanel1.setSoSafeSystem(soSafeSystem);
        statePanel1.setSoSafeSystem(soSafeSystem);
        statePanel1.setFakeTimer(fakeTimer1);
        installPanel1.installPanelDataRecover();
        configurePanel1.configurePanelDataRecover();
    }

    //initial GUI
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setPreferredSize(new Dimension(3000, 3000));
        tabbedPane1 = new JTabbedPane();
        panel1.add(tabbedPane1, BorderLayout.CENTER);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("Installation", panel2);
        installPanel1 = new InstallPanel();
        panel2.add(installPanel1.$$$getRootComponent$$$(), BorderLayout.CENTER);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("Configuration", panel3);
        configurePanel1 = new ConfigurePanel();
        panel3.add(configurePanel1.$$$getRootComponent$$$(), BorderLayout.CENTER);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("Bill", panel4);
        billPanel1 = new BillPanel();
        panel4.add(billPanel1.$$$getRootComponent$$$(), BorderLayout.CENTER);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("State", panel5);
        statePanel1 = new StatePanel();
        panel5.add(statePanel1.$$$getRootComponent$$$(), BorderLayout.CENTER);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridBagLayout());
        panel6.setPreferredSize(new Dimension(100, 100));
        panel1.add(panel6, BorderLayout.EAST);
        fakeTimer1 = new FakeTimer();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel6.add(fakeTimer1.$$$getRootComponent$$$(), gbc);
    }

    //return the root component
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
