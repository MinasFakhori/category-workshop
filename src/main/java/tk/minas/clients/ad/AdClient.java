package tk.minas.clients.ad;

import tk.minas.middle.MiddleFactory;
import tk.minas.middle.Names;
import tk.minas.middle.RemoteMiddleFactory;


import javax.swing.*;

public class AdClient {
        public static void main (String args[]) {
            String stockURL = Names.STOCK_R;

            RemoteMiddleFactory mrf = new RemoteMiddleFactory();
            mrf.setStockRInfo( stockURL );


            displayGUI(mrf);                          // Create GUI
        }

        private static void displayGUI(MiddleFactory mf) {
            JFrame  window = new JFrame();
            window.setTitle( "AD Client" );
            window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

            AdModel model = new AdModel(mf);
            AdView view  = new AdView( window, mf, 0, 0 );
            AdController cont  = new AdController( model, view );
            view.setController( cont );

            model.addObserver( view );
            window.setVisible(true);
        }
}

