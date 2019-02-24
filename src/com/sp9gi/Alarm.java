package com.sp9gi;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Alarm {

    /**
     * Method for listen the state of the pin and activated/deactivated buzzer
     * @throws InterruptedException
     */
    public void buzzer() throws InterruptedException {

        Thread th2 = new Thread() {
            public void run() {

                // create gpio controller
                final GpioController gpio = GpioFactory.getInstance();

                // provision gpio pin #24 as an input pin with its internal pull down resistor enabled
                final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_24, PinPullResistance.PULL_DOWN);
                // provision gpio pin #29 as an output pin and turn on
                final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, "MyLED", PinState.HIGH);

                // set shutdown state for this input pin
                myButton.setShutdownOptions(true);

                // create and register gpio pin listener
                myButton.addListener(new GpioPinListenerDigital() {
                    @Override
                    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                        pin.low(); //chanage the state of pin, in this case turn off the buzzer
                    }

                });
                try {
                    sleep(500); //intervals of checking state of the button
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        th2.start();
    }
}