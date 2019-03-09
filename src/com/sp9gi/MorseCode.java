package com.sp9gi;

import java.util.HashMap;

/**
 * Class for storage and generate Morse Code
 */
public class MorseCode {

    char[] in_arr;
    static char[] co_arr; //at least two object will have access to this variable, so it's static
    String code = "";
    String a = "";
    String input = "";

    private static HashMap<String, String> codes = new HashMap<String, String>();
    // create a static block
    static {
        codes.put("a", ".-");
        codes.put("b", "-...");
        codes.put("c", "-.-.");
        codes.put("d", "-..");
        codes.put("e", ".");
        codes.put("f", "..-.");
        codes.put("g", "--.");
        codes.put("h", "....");
        codes.put("i", "..");
        codes.put("j", ".---");
        codes.put("k", "-.-");
        codes.put("l", ".-..");
        codes.put("m", "--");
        codes.put("n", "-.");
        codes.put("o", "---");
        codes.put("p", ".--.");
        codes.put("r", ".-.");
        codes.put("q", "--.-");
        codes.put("s", "...");
        codes.put("t", "-");
        codes.put("u", "..-");
        codes.put("v", "...-");
        codes.put("w", ".--");
        codes.put("x", "-..-");
        codes.put("y", "-.--");
        codes.put("z", "--..");
        codes.put("0", "-----");
        codes.put("1", ".----");
        codes.put("2", "..---");
        codes.put("3", "...--");
        codes.put("4", "....-");
        codes.put("5", ".....");
        codes.put("6", "-....");
        codes.put("7", "--...");
        codes.put("8", "---..");
        codes.put("9", "----.");
        codes.put(".", ".-.-");
        codes.put(",", "--..--");
        codes.put("?", "..--..");
        codes.put(" ", "|");

    }

    /**
     * Method for generate array of morse code
     */
    public void executeCode() {
        code = "";//clear value of of the variable
        in_arr = input.toCharArray();

        // Loop for generate morse code with suitable pauses
        for (char i : in_arr) {
            if (i == ' ') {
                a = "";
            } else a = "_";

            String j = Character.toString(i);
            code = code + codes.get(j) + a;
        }
        co_arr = code.toCharArray(); //array of morse code

        System.out.println("Po morsowskiemu to " + code);
    }

}
