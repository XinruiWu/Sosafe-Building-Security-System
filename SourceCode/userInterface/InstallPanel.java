package userInterface;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import backend.*;

/**
 * Created by xinrui on 6/4/17.
 */

//use this class to install sensors by user's wish
public class InstallPanel extends JPanel {
    private JTabbedPane maintabbedPane;
    private JPanel mainpanel;
    private JPanel installSensorPanel;
    private JPanel passwordPanel;
    private JPanel profilePanel;
    private JButton IstConfirmButton;
    private JPanel confirmPanel;
    private JPanel istmapPanel;
    private JLabel instructionLabel;
    private BuildingLayout buildingLayout1;
    private InstallUnit installUnit1;
    private InstallUnit installUnit2;
    private InstallUnit installUnit5;
    private InstallUnit installUnit6;
    private InstallUnit installUnit3;
    private InstallUnit installUnit4;
    private JPanel pwinputPanel;
    private JPanel pwConfirmPanel;
    private JButton pwConfirmButton;
    private PwInputUnit pwInputUnit1;
    private JPanel PfConfirmPanel;
    private JButton confirmButton;
    private ProfileUnit profileUnit;
    private SoSafeSystem soSafeSystem;
    private ArrayList<InstallUnit> installUnitArrayList;
    private StatePanel statePanel;
    private FakeTimer fakeTimer;

