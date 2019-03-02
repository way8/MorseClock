package com.sp9gi;

import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.wiringpi.Gpio;

/**
 * Generate PWM signal for speaker
 */
public class Pwm {
    public void buzz() throws InterruptedException {

// Note 60 seconds = 1 minute and 50 elements = 1 morse word
//                P = di da da di = 1 1 3 1 3 1 1 (3) = 14 elements
//                A = di da = 1 1 3 (3) = 8 elements
//                R = di da di = 1 1 3 1 1 (3) = 10 elements
//                I = di di = 1 1 1 (3) = 6 elements
//                S = di di di = 1 1 1 1 1 [7] = 12 elements
//                Total = 50 elements
//                () = intercharacter
//                [] = interword


        MorseCode signal = new MorseCode();


        // create GPIO controller instance,
        GpioController invGpio = GpioFactory.getInstance();

        // All Raspberry Pi models support a hardware PWM invPin on GPIO_01.
        Pin invPin = CommandArgumentParser.getPin(
                RaspiPin.class,    // invPin provider class to obtain invPin instance from
                RaspiPin.GPIO_01);  // default invPin if no invPin argument found

        GpioPinPwmOutput invPwm = invGpio.provisionPwmOutputPin(invPin);

        // you can optionally use these wiringPi methods to further customize the PWM generator
        // see: http://wiringpi.com/reference/raspberry-pi-specifics/
        // pwmFrequency in Hz = 19.2e6 Hz / pwmClock / pwmRange
        com.pi4j.wiringpi.Gpio.pwmSetMode(Gpio.PWM_MODE_MS);
        com.pi4j.wiringpi.Gpio.pwmSetRange(1024); //This sets the resolution, the number of steps between 0 and 100% duty cycle. The default value is 1024.
        com.pi4j.wiringpi.Gpio.pwmSetClock(32);  //divisor


        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < signal.c_arr.length; i++) {
                if (signal.c_arr[i] == '.') {
                    invPwm.setPwm(500);
                    Thread.sleep(60);
                } else if (signal.c_arr[i] == '-') {
                    invPwm.setPwm(500);
                    Thread.sleep(180);
                }
                invPwm.setPwm(0);
                Thread.sleep(60);
            }
            invPwm.setPwm(0);
            Thread.sleep(3000);
        }

//        invPwm.setPwm(500);
//
//        Thread.sleep(10000);
//
//        invPwm.setPwm(500);
//        Thread.sleep(8000);
//        invPwm.setPwm(0);
        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)

        invGpio.shutdown();
    }

}
