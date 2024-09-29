package tk.minas.clients.music;

import tk.minas.catalogue.Basket;
import tk.minas.clients.Picture;
import tk.minas.middle.MiddleFactory;
import tk.minas.middle.OrderProcessing;
import tk.minas.middle.StockException;
import tk.minas.middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;


/**
 * View of the model
 * @author  M A Smith (c) June 2014
 */
public class MusicView implements Observer {



    private static final int H = 300;       // Height of window pixels
    private static final int W = 400;       // Width  of window pixels

    private static final String prev  = "<";
    private static String play = "⏸️" ;
    private static final String next = ">";

    private Picture thePicture = new Picture(250,145);





    private final JLabel  theAction  = new JLabel( "" , SwingConstants.CENTER);

    private final JButton     thePrevBtn = new JButton(prev);
    private final JButton     thePlayBtn   = new JButton(play);
    private final JButton     theNextBtn= new JButton(next);


    private MusicController cont   = null;



    public MusicView(  RootPaneContainer rpc, int x, int y  ) {

        Container cp         = rpc.getContentPane();    // Content Pane
        Container rootWindow = (Container) rpc;         // Root Window
        cp.setLayout(null);                             // No layout manager
        rootWindow.setSize( W, H );                     // Size of Window
        rootWindow.setLocation( x, y );

        Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is

        thePrevBtn.setBounds( 16, 110, 50, 40 );    // Check Button
        thePrevBtn.addActionListener(                   // Call back code
                e -> cont.goBack());
        cp.add(thePrevBtn);                           //  Add to canvas


        thePlayBtn.setBounds( 174, 220, 50, 40 );    // Check Button

        thePlayBtn.addActionListener(                   // Call back code
                e -> cont.pause());

        cp.add(thePlayBtn);                           //  Add to canvas


        theNextBtn.setBounds( 340, 110, 50, 40 );    // Check Button
        theNextBtn.addActionListener(                   // Call back code
                e -> cont.goNext());
        cp.add(theNextBtn);                           //  Add to canvas




        theAction.setBounds( 0, 10 , 390, 20 );       // Message area
        theAction.setText("Click Play to play music");                        // Blank
        cp.add( theAction );                            //  Add to canvas


        thePicture.setBounds( 80, 55, 10, 10 );   // Picture area
        cp.add( thePicture );                           //  Add to canvas
        thePicture.clear();


    }

    /**
     * The controller object, used so that an interaction can be passed to the controller
     * @param c   The controller
     */

    public void setController( MusicController c ){
        cont = c;
    }

    /**
     * Update the view
     * @param modelC   The observed model
     * @param arg      Specific args
     */
    @Override
    public void update( Observable modelC, Object arg ) {
        MusicModel model  = (MusicModel) modelC;
        String      message = (String) arg;
        theAction.setText( message );



        ImageIcon image = model.getThePic();
        if ( image == null )
        {
            thePicture.clear();
        } else {
            thePicture.set( image );
        }




    }

}
