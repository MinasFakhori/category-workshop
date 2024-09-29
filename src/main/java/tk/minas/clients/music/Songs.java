package tk.minas.clients.music;

import javax.swing.*;

public class Songs {
    private final ImageIcon imagePath;
    private  String name;
    private  String filePath;


    public Songs(String name , String filePath ,ImageIcon imagePath){
        this.name = name;
        this.filePath = filePath;
        this.imagePath  = imagePath;
    }

    public ImageIcon getImagePath(){
        return this.imagePath;
    }

    public String getName(){
        return this.name;
    }

    public String getFilePath(){
        return this.filePath;
    }

}
