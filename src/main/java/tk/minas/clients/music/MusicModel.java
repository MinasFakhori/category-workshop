package tk.minas.clients.music;

import tk.minas.catalogue.Basket;
import tk.minas.catalogue.Product;
import tk.minas.clients.Sound;
import tk.minas.debug.DEBUG;
import tk.minas.middle.MiddleFactory;
import tk.minas.middle.OrderProcessing;
import tk.minas.middle.StockException;
import tk.minas.middle.StockReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

public class MusicModel extends Observable {
        private ImageIcon icon;

        public  ImageIcon getThePic(){
                return icon;
        }

        private ArrayList <Songs> songsArray  = new ArrayList<>();

        private  boolean isFirstSong = true;


        public ImageIcon songIcon(String filePath) {
                BufferedImage img;
                try {
                        img = ImageIO.read(new File(filePath));
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
                return icon = new ImageIcon(img);
        }


        private  int index =0;
        private String song ="";



        private Sound sound;

        private String theAction = "";


       private Songs s1 = new Songs("Gnash - Imagine If " , "src/main/resources/music/song1.wav" ,  songIcon("src/main/resources/music_images/song1_cover.jpg"));
        private Songs s2 = new Songs("Imagine Dragons - Bones" , "src/main/resources/music/song2.wav" , songIcon("src/main/resources/music_images/song2_cover.jpg"));

        private Songs s3 = new Songs("Lil Wayne - Mirror" , "src/main/resources/music/song3.wav" , songIcon("src/main/resources/music_images/song3_cover.jpg"));





        public MusicModel() {
                songsArray.add(s1);
                songsArray.add(s2);
                songsArray.add(s3);



                song = songsArray.get(index).getFilePath();
                icon = songsArray.get(index).getImagePath();
                sound  = new Sound( song);
                sound.play();
                sound.stop();

                new Thread(this::isFinished).start();
        }


        public void goBack(){
                sound.stop();
                if (index == 0){
                        songsArray.get(songsArray.size()-1); //Loop through music
                        index = (songsArray.size()-1);
                        sound = new Sound( songsArray.get(index).getFilePath());
                        sound.play();
                }else{
                        index = index -1;
                        sound = new Sound( songsArray.get(index).getFilePath());
                        sound.play();
                        songsArray.get(index);
                }
                icon = songsArray.get(index).getImagePath();
                theAction = songsArray.get(index).getName();
                setChanged(); notifyObservers(theAction); // Notify
                isFirstSong = false;

        }


        public void goNext(){
                sound.stop();
                if (index == songsArray.size()-1){
                        songsArray.get(0).getFilePath(); //Loop through music
                        index = 0;
                }else{
                        songsArray.get(index + 1);
                        index += 1;
                }
                sound = new Sound(songsArray.get(index).getFilePath());
                sound.play();
                icon = songsArray.get(index).getImagePath();
                theAction = songsArray.get(index).getName();
                setChanged(); notifyObservers(theAction); // Notify
                isFirstSong = false;
        }


        public void pause() {
                if (sound.getIsPlaying()){
                        sound.stop();
                        isFirstSong  = true;
                }else{
                        sound.play();
                        icon = songsArray.get(index).getImagePath();
                        theAction = songsArray.get(index).getName();
                        setChanged(); notifyObservers(theAction); // Notify
                        isFirstSong = false;
                }
        }


        public void isFinished(){
                while ( true ){
                        try {
                                if (!sound.getIsFinished() && !isFirstSong){
                                        goNext();
                                }
                                Thread.sleep(2000);

                        }
                        catch ( InterruptedException e )
                        {
                                System.out.println(e);
                        }
                }
        }
}