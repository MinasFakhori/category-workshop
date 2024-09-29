package tk.minas.clients.music;


import tk.minas.clients.PosOnScrn;

import javax.swing.*;
import java.awt.*;

public class MusicClient {
        private Dimension pos;

        public MusicClient(Dimension pos){this.pos = pos;}

        public static void main (String args[]) {
                Dimension pos = PosOnScrn.getPos();
                MusicClient mc = new MusicClient(pos);
                mc.run();
            }

        public void run(){
                JFrame window = new JFrame("Music Client");
                window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                MusicModel model = new MusicModel();
                MusicView view  = new MusicView( window, pos.width, pos.height);
                MusicController cont  = new MusicController( model, view );
                view.setController( cont );
                model.addObserver( view );
                window.setVisible(true);
        }
}
