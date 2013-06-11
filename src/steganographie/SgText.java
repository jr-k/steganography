/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package steganographie;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jessym
 */
public class SgText {

    private String MESSAGE_STRING = "abcdefghijklmnopqrstuvwxyz";
    private String MESSAGE_BINARY;
    private int finalBits = 2;
    
    ArrayList<String> dataToStore = new ArrayList();

    public SgText(String msg, int fb) {
        try {
            this.MESSAGE_STRING = msg;
            this.finalBits = fb;

            byte[] dataByte;
            dataByte = MESSAGE_STRING.getBytes("UTF8");


            MESSAGE_BINARY = SgUtils.toBinary(dataByte);


            for (int i = 0; i < MESSAGE_BINARY.length(); i += finalBits) {

                String o = MESSAGE_BINARY.substring(i, i + finalBits);
                dataToStore.add(o);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SgText.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public String getMessage(){
        return this.MESSAGE_STRING;
    }
    
    public ArrayList<String> getSchema(){
        return this.dataToStore;
    }
    
    public String getFirstByte(){
        return SgUtils.fill8zero(MESSAGE_STRING.length());
    }
    
    public String getFirstByteAdvanced(int level){
        String fa = SgUtils.fill8zeroAdvanced(MESSAGE_STRING.length());
        
        if (level == 1){
            return fa.substring(0,8);
        }
        else if (level == 2){
            return fa.substring(8,16);
        }
        else if (level == 3){
            return fa.substring(16,24);
        }
        else
           return "";
        
    }
    
    public int getMaxByte(){
        return (MESSAGE_STRING.length() * 8) / finalBits;
    }
    
    public void showMaxByte(){
        System.out.println("Pour stocker le message vous aurez besoin de "+getMaxByte()+" octets pour un découpage de "+finalBits+" bits.\n");
    }
    
    public void showFirstByte(){
        System.out.println("La longueur du message est de : "+MESSAGE_STRING.length()+" caractères.\n");
    }
    
    public void showSchema(){
        System.out.println("Le schéma de découpage est le suivant : "+this.dataToStore);
        System.out.println("");
    }
    
    public String get(int i){
        return this.dataToStore.get(i);
    }
    

    
}
