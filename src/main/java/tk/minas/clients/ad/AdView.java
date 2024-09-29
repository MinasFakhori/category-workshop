package tk.minas.clients.ad;

import tk.minas.clients.Picture;
import tk.minas.middle.MiddleFactory;


import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class AdView implements Observer {


    private static final int H = 300;       // Height of window pixels
    private static final int W = 400;       // Width  of window pixels


    private Picture thePicture = new Picture(350, 200);


    private final JLabel theAction = new JLabel("", SwingConstants.CENTER);


    private AdController cont = null;


    public AdView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {

        Container cp = rpc.getContentPane();    // Content Pane
        Container rootWindow = (Container) rpc;         // Root Window
        cp.setLayout(null);                             // No layout manager
        rootWindow.setSize(W, H);                     // Size of Window
        rootWindow.setLocation(x, y);

        Font f = new Font("Monospaced", Font.PLAIN, 12);  // Font f is


        theAction.setBounds(0, 10, 390, 20);       // Message area
        theAction.setText("");                        // Blank
        cp.add(theAction);                            //  Add to canvas


        thePicture.setBounds(20, 55, 20, 20);   // Picture area
        cp.add(thePicture);                           //  Add to canvas
        thePicture.clear();


    }

    /**
     * The controller object, used so that an interaction can be passed to the controller
     *
     * @param c The controller
     */

    public void setController(AdController c) {
        cont = c;
    }

    /**
     * Update the view
     *
     * @param modelC The observed model
     * @param arg    Specific args
     */
    @Override
    public void update(Observable modelC, Object arg) {
        AdModel model = (AdModel) modelC;
        String message = (String) arg;
        theAction.setText(message);


        ImageIcon image = model.getThePic();  // Image of product
        if (image == null) {
            thePicture.clear();                  // Clear picture
        } else {
            thePicture.set(image);             // Display picture
        }


    }

}
