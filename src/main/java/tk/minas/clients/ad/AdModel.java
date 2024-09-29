package tk.minas.clients.ad;



import tk.minas.catalogue.Product;
import tk.minas.middle.MiddleFactory;
import tk.minas.middle.StockException;
import tk.minas.middle.StockReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class AdModel extends Observable {

    private final List<Item> items = new ArrayList<>();

    private int index = 0;


    private StockReader theStock = null;

    private Product pr;

    private String theAction = "";
    private ImageIcon icon;

        public  ImageIcon getThePic(){
            return icon;
        }


    public ImageIcon ad_img(String filePath) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return icon = new ImageIcon(img);
    }

    private Item i1= new Item("0001" ,ad_img("src/main/resources/ad_images/tv.jpg") );
    private Item i2= new Item("0002" ,ad_img("src/main/resources/ad_images/radio.jpg") );
    private Item i3= new Item("0003" ,ad_img("src/main/resources/ad_images/toaster.jpg") );


    public AdModel(MiddleFactory mf) {

        try {
            theStock = mf.makeStockReader();
        } catch (StockException e) {
            throw new RuntimeException(e);
        }
        items.add(i1);
        items.add(i2);
        items.add(i3);


        new Thread(this::changeOnTimer).start();
    }

    public void changeOnTimer(){
            while ( true ){
                try {
                        goNext();
                    Thread.sleep(5000);
                    }
                catch ( InterruptedException e )
                {
                    System.out.println(e);
                }
            }

    }



    public int goNext(){

        if (index == items.size()-1){
            index = 0;
        }else{
            index += 1;
        }
        icon = items.get(index).getImagePath();
        getItem(items.get(index).getName());
        return index;
        }



    public void getItem(String productNum ) {

        try {
            if (theStock.exists( productNum )){
               pr = theStock.getDetails( productNum );

               theAction = pr.getDescription();
            }
        } catch( StockException e ) {
            System.out.println(e);
        }
        setChanged(); notifyObservers(theAction);
    }

}
