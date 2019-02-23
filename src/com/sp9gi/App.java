package com.sp9gi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Clock;
import java.util.GregorianCalendar;
import java.util.Calendar;

/**
 * Main Class for run the clock
 *
 * @author Sylwester
 */
public class App {
    private JButton button_msg;
    private JPanel panelMain;
    public JLabel clock;
    private JLabel date;

    public App() {
        button_msg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hello World");
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

                        if (hour == 19 && second == 0) {  //Alarm set to...

                            try {
                                diode.blink();

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
//            diode.blink();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }



    }

}
