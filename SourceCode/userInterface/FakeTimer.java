package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xinrui on 6/8/17.
 */

//use this class to simulate the time to test the system, it is 3600x faster than physical time
public class FakeTimer extends JPanel {

    private JPanel fakeTimerPanel;
    private JLabel hourLable;
    private JLabel dateLable;
    private Timer timer;
    private Calendar calendar;
    private Date fakeCurrentTime;

    //return fake hour
    public JLabel getHourLable() {
        return hourLable;
    }

    //return fake date
    public JLabel getDateLable() {
        return dateLable;
    }

    //return date + hour
    public Date getFakeCurrentTime() {
        return fakeCurrentTime;
    }

    /**initial the class and make it run in the speed as fast as 3600x to the physical time
     * and show the time on the state panel
     */
    public FakeTimer() {
        $$$setupUI$$$();
        DateFormat dateFormatDate = new SimpleDateFormat("yyyy/MM/dd");
        calendar = Calendar.getInstance();
        fakeCurrentTime = calendar.getTime();
        DateFormat dateFormatHour = new SimpleDateFormat("HH:mm");
        dateLable.setText(dateFormatDate.format(fakeCurrentTime));
        hourLable.setText(dateFormatHour.format(fakeCurrentTime));
        timer = new Timer(800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.HOUR, 1);
                Date fakeCurrentTime = calendar.getTime();
                dateLable.setText(dateFormatDate.format(fakeCurrentTime));
                hourLable.setText(dateFormatHour.format(fakeCurrentTime));
            }
        });
        timer.setInitialDelay(800);
        timer.setRepeats(true);
        timer.start();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        fakeTimerPanel = new JPanel();
        fakeTimerPanel.setLayout(new GridBagLayout());
        fakeTimerPanel.setMinimumSize(new Dimension(100, 100));
        hourLable = new JLabel();
        hourLable.setText("00:00");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        fakeTimerPanel.add(hourLable, gbc);
        dateLable = new JLabel();
        dateLable.setText("1");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        fakeTimerPanel.add(dateLable, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return fakeTimerPanel;
    }
}
