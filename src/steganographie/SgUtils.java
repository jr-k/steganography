/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package steganographie;

/**
 *
 * @author Jessym
 */
public class SgUtils {
    
    
    static String toBinary(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for (int i = 0; i < Byte.SIZE * bytes.length; i++) {
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        }
        return sb.toString();
    }
    
    
    public static int getBlue(int RGB) {
        return RGB & 0xFF;
    }

    public static int getGreen(int RGB) {
        return (RGB >> 8) & 0xFF;
    }

    public static int getRed(int RGB) {
        return (RGB >> 16) & 0xFF;
    }

    public static int makeRGB(int red, int green, int blue) {
        return ((blue & 0xFF) + ((green & 0xFF) << 8) + ((red & 0xFF) << 16));
    }

    public static String fill4zero(String miOctect){
        return miOctect + "0000";
    }

    public static String fill8zero(int nb) {

        String number = String.valueOf(Integer.toBinaryString(nb));
        while (number.length() < 8) {
            number = "0" + number;
        }
        return number;

    }
    
    
     public static String fill16zero(int nb) {

        String number = String.valueOf(Integer.toBinaryString(nb));
        while (number.length() < 16) {
            number = "0" + number;
        }
        return number;

    }
    
    
    public static String fill8zeroAdvanced(int nb) {

        String number = String.valueOf(Integer.toBinaryString(nb));
        while (number.length() < 24) {
            number = "0" + number;
        }
        return number;

    }

    public static String fill8zero(String old, String number, int l) {

        int limit = l;

        String out = "";

        for (int i = 0; i < old.length() - limit; i++) {
            out += old.charAt(i);
        }

        for (int i = 0; i < number.length(); i++) {
            out += number.charAt(i);
        }

        return out;

    }

    public static int unfill8zero(String oc) {

        int number = Integer.valueOf(oc, 2);


        return number;

    }
    
    public static int unfill16zero(String oc) {

        int number = Integer.valueOf(oc, 2);


        return number;

    }
    
    public static int unfill8zeroAdvanced(String oc) {

        int number = Integer.valueOf(oc, 2);

        return number;

    }
    
    public static String fusionOctect(String octect1, String octect2){
        
        return octect1.substring(0,4) + octect2.substring(4, 8);
        
    }
    
    
}
