package userInterface;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import backend.*;

/**
 * Created by xinrui on 6/5/17.
 */

//panel to show the state of sensors and to do the test
public class StatePanel extends JPanel {
    private BuildingLayout buildingLayout1;
    private JPanel panel1;
    private StateUnit stateUnit1;
    private StateUnit stateUnit5;
    private StateUnit stateUnit2;
    private StateUnit stateUnit6;
    private StateUnit stateUnit3;
    private StateUnit stateUnit4;
    private JButton confirmButton;
    private TestUnit testPanel;
    private SoSafeSystem soSafeSystem;
    private StateUnitFireAlarmPanel stateUnitFireAlarmPanel;
    private StateUnitIntrusionAlarmPanel stateUnitIntrusionAlarmPanel;
    private ArrayList<StateUnit> stateUnitArrayList;
    private List<Sensor> installedSensorList;
    private List<Sensor> activatedSensorList;
    private FakeTimer fakeTimer;

    //initial the panel
    public StatePanel() {
        $$$setupUI$$$();
        stateUnitArrayList = new ArrayList<>();
        stateUnitArrayList.add(stateUnit1);
        stateUnitArrayList.add(stateUnit2);
        stateUnitArrayList.add(stateUnit3);
        stateUnitArrayList.add(stateUnit4);
        stateUnitArrayList.add(stateUnit5);
        stateUnitArrayList.add(stateUnit6);
        this.stateUnitFireAlarmPanel = new StateUnitFireAlarmPanel();
        this.stateUnitIntrusionAlarmPanel = new StateUnitIntrusionAlarmPanel();

        //use confirm button to send information simulation events to the system
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(testPanel.getRoomNO().getSelectedItem().toString());
                String type = testPanel.getEventType().getSelectedItem().toString().equals("Fire") ? "Fire" : "Breakin";
                //trigger an event
                soSafeSystem.triggerEvent(id, type, fakeTimer.getDateLable().getText() + " " + fakeTimer.getHourLable().getText());
            }
        });
    }

    //set callback function for the backend to let the data from backend show in the GUI
    public void setSoSafeSystem(SoSafeSystem soSafeSystem) {
        this.soSafeSystem = soSafeSystem;
        soSafeSystem.setUICallback(new UICallback() {

            //function to let the GUI display event and ask for PIN
            @Override
            public void onAlarm(int ID, String type) {

                //use txt file to record the event log
                try {
                    String path = SoSafeSystem.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                    path += "database/event.txt";
                    File file = new File(path);
                    FileWriter fileWriter = new FileWriter(file, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    String[] strings = fakeTimer.getDateLable().getText().split("/");
                    bufferedWriter.append(type + " " + strings[1] + "\n");
                    bufferedWriter.close();
                    fileWriter.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                //show alarm pictures on GUI
                if (type.equals("Fire")) {
                    stateUnitArrayList.get(ID).getCanvas().add(stateUnitFireAlarmPanel);
                    stateUnitArrayList.get(ID).getCanvas().repaint();
                } else {
                    stateUnitArrayList.get(ID).getCanvas().add(stateUnitIntrusionAlarmPanel);
                    stateUnitArrayList.get(ID).getCanvas().repaint();
                }

                //make string of emergency phone for display purpose
                String password = "";
                String[] phone = soSafeSystem.getEmergency();
                String phonePrint;
                if (phone[0].equals("")) {
                    phonePrint = phone[1];
                } else if (phone[1].equals("")) {
                    phonePrint = phone[0];
                } else {
                    phonePrint = "Calling " + phone[0] + " & " + phone[1] + "...";
                }

                //ask for PIN
                while (!soSafeSystem.validatePassword(password)) {
                    JPanel panel = new JPanel();
                    JLabel label = new JLabel(phonePrint + "Please type in correct password to dismiss the alart!");
                    JPasswordField pass = new JPasswordField(10);
                    panel.add(label);
                    panel.add(pass);
                    String[] options = new String[]{"OK", "Cancel"};
                    int option = JOptionPane.showOptionDialog(null, panel, "ALARM",
                            JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options, options[1]);
                    if (option == 0) // pressing OK button
                    {
                        password = new String(pass.getPassword());
                    }
                }
                //if PIN is right, remove the alarm
                stateUnitArrayList.get(ID).getCanvas().removeAll();
                stateUnitArrayList.get(ID).getCanvas().repaint();
            }

            //used by backend to notify GUI the state change of certain sensors
            @Override
            public void onSensorStateChange(int ID, String type, boolean state) {
                if (type.equals("Fire")) {
                    if (state == true) {
                        stateUnitArrayList.get(ID).getFireText().setText("Working");
                        stateUnitArrayList.get(ID).getFireColor().setBackground(Color.green);
                    } else {
                        stateUnitArrayList.get(ID).getFireText().setText("Activated");
                        stateUnitArrayList.get(ID).getFireColor().setBackground(Color.red);
                    }
                } else {
                    if (state == true) {
                        stateUnitArrayList.get(ID).getIntrusionText().setText("Working");
                        stateUnitArrayList.get(ID).getIntrusionColor().setBackground(Color.green);
                    } else {
                        stateUnitArrayList.get(ID).getIntrusionText().setText("Activated");
                        stateUnitArrayList.get(ID).getIntrusionColor().setBackground(Color.red);
                    }
                }

            }

            //pass the fake time to backend
            @Override
            public String getCurrentTimeFromPanel() {
                String[] temp = fakeTimer.getHourLable().getText().split(":");
                return temp[0] + temp[1];
            }
        });
    }

    //update the information on state panel to reflect the state of each sensor
    public void updateState() {
        installedSensorList = soSafeSystem.getSensorList();
        activatedSensorList = soSafeSystem.getActivatedSensorList();
        for (StateUnit s : stateUnitArrayList) {
            s.getFireText().setText("NOT INSTALL");
            s.getIntrusionText().setText("NOT INSTALL");
            s.getIntrusionColor().setBackground(new Color(242, 241, 240));
            s.getFireColor().setBackground(new Color(242, 241, 240));
        }
        for (Sensor s : installedSensorList) {
            int id = s.getID();
            String type = s.getType();
            if (type.equals("Fire")) {
                stateUnitArrayList.get(id).getFireText().setText("Installed");
            } else {
                stateUnitArrayList.get(id).getIntrusionText().setText("Installed");
            }
        }
        for (Sensor s : activatedSensorList) {
            int id = s.getID();
            String type = s.getType();
            if (type.equals("Fire")) {
                stateUnitArrayList.get(id).getFireText().setText("Activated");
                stateUnitArrayList.get(id).getFireColor().setBackground(Color.red);
            } else {
                stateUnitArrayList.get(id).getIntrusionText().setText("Activated");
                stateUnitArrayList.get(id).getIntrusionColor().setBackground(Color.red);
            }
        }

    }

    //link the system fake time to the panel
    public void setFakeTimer(FakeTimer fakeTimer) {
        this.fakeTimer = fakeTimer;
    }

    //initial the GUI
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        buildingLayout1 = new BuildingLayout();
        buildingLayout1.setLayout(new GridBagLayout());
        panel1.add(buildingLayout1, BorderLayout.CENTER);
        buildingLayout1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-16777216)));
        stateUnit1 = new StateUnit();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(stateUnit1.$$$getRootComponent$$$(), gbc);
        stateUnit5 = new StateUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(stateUnit5.$$$getRootComponent$$$(), gbc);
        stateUnit2 = new StateUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(stateUnit2.$$$getRootComponent$$$(), gbc);
        stateUnit6 = new StateUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(stateUnit6.$$$getRootComponent$$$(), gbc);
        stateUnit3 = new StateUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(stateUnit3.$$$getRootComponent$$$(), gbc);
        stateUnit4 = new StateUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(stateUnit4.$$$getRootComponent$$$(), gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel1.add(panel2, BorderLayout.WEST);
        testPanel = new TestUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel2.add(testPanel.$$$getRootComponent$$$(), gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel1.add(panel3, BorderLayout.SOUTH);
        confirmButton = new JButton();
        confirmButton.setText("Confirm");
        panel3.add(confirmButton, BorderLayout.EAST);
    }

    //return the root component
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
