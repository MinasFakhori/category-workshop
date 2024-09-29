package tk.minas.clients;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
    private String filePath;
    private  Clip clip;

    private boolean isPlaying = false;

    public boolean getIsPlaying(){
        return isPlaying;
    }


    public boolean getIsFinished(){
        return clip.isRunning();
    }


    public Sound (String filePath){
        this.filePath = filePath;

    }

    public void play(){
        isPlaying = true;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filePath));
            clip.open(stream);
            clip.start();


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    public void stop(){
        isPlaying = false;
        clip.stop();
    }
}
