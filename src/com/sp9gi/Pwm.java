package com.sp9gi;

import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * Generate PWM signal for speaker
 */
public class Pwm {
    int off = 0;

    //create object for take from them morse code
    MorseCode signal = new MorseCode();

    // create GPIO controller instance,
    GpioController gpio = GpioFactory.getInstance();

    // provision gpio pin #24 as an input pin with its internal pull down resistor enabled
    final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_24, PinPullResistance.PULL_DOWN);
    // provision gpio pin #29 as an output pin and turn on
    final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, "MyLED", PinState.HIGH);
    // set shutdown state for this input pin


    // All Raspberry Pi models support a hardware PWM invPin on GPIO_01.
    Pin invPin = CommandArgumentParser.getPin(
            RaspiPin.class,    // invPin provider class to obtain invPin instance from
            RaspiPin.GPIO_01);  // default invPin if no invPin argument found

    GpioPinPwmOutput invPwm = gpio.provisionPwmOutputPin(invPin);

    /**
     * Method where we can set frequency of PWM and this method itself "play"  morse code - uses new thread
     *
     * @throws InterruptedException
     */
    public void buzz() throws InterruptedException {
        off = 0;
        pin.high();
        myButton.setShutdownOptions(true);


        // you can optionally use these wiringPi methods to further customize the PWM generator
        // see: http://wiringpi.com/reference/raspberry-pi-specifics/
        // pwmFrequency in Hz = 19.2e6 Hz / pwmClock / pwmRange
        com.pi4j.wiringpi.Gpio.pwmSetMode(Gpio.PWM_MODE_MS);
        com.pi4j.wiringpi.Gpio.pwmSetRange(1024); //This sets the resolution, the number of steps between 0 and 100% duty cycle. The default value is 1024.
        com.pi4j.wiringpi.Gpio.pwmSetClock(32);  //divisor

        //another threat for "play" the morse code
        Thread th3 = new Thread() {
            public void run() {

                // create and register gpio pin listener
                myButton.addListener(new GpioPinListenerDigital() {
                    @Override
                    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                        pin.low(); //chanage the state of pin, in this case turn off the buzzer
                        off = 1;
                        invPwm.setPwm(0);
                    }
                });
                try {
                    Thread.sleep(500); //intervals for checking state of the button
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                for (int j = 0; j < 2; j++) {
                    for (int i = 0; i < signal.co_arr.length; i++) {
                        switch (signal.co_arr[i]) {
                            case '.':
                                if (off == 0) {
                                    invPwm.setPwm(500);
                                }
                                try {
                                    Thread.sleep(60);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                invPwm.setPwm(0);
                                break;
                            case '-':
                                if (off == 0) {
                                    invPwm.setPwm(500);
                                }
                                try {
                                    Thread.sleep(180);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                invPwm.setPwm(0);
                                break;

                            case '_':
                                //pause between letters
                                invPwm.setPwm(0);
                                try {
                                    Thread.sleep(180);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case '|':
                                //pause between words
                                invPwm.setPwm(0);
                                try {
                                    Thread.sleep(420);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                        // Pause between signs
                        invPwm.setPwm(0);
                        try {
                            Thread.sleep(60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();

                        }
                    }
                }
                // stop all GPIO activity/threads by shutting down the GPIO controller
                // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
                //gpio.shutdown();
            }
        };
        th3.start();
    }
}
