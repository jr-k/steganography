/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package steganographie;

import a.util.ImageFileFilter;
import a.util.JSFileChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Jessym
 */
public class SgImgPanel extends JPanel implements MouseListener{

    public BufferedImage img;

    public boolean shows = true;
    
    public SgImgPanel() {
        this.defineDnD();

    }
    
    public SgImgPanel(boolean shows) {
        this.defineDnD();
        this.shows = shows;
    }

    public void setImageB(BufferedImage i) {
        this.img = i;
        this.setPreferredSize(new Dimension(i.getWidth(),i.getHeight()));
        JScrollPane p = (JScrollPane) this.getParent().getParent();
        p.setPreferredSize(new Dimension(366,100));
        
        this.revalidate();
    }

    public BufferedImage getImageB() {
        return this.img;
    }

    public void paint(Graphics g) {

        /*
        if (shows) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.BLACK);
            g.drawString("Glissez-déposez ici...", 120, this.getHeight() / 2);
        }
        * */
        
        g.drawImage(img, 0, 0, this);


        this.repaint();
    }

    public void openIN(){
        JSFileChooser ofc = new JSFileChooser();

        ofc.removeChoosableFileFilter(ofc.getAcceptAllFileFilter());
        ofc.addChoosableFileFilter(new ImageFileFilter(".bmp", "Fichier image au format Bitmap"));

        if (ofc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = new File(ofc.getSelectedFile().getAbsolutePath());
            this.openImage(file);

        }
    }
    
    
    /**
     * Défini le comportement du glisser-déposer sur la fenêtre principal du
     * logiciel
     */
    public void defineDnD() {
        
        
        this.addMouseListener(this);
        
        
        final SgImgPanel area = this;
        DropTargetListener dropListener = new DropTargetListener() {

            public void dragEnter(DropTargetDragEvent dtde) {
                doDrag(dtde);
            }

            public void dragOver(DropTargetDragEvent dtde) {
                doDrag(dtde);
            }

            public void dropActionChanged(DropTargetDragEvent dtde) {
                doDrag(dtde);
            }

            public void dragExit(DropTargetEvent dte) {
            }

            private void doDrag(DropTargetDragEvent dtde) {
                if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    dtde.acceptDrag(dtde.getDropAction());
                } else {
                    dtde.rejectDrag();
                }
            }

            public void drop(DropTargetDropEvent dtde) {
                dtde.acceptDrop(dtde.getDropAction());
                List data;
                try {
                    data = (List) dtde.getTransferable().
                            getTransferData(DataFlavor.javaFileListFlavor);
                } catch (UnsupportedFlavorException e) {
                    dtde.dropComplete(false);
                    return;
                } catch (IOException e) {
                    dtde.dropComplete(false);
                    return;
                }
                if (((File) data.get(0)).getAbsoluteFile().toString().endsWith("iwp")) {
                    //openProject((File) data.get(0));
                } else if (data.size() == 1) {
                    openImage((File) data.get(0));
                } else {
                    // openListImage(data);
                }
                dtde.dropComplete(true);
            }
        };
        area.setDropTarget(new DropTarget(area, DnDConstants.ACTION_COPY_OR_MOVE, dropListener));
    }

    /*
     * A partir d'ici on gère l'ouverture de fichier grâce au Drag And Drop
     */
    /**
     * Ouvre une image donnée en paramètre
     *
     * @param file image à ouvrir
     */
    public void openImage(File file) {

        try {
            FileImageInputStream fis = new FileImageInputStream(file);
            BufferedImage tmp = ImageIO.read(fis);
            
            boolean resize = false;

            if (resize) {

                ResizeImage.IMG_WIDTH = this.getWidth();
                ResizeImage.IMG_HEIGHT = this.getHeight();

                tmp = ResizeImage.resizeImageWithHint(tmp, tmp.getType());

            }
            
            this.img = tmp;
            
            this.setPreferredSize(new Dimension(tmp.getWidth(),tmp.getHeight()));
            this.revalidate();
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SgImgPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SgImgPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        openIN();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
    

}
