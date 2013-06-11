package steganographie;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;



/**
 * Classe qui gère la barre de menu du logiciel
 * @author Jessym
 */
public class SgMenuBar extends JMenuBar {

   public static JMenu fichier = new JMenu("Fichier");
   public static JMenu apropos = new JMenu("À propos");
           

    public JMenuItem 
            ouvrir = new JMenuItem("Ouvrir"),
            enregistrer = new JMenuItem("Enregistrer"),
            encoder = new JMenuItem("Encoder"),
            decoder = new JMenuItem("Décoder"),
            quitter = new JMenuItem("Quitter"),
            what = new JMenuItem("?")
            ;
    
    public  JMenuItem SP = new JMenuItem("SP");
    
    JMenu[] jmt;
    JMenuItem[] jmit0, jmit1;
    KeyStroke[] kst;

    SgPanel sg = null;
    
    /**
     * Constructeur de la barre de menu
     * @param mf Sauvegarde la fenêtre qui englobe la barre de menu
     */
    public SgMenuBar(JFrame fm,SgPanel sg) {
       

        initJMenu();
        initJMenuItem();
        
        this.sg = sg;
        
        fm.setJMenuBar(this);
    }

    /**
     * Initialise les principaux Menus (JMenu)
     */
    public void initJMenu() {
        jmt = new JMenu[]{fichier, apropos};

        for (int i = 0; i < jmt.length; i++) {
            add(jmt[i]);
            jmt[i].setMnemonic(jmt[i].getText().charAt(0));
        }
    }

    /**
     * Initilise les boutons du menu (JMenuItem)
     */
    public void initJMenuItem() {

        jmit0 = new JMenuItem[]{
             ouvrir, enregistrer, SP, encoder,decoder, SP, quitter
        };
        kst = new KeyStroke[]{
            KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK),
            KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK),
            null,
            KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK),
            KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_MASK),
            null,
            KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK)
        };
        chargeIn(jmit0, 0, kst);


        ouvrir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                sg.getOpenImg().doClick();

            }
        });
        
        enregistrer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                sg.getSaveNew().doClick();
            }
        });
        
        encoder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                sg.getEncodeButton().doClick();
            }
        });
        
        decoder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                sg.getDecodeButton().doClick();
            }
        });
        
        quitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                System.exit(0);
            }
        });



        jmit1 = new JMenuItem[]{
            what
        };
        kst = new KeyStroke[]{
            null};
        chargeIn(jmit1, 1, kst);

        what.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
               JOptionPane jop = new JOptionPane();
               jop.showMessageDialog(null,"LP Dasi 2013 - Réseau - Stéganographie\nJessym REZIGA\nRafi PANOYAN\n","A propos",JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }

    /**
     * Charge dans un menu principal (JMenu) un bouton d'action (JMenuItem)
     * @param jmat Tableau des boutons d'actions à insérer
     * @param jmenuIndex Menu principal qui va contenir les boutons d'actions
     */
    public void chargeIn(JMenuItem[] jmat, int jmenuIndex) {
        for (int i = 0; i < jmat.length; i++) {

            if (jmat[i].getText().equals("SP")) {
                jmt[jmenuIndex].addSeparator();
            } else {
                jmt[jmenuIndex].add(jmat[i]);
            }
        }
    }

    /**
     * Charge dans un menu principal (JMenu) un bouton d'action (JMenuItem) ainsi que leurs raccourcis associés
     * @param jmat Tableau des boutons d'actions à insérer
     * @param jmenuIndex Menu principal qui va contenir les boutons d'actions
     * @param kmat Tableau des raccourcis liés aux boutons d'actions
     */
    public void chargeIn(JMenuItem[] jmat, int jmenuIndex, KeyStroke[] kmat) {
        for (int i = 0; i < jmat.length; i++) {

            if (jmat[i].getText().equals("SP")) {
                jmt[jmenuIndex].addSeparator();
            } else {
                jmt[jmenuIndex].add(jmat[i]);
                jmat[i].setAccelerator(kmat[i]);
            }
        }
    }

    /**
     * Méthode utilitaire qui rafraîchi un panneau d'affichage
     * @param o Panneau d'affichage à rafraîchir
     */
    public static void refresh(JComponent o) {
        o.setSize(o.getWidth() + 1, o.getHeight() + 1);
        o.setSize(o.getWidth() - 1, o.getHeight() - 1);
    }
}
