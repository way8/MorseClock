package com.sp9gi;

import com.pi4j.io.gpio.*;

public class Alarm {


    public void blink() throws InterruptedException {
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #01 as an output pin and turn on
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.LOW);

        // set shutdown state for this pin
        //    pin.setShutdownOptions(true, PinState.LOW);


    }
}
