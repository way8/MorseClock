package com.sp9gi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.Calendar;

/**
 * Main Class for run the clock
 *
 * @author Sylwester
 */
public class App {
    String alarmHour = "0"; // todo set those variables to 0 because of problem with parsing - to RESOLVE!
    String alarmMin= "0";   // because now teh alarm will be always set to 00:00
    private JButton alarmSetButton;
    private JPanel panelMain;
    public JLabel clock;
    private JLabel date;
    private JLabel labAlarm;
    private JTextField codeField;
    private JButton codeSetButton;
    private JButton menuButton;
    private JButton codeButton;
    private JLabel info;

    /**
     * Method to operate App.form
     */
    public App() {
        MorseCode signal = new MorseCode();
       // Alarm pwm = new Alarm();

        codeSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signal.input = codeField.getText();
                signal.executeCode();

//                try {
//                    pwm.buzz();
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
            }
        });
        clock();
        //popup window for set the clock alarm
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                JTextField hourField = new JTextField(2);
                JTextField minField = new JTextField(2);

                panel.add(new JLabel("Set alarm time:"));
                panel.add(hourField);
                panel.add(minField);
                JOptionPane.showConfirmDialog(null, panel, "Settings", JOptionPane.OK_CANCEL_OPTION);
                alarmMin = minField.getText();
                alarmHour = hourField.getText();
                labAlarm.setText(alarmHour + " : " + alarmMin);
            }
        });

        //popup window for set the morse code for alarm
        codeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                JTextField codeField2 = new JTextField(5);

                panel.add(new JLabel("Set alarm theme:"));
                panel.add(codeField2);
                JOptionPane.showConfirmDialog(null, panel, "Settings", JOptionPane.OK_CANCEL_OPTION);
                signal.input = codeField2.getText();
                signal.executeCode();
            }
        });
    }

    /**
     * Method to generate time - uses new thread
     */
    public void clock() {

        Alarm alarm = new Alarm();

        Thread th = new Thread() {
            public void run() {
                try {
                    for (; ; ) {
                        Calendar cl = new GregorianCalendar(); //Calendar is an abstract class
                        int day = cl.get(Calendar.DAY_OF_MONTH);
                        int month = cl.get(Calendar.MONTH) + 1;
                        int year = cl.get(Calendar.YEAR);

                        int second = cl.get(Calendar.SECOND);
                        int min = cl.get(Calendar.MINUTE);
                        int hour = cl.get(Calendar.HOUR_OF_DAY);

                        clock.setText("" + hour + ":" + min + ":" + second); // Display clock in app
                        date.setText(day + "/" + month + "/" + year);
                        sleep(500);


                        int alarmHourInt = Integer.valueOf(alarmHour); //Taking the time from form
                        int alarmMinInt = Integer.valueOf(alarmMin);

                        if (hour == alarmHourInt && min == alarmMinInt && second == 0) {  //Alarm set to...

                            try {
                                alarm.buzz();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }

                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        th.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new  App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

}
