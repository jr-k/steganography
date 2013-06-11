/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package steganographie;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;

/**
 *
 * @author Jessym
 */
public class SgImage {

    public BufferedImage img = null;
    public BufferedImage imgOut = null;
    String ImageString;
    ArrayList<String> dataToStore = new ArrayList<>();
    private int finalBits = 2;

    public SgImage(String url, int fi) {
        this.finalBits = fi;
        try {
            img = ImageIO.read(new File(url));
            imgOut = ImageIO.read(new File(url));

            // remplir le tableau de Byte avec les RGB
            for (int w = 0; w < img.getHeight(); w++) {
                for (int h = 0; h < img.getWidth(); h++) {

                    dataToStore.add(SgUtils.fill8zero(SgUtils.getRed(this.img.getRGB(h, w))));
                    dataToStore.add(SgUtils.fill8zero(SgUtils.getGreen(this.img.getRGB(h, w))));
                    dataToStore.add(SgUtils.fill8zero(SgUtils.getBlue(this.img.getRGB(h, w))));
                }
            }

            //System.out.println(url);
            //System.out.println(dataToStore);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static BufferedImage newCopy(BufferedImage tmpBuf) {

        BufferedImage tmp = new BufferedImage(tmpBuf.getWidth(), tmpBuf.getHeight(), tmpBuf.getType());

        for (int col = 0; col < tmpBuf.getWidth(); col++) {

            for (int ligne = 0; ligne < tmpBuf.getHeight(); ligne++) {

                tmp.setRGB(col, ligne, tmpBuf.getRGB(col, ligne));

            }

        }

        return tmp;


    }

    public SgImage(BufferedImage ib, int fi) {

        this.finalBits = fi;
        this.img = ib;
        this.imgOut = newCopy(ib);

        for (int w = 0; w < img.getHeight(); w++) {
            for (int h = 0; h < img.getWidth(); h++) {

                dataToStore.add(SgUtils.fill8zero(SgUtils.getRed(this.img.getRGB(h, w))));
                dataToStore.add(SgUtils.fill8zero(SgUtils.getGreen(this.img.getRGB(h, w))));
                dataToStore.add(SgUtils.fill8zero(SgUtils.getBlue(this.img.getRGB(h, w))));
            }
        }

    }

    public BufferedImage getImage() {
        return this.img;
    }

    public BufferedImage getImageOut() {
        return this.imgOut;
    }

    public void encodeText(SgText hidden, boolean save) {

        String firstOctal = hidden.getFirstByte();
       
        int limit = hidden.getMaxByte();
        int cpt = 0;
        boolean forward = true;

        // ENCODER

        for (int w = 0; w < img.getHeight(); w++) {
            for (int h = 0; h < img.getWidth(); h++) {

                if (forward) {
                    int numbR = (SgUtils.getRed(img.getRGB(h, w)));
                    int numbG = (SgUtils.getGreen(img.getRGB(h, w)));
                    int numbB = (SgUtils.getBlue(img.getRGB(h, w)));

                    String numbRo = SgUtils.fill8zero(numbR);
                    String numbGo = SgUtils.fill8zero(numbG);
                    String numbBo = SgUtils.fill8zero(numbB);

                    if (w == 0 && h == 0) {
                        numbRo = hidden.getFirstByteAdvanced(1);
                        numbGo = hidden.getFirstByteAdvanced(2);
                        numbBo = hidden.getFirstByteAdvanced(3);

                    } else {
                        if (cpt < limit) {
                            numbRo = SgUtils.fill8zero(numbRo, hidden.get(cpt), finalBits);
                            if (cpt + 1 < limit) {
                                numbGo = SgUtils.fill8zero(numbGo, hidden.get(cpt + 1), finalBits);
                            }
                            if (cpt + 2 < limit) {
                                numbBo = SgUtils.fill8zero(numbBo, hidden.get(cpt + 2), finalBits);
                            }
                            cpt += 3;
                        } else {
                            forward = false;
                        }
                    }


                    imgOut.setRGB(h, w, SgUtils.makeRGB(SgUtils.unfill8zero(numbRo), SgUtils.unfill8zero(numbGo), SgUtils.unfill8zero(numbBo)));
                } else {
                    imgOut.setRGB(h, w, img.getRGB(h, w));
                }

            }
            //System.out.println(" ");
        }

        if (save) {
            this.saveOut();
        }
    }

    public void encodeImage(SgImage hidden, boolean save) {

        int limit = hidden.getMaxByte();

        int cpt = 0;
        boolean forward = true;

        // ENCODER

        for (int w = 0; w < img.getHeight(); w++) {
            for (int h = 0; h < img.getWidth(); h++) {

                if (forward) {

                    int numbR = (SgUtils.getRed(img.getRGB(h, w)));
                    int numbG = (SgUtils.getGreen(img.getRGB(h, w)));
                    int numbB = (SgUtils.getBlue(img.getRGB(h, w)));

                    String numbRo = SgUtils.fill8zero(numbR);
                    String numbGo = SgUtils.fill8zero(numbG);
                    String numbBo = SgUtils.fill8zero(numbB);


                    if (w == 0 && h == 0) {
                        numbRo = hidden.getFirstByteAdvanced(1);
                    } else if (w == 0 && h == 1) {
                        numbGo = hidden.getFirstByteAdvanced(2);
                    } else if (w == 0 && h == 2) {
                        numbBo = hidden.getFirstByteAdvanced(3);
                    } else if (w == 0 & h == 3) { // WIDTH
                        numbRo = SgUtils.fill16zero(hidden.img.getWidth()).substring(0, 8);
                    } else if (w == 0 & h == 4) { // WIDTH
                        numbRo = SgUtils.fill16zero(hidden.img.getWidth()).substring(8, 16);
                    } else if (w == 0 & h == 5) { // HEIGHT
                        numbRo = SgUtils.fill16zero(hidden.img.getHeight()).substring(0, 8);
                    } else if (w == 0 & h == 6) { // HEIGHT
                        numbRo = SgUtils.fill16zero(hidden.img.getHeight()).substring(8, 16);
                    } else {

                        if (cpt < limit) {
                            numbRo = numbRo.substring(0, 4) + hidden.dataToStore.get(cpt).substring(0, 4);

                            if (cpt + 1 < limit) {
                                numbGo = numbGo.substring(0, 4) + hidden.dataToStore.get(cpt + 1).substring(0, 4);
                            }
                            if (cpt + 2 < limit) {
                                numbBo = numbBo.substring(0, 4) + hidden.dataToStore.get(cpt + 2).substring(0, 4);
                            }
                            cpt += 3;
                        } else {
                            forward = false;
                        }

                    }

                    imgOut.setRGB(h, w, SgUtils.makeRGB(SgUtils.unfill8zero(numbRo), SgUtils.unfill8zero(numbGo), SgUtils.unfill8zero(numbBo)));
                } else {
                    imgOut.setRGB(h, w, img.getRGB(h, w));
                }

            }
            //System.out.println(" ");
        }

        if (save) {
            this.saveOut();
        }
    }

    public void saveOut() {
        try {

            String ext = "bmp";
            File file = new File("src/steganographie/ImageOut." + ext);
            FileImageOutputStream fios = new FileImageOutputStream(file);
            boolean success = ImageIO.write(imgOut, ext, fios);

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex2) {
            System.out.println(ex2.getMessage());
        }
    }

    public void saveOut2() {
        try {
            System.out.println("saveOut2");
            String ext = "bmp";
            File file = new File("src/steganographie/ImageOutFinal." + ext);
            FileImageOutputStream fios = new FileImageOutputStream(file);
            boolean success = ImageIO.write(imgOut, ext, fios);

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex2) {
            System.out.println(ex2.getMessage());
        }
    }

    public void save() {
        try {

            String ext = "bmp";
            File file = new File("src/steganographie/Image." + ext);
            FileImageOutputStream fios = new FileImageOutputStream(file);
            boolean success = ImageIO.write(imgOut, ext, fios);

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex2) {
            System.out.println(ex2.getMessage());
        }
    }

    public String decodeText() {

        // DECODER
        ArrayList<String> dataToGet = new ArrayList();
        int bitLimit = 0;
        int opt = 0;
        boolean forward = true;

        for (int w = 0; w < imgOut.getHeight(); w++) {
            for (int h = 0; h < imgOut.getWidth(); h++) {
                
                int rgb = imgOut.getRGB(h, w);
                
                int numbR = (SgUtils.getRed(rgb));
                int numbG = (SgUtils.getGreen(rgb));
                int numbB = (SgUtils.getBlue(rgb));

                String numbRo = SgUtils.fill8zero(numbR);
                String numbGo = SgUtils.fill8zero(numbG);
                String numbBo = SgUtils.fill8zero(numbB);

                if (w == 0 && h == 0) {
                    bitLimit = (SgUtils.unfill8zeroAdvanced(numbRo + numbGo + numbBo) * 8) / finalBits;
                } else {
                    if (opt < bitLimit) {
                        dataToGet.add(numbRo.substring(numbRo.length() - finalBits, numbRo.length()));
                        dataToGet.add(numbGo.substring(numbGo.length() - finalBits, numbGo.length()));
                        dataToGet.add(numbBo.substring(numbBo.length() - finalBits, numbBo.length()));
                        opt += 3;
                    } else {
                        forward = false;
                    }
                }
                if (!forward) {
                    break;
                }
                //System.out.print(numbRo + "." + numbGo + "." + numbBo + " | ");
            }
            //System.out.println(" ");
            if (!forward) {
                break;
            }
        }

        //System.out.println();

        if (dataToGet.size() <= 0) {
            return "NaN";
        }

        //System.out.println(dataToGet);

        String MESSAGE_DECODE = "";
        int nbCaract = 0;
        String MESSAGE_FINAL = "";

        for (int i = 0; i < dataToGet.size(); i++) {

            if (nbCaract % 8 == 0 && i > 0) {
                int number = Integer.valueOf(MESSAGE_DECODE, 2);
                MESSAGE_FINAL += (char) number;
                MESSAGE_DECODE = "";
                //System.out.println(deAccent(MESSAGE_FINAL));
            }
            nbCaract += dataToGet.get(i).length();
            MESSAGE_DECODE += dataToGet.get(i);
        }
        int number = Integer.valueOf(MESSAGE_DECODE, 2);
        MESSAGE_FINAL += (char) number;

        System.out.println("Le message lu fait " + MESSAGE_FINAL.length() + " caractères.\n");

        return MESSAGE_FINAL;
    }

    public SgImage decodeImage() {

        // DECODER
        ArrayList<String> dataToGetOriginal = new ArrayList();
        ArrayList<String> dataToGet = new ArrayList();
        int bitLimit = 0;
        int opt = 0;
        boolean forward = true;

        int width = -1;
        int height = -1;
        String bufWidth = "";
        String bufHeight = "";

        String bitLimitString = "";

        for (int w = 0; w < img.getHeight(); w++) {
            for (int h = 0; h < img.getWidth(); h++) {

                //System.out.println("[" + w + "." + h + "]");
                int numbR = (SgUtils.getRed(img.getRGB(h, w)));
                int numbG = (SgUtils.getGreen(img.getRGB(h, w)));
                int numbB = (SgUtils.getBlue(img.getRGB(h, w)));

                String numbRo = SgUtils.fill8zero(numbR);
                String numbGo = SgUtils.fill8zero(numbG);
                String numbBo = SgUtils.fill8zero(numbB);




                if (w == 0 && h == 0) {
                    bitLimitString += numbRo;
                } else if (w == 0 && h == 1) {
                    bitLimitString += numbGo;
                } else if (w == 0 && h == 2) {
                    bitLimitString += numbBo;
                    bitLimit = (SgUtils.unfill8zeroAdvanced(bitLimitString));
                } else if (w == 0 & h == 3) { // WIDTH
                    bufWidth = numbRo;
                } else if (w == 0 & h == 4) { // HEIGHT
                    bufWidth += numbRo;
                    width = SgUtils.unfill16zero(bufWidth);
                } else if (w == 0 & h == 5) { // WIDTH
                    bufHeight = numbRo;
                } else if (w == 0 & h == 6) { // HEIGHT
                    bufHeight += numbRo;
                    height = SgUtils.unfill16zero(bufHeight);
                } else {
                    
                    if (opt < bitLimit) {
                        //Remplit les octect avec les 4 de poids faibles et 4 0 derrière
                        dataToGet.add(SgUtils.fill4zero(numbRo.substring(4,8)));
                        dataToGet.add(SgUtils.fill4zero(numbGo.substring(4,8)));
                        dataToGet.add(SgUtils.fill4zero(numbBo.substring(4,8)));
                        opt += 3;
                    } else {
                        forward = false;
                    }
                }
                if (!forward) {
                    break;
                }
                //System.out.print(numbRo + "." + numbGo + "." + numbBo + " | ");
            }
            //System.out.println(" ");
            if (!forward) {
                break;
            }


        }

        if (dataToGet.size() <= 0) {
            System.out.println("dataToGet 0");
            return null;
        }



        this.imgOut = new BufferedImage(width, height, img.getType());

        int cpt = 0;

        for (int wid = 0; wid < height; wid++) {
            for (int hei = 0; hei < width; hei++) {

                int o1 = SgUtils.unfill8zero(dataToGet.get(cpt));
                int o2 = SgUtils.unfill8zero(dataToGet.get(cpt + 1));
                int o3 = SgUtils.unfill8zero(dataToGet.get(cpt + 2));

                int rgb = SgUtils.makeRGB(o1, o2, o3);

                this.imgOut.setRGB(hei, wid, rgb);

                cpt += 3;

            }
        }

        this.saveOut2();

        return null;
    }

    public String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public void showMaxBytes() {
        int oct = (img.getHeight() * img.getWidth()) * 3;
        System.out.println("La bitmap contient " + oct + " octets au total dont " + (oct - 3) + " écrivables.\n");
    }

    public String getFirstByteAdvanced(int level) {

        String fa = SgUtils.fill8zeroAdvanced(dataToStore.size());

        if (level == 1) {
            return fa.substring(0, 8);
        } else if (level == 2) {
            return fa.substring(8, 16);
        } else if (level == 3) {
            return fa.substring(16, 24);
        } else {
            return "";
        }
    }

    public int getMaxByte() {
        return dataToStore.size();
    }

    public String get(int id) {
        return dataToStore.get(id);
    }
}
