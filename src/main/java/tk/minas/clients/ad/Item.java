package tk.minas.clients.ad;

import javax.swing.*;

public class Item {
    private final ImageIcon imagePath;

    public ImageIcon getImagePath(){
        return imagePath;
    }
    private  String name;

    public String getName(){
        return name;
    }

    public Item(String name, ImageIcon imagePath){
        this.name = name;

        this.imagePath  = imagePath;
    }



}
