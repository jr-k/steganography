package steganographie;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Steganographie {

    public static void main(String[] args) {


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (UnsupportedLookAndFeelException e) {
        }

        /*
         * int tolerance = 2;
         *
         * SgText texte = new SgText("abxcdfvcvxc", tolerance);
         * texte.showMaxByte(); //texte.showFirstByte(); //texte.showSchema();
         *
         *
         * SgImage image = new SgImage("src/steganographie/Image.bmp",
         * tolerance); image.showMaxBytes();
         *
         * //image.encodeText(texte, true); //SgText recupere =
         * image.decodeText(); //System.out.println("Le message récupéré est : "
         * + recupere.getMessage() + "\n");
     

     
        SgImage Eimage = new SgImage("src/steganographie/imageHidden.bmp", 4);

        SgImage Simage = new SgImage("src/steganographie/imageSource.bmp", 4);

        

        Simage.encodeImage(Eimage, true);
   
     
        
        SgImage Uimage = new SgImage("src/steganographie/ImageOut.bmp", 4);
        */


        
        
        JFrame frame = new JFrame();
        SgPanel textPane = new SgPanel();
        SgPanel2 imgePane = new SgPanel2();
        
        JTabbedPane tab = new JTabbedPane();
        
        tab.add("Text encoding",textPane);
        tab.add("Image encoding",imgePane);
        
        
        SgMenuBar menu = new SgMenuBar(frame, textPane);
        frame.setSize(965, 620);
        frame.setTitle("Stéganographie");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(tab);
        
        frame.setVisible(true);
        frame.setResizable(false);



    }
}
