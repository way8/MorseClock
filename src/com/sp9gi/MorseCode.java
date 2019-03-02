package com.sp9gi;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Class for storage Morse Code
 */
public class MorseCode {
    public MorseCode() {
        executeCode();
    }

    String letter;
    char[] c_arr;

    // public static void morseData () {
    private static HashMap<String, String> codes = new HashMap<String, String>();

    // create a static block
    static {
        codes.put("a", ".-");
        codes.put("b", "-...");
        codes.put("c", "-.-.");
        codes.put("g", "--.");
        codes.put("i", "..");
        codes.put("s", "...");
        codes.put("p", ".--.");
        codes.put("9", "----.");

    }



    public void executeCode() {
        letter = (codes.get("s") + "." + codes.get("p") + "." + codes.get("9") + "." +
                codes.get("g") + "." + codes.get("i"));

        c_arr = letter.toCharArray();

//        Scanner scan = new Scanner(System.in);
//        System.out.println("Podaj znak");


//        System.out.println("Po morsowskiemu to " + codes.get(letter.toLowerCase()));
    }

}
