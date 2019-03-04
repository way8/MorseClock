package com.sp9gi;

import com.pi4j.io.gpio.Pin;

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
    String alarmHour = "0"; // set those variables to 0 because of problem with parsing - to RESOLVE!
    String alarmMin= "0";   // because now teh alarm will be always set to 00:00
    private JButton alarmSetButton;
    private JPanel panelMain;
    public JLabel clock;
    private JLabel date;
    private JTextField minField;
    private JTextField hourField;
    private JLabel setLabel;
    private JLabel labAlarm;
    private JTextField codeField;
    private JLabel codeLabel;
    private JButton codeSetButton;

    /**
     * Method to operate App.form
     */
    public App() {
        MorseCode signal = new MorseCode();
        alarmSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alarmMin = minField.getText();
                alarmHour = hourField.getText();
                labAlarm.setText(alarmHour + " : " + alarmMin);
            }
        });

        codeSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signal.input = codeField.getText();
                Pwm pwm = new Pwm();
                try {
                    pwm.buzz();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });



        clock();


    }

    /**
     * Method to generate time - uses new thread
     */
    public void clock() {

        Alarm diode = new Alarm();

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
                        sleep(1000);


                        int alarmHourInt = Integer.valueOf(alarmHour); //Taking the time from form
                        int alarmMinInt = Integer.valueOf(alarmMin);

                        if (hour == alarmHourInt && min == alarmMinInt && second == 0) {  //Alarm set to...

                            try {
                                diode.buzzer();

                            } catch (InterruptedException e) {
                                e.printStackTrace();
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





//        Alarm diode = new Alarm();
//        try {
//            diode.buzzerOn();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }



    }

}
