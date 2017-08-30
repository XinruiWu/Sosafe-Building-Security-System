package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import backend.*;

/**
 * Created by xinrui on 6/5/17.
 */

//JPanel used for the configuration tab to set schedule for sensors
public class ConfigurePanel extends JPanel {
    private JPanel panel1;
    private JButton confirmButton;
    private JPanel cfgTextInstructionPanel;
    private BuildingLayout buildingLayout1;
    private ActivationCtrlUnit activationCtrlUnit5;
    private ActivationCtrlUnit activationCtrlUnit2;
    private ActivationCtrlUnit activationCtrlUnit6;
    private ActivationCtrlUnit activationCtrlUnit3;
    private ActivationCtrlUnit activationCtrlUnit4;
    private ActivationCtrlUnit activationCtrlUnit1;
    private ActivationCtrlCentrlUnit activationCtrlCentrlUnit1;
    private SoSafeSystem soSafeSystem;
    private ArrayList<ActivationCtrlUnit> activationCtrlUnitArrayList;
    private StatePanel statePanel;
    private FakeTimer fakeTimer;

    //initial the class
    public ConfigurePanel() {

        $$$setupUI$$$();

        //add control cells into the main panel, one cell for each room
        this.activationCtrlUnitArrayList = new ArrayList<>();
        activationCtrlUnitArrayList.add(activationCtrlUnit1);
        activationCtrlUnit1.setConfigurePanel(this);
        activationCtrlUnitArrayList.add(activationCtrlUnit2);
        activationCtrlUnit2.setConfigurePanel(this);
        activationCtrlUnitArrayList.add(activationCtrlUnit3);
        activationCtrlUnit3.setConfigurePanel(this);
        activationCtrlUnitArrayList.add(activationCtrlUnit4);
        activationCtrlUnit4.setConfigurePanel(this);
        activationCtrlUnitArrayList.add(activationCtrlUnit5);
        activationCtrlUnit5.setConfigurePanel(this);
        activationCtrlUnitArrayList.add(activationCtrlUnit6);
        activationCtrlUnit6.setConfigurePanel(this);
        activationCtrlCentrlUnit1.setConfigurePanel(this);

        //use confirm button to submit scheule for sensors to the backend
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String system;
                String globalFrom = activationCtrlCentrlUnit1.getFrom().getText();
                String globalTo = activationCtrlCentrlUnit1.getTo().getText();

                //enforce the format of input start and end time
                if ((globalFrom.equals("") && !globalTo.equals("")) || (!globalFrom.equals("") && globalTo.equals(""))) {
                    JOptionPane.showMessageDialog(activationCtrlCentrlUnit1, "Please leave global schedule time textfields all blank or all entered");
                    activationCtrlCentrlUnit1.getFrom().setText("");
                    activationCtrlCentrlUnit1.getTo().setText("");
                }

                //get the type of the system : fire or breakin
                if (activationCtrlCentrlUnit1.getSystemComboBox().getSelectedItem().toString().equals("Fire System")) {
                    system = "Fire";
                } else {
                    system = "Breakin";
                }

                //send user's configuration to backend and save to txt file
                try {
                    File file;
                    String path = SoSafeSystem.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                    if (system.equals("Fire")) {
                        path += "database/cfgfire.txt";
                        file = new File(path);
                    } else {
                        path += "database/cfgbreakin.txt";
                        file = new File(path);
                    }
                    FileWriter fileWriter = new FileWriter(file);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write("");

                    //check every sensor's configuration panel
                    for (int i = 0; i < activationCtrlUnitArrayList.size(); i++) {

                        //not to activete
                        if (!activationCtrlUnitArrayList.get(i).getActivateCheckBox().isSelected()) {
                            soSafeSystem.activateSensor(i, system, false);
                        } else {
                            //activate
                            String localFrom = activationCtrlUnitArrayList.get(i).getFrom().getText();
                            String localTo = activationCtrlUnitArrayList.get(i).getTo().getText();
                            //if times for individual sensor are not complete, use global's
                            if (localFrom.equals("") || localTo.equals("")) {
                                localFrom = globalFrom.equals("") ? "0000" : globalFrom;
                                localTo = globalTo.equals("") ? "2359" : globalTo;
                            }
                            //tell backend to activate the censor
                            soSafeSystem.activateSensor(i, system, true);
                            soSafeSystem.setSensorSchedule(i, system, Long.parseLong(localFrom), Long.parseLong(localTo), fakeTimer.getDateLable().getText() + " " + fakeTimer.getHourLable().getText());
                            bufferedWriter.append(i + " " + localFrom + " " + localTo + "\n");
                        }
                    }
                    bufferedWriter.close();
                    fileWriter.close();
                    statePanel.updateState();
                    JOptionPane.showMessageDialog(statePanel, "Settings Saved!!!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        //switch systems between fire and intrusion
        activationCtrlCentrlUnit1.getSystemComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file;
                    String system;

                    //initial every configuration cell
                    activationCtrlCentrlUnit1.getActivateCheckBox().setSelected(false);
                    activationCtrlCentrlUnit1.getFrom().setText("");
                    activationCtrlCentrlUnit1.getTo().setText("");
                    for (ActivationCtrlUnit a : activationCtrlUnitArrayList) {
                        a.clear();
                    }

                    //choose file to load the data
                    if (activationCtrlCentrlUnit1.getSystemComboBox().getSelectedItem().toString().equals("Fire System")) {
                        String path = SoSafeSystem.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                        path += "database/cfgfire.txt";
                        file = new File(path);
                        system = "Fire";
                    } else {
                        String path = SoSafeSystem.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                        path += "database/cfgbreakin.txt";
                        file = new File(path);
                        system = "Breakin";
                    }

                    //load data to backend and GUI
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] parsedline = line.split(" ");
                        int index = Integer.parseInt(parsedline[0]);
                        activationCtrlUnitArrayList.get(index).getActivateCheckBox().setSelected(true);
                        activationCtrlUnitArrayList.get(index).getFrom().setText(parsedline[1]);
                        activationCtrlUnitArrayList.get(index).getTo().setText(parsedline[2]);
                        soSafeSystem.activateSensor(index, system, true);
                        soSafeSystem.setSensorSchedule(index, system, Long.parseLong(parsedline[1]), Long.parseLong(parsedline[2]), fakeTimer.getDateLable().getText() + " " + fakeTimer.getHourLable().getText());
                    }
                    bufferedReader.close();
                    fileReader.close();
                    statePanel.updateState();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    //recover data from file when open the GUI
    public void configurePanelDataRecover() {
        activationCtrlCentrlUnit1.getSystemComboBox().setSelectedIndex(1);
        activationCtrlCentrlUnit1.getSystemComboBox().setSelectedIndex(0);
    }

    //when one of the censor is unchecked for activate, uncheck the centre checkbox for the whole system
    public void centrlUnitUnCheck() {
        activationCtrlCentrlUnit1.getActivateCheckBox().setSelected(false);
    }

    //when the centre checkbox is checked, check checkboxes of all the configuration cells.
    public void checkActivationCtrlUnit() {
        for (int i = 0; i < activationCtrlUnitArrayList.size(); i++) {
            activationCtrlUnitArrayList.get(i).getActivateCheckBox().setSelected(true);
        }
    }

    /**use fake time to simulate the real time, 3600X faster time
     * link the faetime class to configuration panel for schedule purpose
     * @param fakeTimer
     */
    public void setFakeTimer(FakeTimer fakeTimer) {
        this.fakeTimer = fakeTimer;
    }

    //link the state panel to configuration panel to show the working state of each sensor
    public void setStatePanel(StatePanel statePanel) {
        this.statePanel = statePanel;
    }

    //link the backend to configuration panel to make changes to data
    public void setSoSafeSystem(SoSafeSystem soSafeSystem) {
        this.soSafeSystem = soSafeSystem;
    }

    //initial the GUI
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel1.add(panel2, BorderLayout.SOUTH);
        confirmButton = new JButton();
        confirmButton.setText("Confirm");
        panel2.add(confirmButton, BorderLayout.EAST);
        activationCtrlCentrlUnit1 = new ActivationCtrlCentrlUnit();
        panel2.add(activationCtrlCentrlUnit1.$$$getRootComponent$$$(), BorderLayout.CENTER);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel1.add(panel3, BorderLayout.CENTER);
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "MAP"));
        cfgTextInstructionPanel = new JPanel();
        cfgTextInstructionPanel.setLayout(new GridBagLayout());
        panel3.add(cfgTextInstructionPanel, BorderLayout.NORTH);
        final JLabel label1 = new JLabel();
        label1.setText("If the time slot of the sensor is blank, the sensor will only be functional during the time decleared in the textfields next to Comfirm Button.");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        cfgTextInstructionPanel.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("If no time is decleared at all, all sensors will work 24 hours a day.");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        cfgTextInstructionPanel.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Checked sensors will be activated during the time specified.Input time should be 4 digits between 0000 and 2359.");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        cfgTextInstructionPanel.add(label3, gbc);
        buildingLayout1 = new BuildingLayout();
        buildingLayout1.setLayout(new GridBagLayout());
        panel3.add(buildingLayout1, BorderLayout.CENTER);
        activationCtrlUnit5 = new ActivationCtrlUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(activationCtrlUnit5.$$$getRootComponent$$$(), gbc);
        activationCtrlUnit2 = new ActivationCtrlUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(activationCtrlUnit2.$$$getRootComponent$$$(), gbc);
        activationCtrlUnit6 = new ActivationCtrlUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(activationCtrlUnit6.$$$getRootComponent$$$(), gbc);
        activationCtrlUnit3 = new ActivationCtrlUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(activationCtrlUnit3.$$$getRootComponent$$$(), gbc);
        activationCtrlUnit4 = new ActivationCtrlUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(activationCtrlUnit4.$$$getRootComponent$$$(), gbc);
        activationCtrlUnit1 = new ActivationCtrlUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        buildingLayout1.add(activationCtrlUnit1.$$$getRootComponent$$$(), gbc);
    }

    //return the root component
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