    //initial the class
    public InstallPanel() {

        $$$setupUI$$$();

        //add install panel unit into arraylist for management purpose
        installUnitArrayList = new ArrayList<>();
        installUnitArrayList.add(installUnit1);
        installUnitArrayList.add(installUnit2);
        installUnitArrayList.add(installUnit3);
        installUnitArrayList.add(installUnit4);
        installUnitArrayList.add(installUnit5);
        installUnitArrayList.add(installUnit6);

        //send installation information to backend and save it as a txt file
        IstConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                installSensors();
                JOptionPane.showMessageDialog(installSensorPanel, "Settings Saved!!!");

            }
        });

        //send password information to backend and save it as a text file
        pwConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPassword();
            }
        });

        //send user information to backend and save it as a text file
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUserInfo();
                JOptionPane.showMessageDialog(installSensorPanel, "Profile Saved!!!");
            }
        });
    }

    //read data from txt file and load it to the system when the application is running
    public void installPanelDataRecover() {
        try {
            //load installation data
            String path = SoSafeSystem.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path += "database/installed.txt";
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] parsedline = line.split(" ");
                int index = Integer.parseInt(parsedline[1]);
                if (parsedline[0].equals("Fire")) {
                    installUnitArrayList.get(index).getFireSensorCheckBox().setSelected(true);
                } else {
                    installUnitArrayList.get(index).getIntrusionSensorCheckBox().setSelected(true);
                }
            }
            bufferedReader.close();
            fileReader.close();
            installSensors();

            //load password data
            path = SoSafeSystem.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path += "database/password.txt";
            file = new File(path);
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            line = bufferedReader.readLine();
            bufferedReader.close();
            fileReader.close();
            soSafeSystem.resetPassword("", line, line);

            //load user information data
            path = SoSafeSystem.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path += "database/userinfo.txt";
            file = new File(path);
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            profileUnit.getSvcID().setText(bufferedReader.readLine());
            profileUnit.getNameTextField().setText(bufferedReader.readLine());
            profileUnit.getAddress().setText(bufferedReader.readLine());
            profileUnit.getNumber1().setText(bufferedReader.readLine());
            profileUnit.getNumber2().setText(bufferedReader.readLine());
            profileUnit.getPhone().setText(bufferedReader.readLine());
            profileUnit.getEmail().setText(bufferedReader.readLine());
            profileUnit.getEffectiveDate().setText(bufferedReader.readLine());
            bufferedReader.close();
            fileReader.close();
            setUserInfo();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //install sensors in backend and save the log to txt file
    private void installSensors() {
        try {
            String path = SoSafeSystem.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path += "database/installed.txt";
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");

            for (int i = 0; i < installUnitArrayList.size(); i++) {
                if (installUnitArrayList.get(i).getFireSensorCheckBox().isSelected()) {
                    bufferedWriter.append("Fire" + " " + i + "\n");
                    if (!checkInstallSensor(i, "Fire")) {
                        soSafeSystem.addSensor(i, "Fire", fakeTimer.getFakeCurrentTime());
                    }
                } else {
                    if (checkInstallSensor(i, "Fire")) {
                        soSafeSystem.removeSensor(i, "Fire");
                    }
                }
                if (installUnitArrayList.get(i).getIntrusionSensorCheckBox().isSelected()) {
                    bufferedWriter.append("Breakin" + " " + i + "\n");
                    if (!checkInstallSensor(i, "Breakin")) {
                        soSafeSystem.addSensor(i, "Breakin", fakeTimer.getFakeCurrentTime());
                    }
                } else {
                    if (checkInstallSensor(i, "Breakin")) {
                        soSafeSystem.removeSensor(i, "Breakin");
                    }
                }
            }
            bufferedWriter.close();
            fileWriter.close();
            statePanel.updateState();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //set password in backend and save it to txt file
    private void setPassword() {
        try {
            boolean flag = soSafeSystem.resetPassword(new String(pwInputUnit1.getOldPassword().getPassword()), new String(pwInputUnit1.getNewPassword1().getPassword()), new String(pwInputUnit1.getNewPassword2().getPassword()));
            if (!flag) {
                JOptionPane.showMessageDialog(pwInputUnit1, "Wrong old password or new passwords are not identical!!!");
            } else {
                JOptionPane.showMessageDialog(pwInputUnit1, "Password Set!!!");
                String path = SoSafeSystem.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                path += "database/password.txt";
                File file = new File(path);
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(new String(pwInputUnit1.getNewPassword1().getPassword()));
                bufferedWriter.close();
                fileWriter.close();
                pwInputUnit1.getOldPassword().setText("");
                pwInputUnit1.getNewPassword1().setText("");
                pwInputUnit1.getNewPassword2().setText("");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //set user information in backend and save it to txt file
    private void setUserInfo() {
        try {
            String path = SoSafeSystem.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path += "database/userinfo.txt";
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            DateFormat dateFormatDate = new SimpleDateFormat("yyyy/MM/dd");

            profileUnit.getEffectiveDate().setText(dateFormatDate.format(fakeTimer.getFakeCurrentTime()));
            profileUnit.getSvcID().setText(profileUnit.getEmail().getText());
            soSafeSystem.setUserInfo(profileUnit.getNameTextField().getText(), profileUnit.getAddress().getText(), profileUnit.getEmail().getText(), profileUnit.getPhone().getText(), profileUnit.getNumber1().getText(), profileUnit.getNumber2().getText());
            bufferedWriter.write("");
            bufferedWriter.append(profileUnit.getEmail().getText() + "\n");
            bufferedWriter.append(profileUnit.getNameTextField().getText() + "\n");
            bufferedWriter.append(profileUnit.getAddress().getText() + "\n");
            bufferedWriter.append(profileUnit.getNumber1().getText() + "\n");
            bufferedWriter.append(profileUnit.getNumber2().getText() + "\n");
            bufferedWriter.append(profileUnit.getPhone().getText() + "\n");
            bufferedWriter.append(profileUnit.getEmail().getText() + "\n");
            bufferedWriter.append(dateFormatDate.format(fakeTimer.getFakeCurrentTime()) + "\n");
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //check a certain sensor to see if it is installed
    private boolean checkInstallSensor(int ID, String type) {
        for (Sensor sensor : soSafeSystem.getSensorList()) {
            if (sensor.getID() == ID && sensor.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    //link the panel to faketime to get the simulating time for schedule purpose
    public void setFakeTimer(FakeTimer fakeTimer) {
        this.fakeTimer = fakeTimer;
    }

    //link the panel to state panel to show the state of the sensor
    public void setStatePanel(StatePanel statePanel) {
        this.statePanel = statePanel;
    }

    //link the panel to backend to save the data
    public void setSoSafeSystem(SoSafeSystem soSafeSystem) {
        this.soSafeSystem = soSafeSystem;
    }

    //initial GUI
    private void $$$setupUI$$$() {
        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout(0, 0));
        mainpanel.setEnabled(true);
        mainpanel.setPreferredSize(new Dimension(0, 0));
        maintabbedPane = new JTabbedPane();
        maintabbedPane.setOpaque(false);
        maintabbedPane.setPreferredSize(new Dimension(0, 0));
        maintabbedPane.setTabPlacement(2);
        mainpanel.add(maintabbedPane, BorderLayout.CENTER);
        installSensorPanel = new JPanel();
        installSensorPanel.setLayout(new BorderLayout(0, 0));
        maintabbedPane.addTab("InstallSensor", installSensorPanel);
        confirmPanel = new JPanel();
        confirmPanel.setLayout(new BorderLayout(0, 0));
        installSensorPanel.add(confirmPanel, BorderLayout.SOUTH);
        IstConfirmButton = new JButton();
        IstConfirmButton.setText("Confirm");
        confirmPanel.add(IstConfirmButton, BorderLayout.EAST);
        istmapPanel = new JPanel();
        istmapPanel.setLayout(new BorderLayout(0, 0));
        installSensorPanel.add(istmapPanel, BorderLayout.CENTER);
        istmapPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "MAP", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font(istmapPanel.getFont().getName(), istmapPanel.getFont().getStyle(), istmapPanel.getFont().getSize())));
        instructionLabel = new JLabel();
        instructionLabel.setText("Checked sensors will be installed in corresponding areas after comfirming.");
        istmapPanel.add(instructionLabel, BorderLayout.NORTH);
        buildingLayout1 = new BuildingLayout();
        buildingLayout1.setLayout(new GridBagLayout());
        istmapPanel.add(buildingLayout1, BorderLayout.CENTER);
        installUnit1 = new InstallUnit();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(installUnit1.$$$getRootComponent$$$(), gbc);
        installUnit2 = new InstallUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(installUnit2.$$$getRootComponent$$$(), gbc);
        installUnit5 = new InstallUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(installUnit5.$$$getRootComponent$$$(), gbc);
        installUnit6 = new InstallUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(installUnit6.$$$getRootComponent$$$(), gbc);
        installUnit3 = new InstallUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(installUnit3.$$$getRootComponent$$$(), gbc);
        installUnit4 = new InstallUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        buildingLayout1.add(installUnit4.$$$getRootComponent$$$(), gbc);
        final JLabel label1 = new JLabel();
        label1.setText("MUST confirm the sechdules in Schedule Panel before the installed senser can be working validly ");
        istmapPanel.add(label1, BorderLayout.SOUTH);
        passwordPanel = new JPanel();
        passwordPanel.setLayout(new BorderLayout(0, 0));
        maintabbedPane.addTab("Password", passwordPanel);
        pwinputPanel = new JPanel();
        pwinputPanel.setLayout(new GridBagLayout());
        passwordPanel.add(pwinputPanel, BorderLayout.CENTER);
        pwInputUnit1 = new PwInputUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        pwinputPanel.add(pwInputUnit1.$$$getRootComponent$$$(), gbc);
        pwConfirmPanel = new JPanel();
        pwConfirmPanel.setLayout(new BorderLayout(0, 0));
        passwordPanel.add(pwConfirmPanel, BorderLayout.SOUTH);
        pwConfirmButton = new JButton();
        pwConfirmButton.setText("Comfirm");
        pwConfirmPanel.add(pwConfirmButton, BorderLayout.EAST);
        profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout(0, 0));
        maintabbedPane.addTab("Profile", profilePanel);
        PfConfirmPanel = new JPanel();
        PfConfirmPanel.setLayout(new BorderLayout(0, 0));
        profilePanel.add(PfConfirmPanel, BorderLayout.SOUTH);
        confirmButton = new JButton();
        confirmButton.setText("Comfirm");
        PfConfirmPanel.add(confirmButton, BorderLayout.EAST);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        profilePanel.add(panel1, BorderLayout.CENTER);
        profileUnit = new ProfileUnit();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel1.add(profileUnit.$$$getRootComponent$$$(), gbc);
    }

    //get the root component
    public JComponent $$$getRootComponent$$$() {
        return mainpanel;
    }
}










