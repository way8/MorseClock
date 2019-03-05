package com.sp9gi;

import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.wiringpi.Gpio;

/**
 * Generate PWM signal for speaker
 */
public class Pwm {
    //create object for take from them morse code
    MorseCode signal = new MorseCode();

    // create GPIO controller instance,
    GpioController invGpio = GpioFactory.getInstance();

    // All Raspberry Pi models support a hardware PWM invPin on GPIO_01.
    Pin invPin = CommandArgumentParser.getPin(
            RaspiPin.class,    // invPin provider class to obtain invPin instance from
            RaspiPin.GPIO_01);  // default invPin if no invPin argument found

    GpioPinPwmOutput invPwm = invGpio.provisionPwmOutputPin(invPin);

    /**
     * Method where we can set frequency of PWM and this method itself "play"  morse code - uses new thread
     * @throws InterruptedException
     */
    public void buzz() throws InterruptedException {

                // you can optionally use these wiringPi methods to further customize the PWM generator
                // see: http://wiringpi.com/reference/raspberry-pi-specifics/
                // pwmFrequency in Hz = 19.2e6 Hz / pwmClock / pwmRange
                com.pi4j.wiringpi.Gpio.pwmSetMode(Gpio.PWM_MODE_MS);
                com.pi4j.wiringpi.Gpio.pwmSetRange(1024); //This sets the resolution, the number of steps between 0 and 100% duty cycle. The default value is 1024.
                com.pi4j.wiringpi.Gpio.pwmSetClock(32);  //divisor

        //another threat for "play" the morse code
        Thread th3 = new Thread() {
            public void run() {

                for (int j = 0; j < 2; j++) {
                    for (int i = 0; i < signal.co_arr.length; i++) {
                        switch (signal.co_arr[i]) {
                            case '.':
                                invPwm.setPwm(500);
                                try {
                                    Thread.sleep(60);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }break;
                            case '-':
                                invPwm.setPwm(500);
                                try {
                                    Thread.sleep(180);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }break;
                            case '_':   //pause between letters
                                invPwm.setPwm(0);
                                try {
                                    Thread.sleep(180);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }break;
                            case '|':  //pause between words
                                invPwm.setPwm(0);
                                try {
                                    Thread.sleep(420);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }break;
                        }

/*                        if (signal.co_arr[i] == '.') {
                            invPwm.setPwm(500);
                            try {
                                Thread.sleep(60);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else if (signal.co_arr[i] == '-') {
                            invPwm.setPwm(500);
                            try {
                                Thread.sleep(180);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else if (signal.co_arr[i] == '_') { //pause between letters
                            invPwm.setPwm(0);
                            try {
                                Thread.sleep(180);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else if (signal.co_arr[i] == '|') { //pause between words
                            invPwm.setPwm(0);
                            try {
                                Thread.sleep(420);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        invPwm.setPwm(0);
                        try {
                            Thread.sleep(60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();

                        }*/
                    }
                    invPwm.setPwm(0);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                }
                // stop all GPIO activity/threads by shutting down the GPIO controller
                // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
                invGpio.shutdown();
            }
        };
        th3.start();
    }
}
