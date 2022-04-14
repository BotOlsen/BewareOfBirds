package org.csc133.a3;


//Sound made by Eric Matyas, www.soundimage.org
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

import java.io.InputStream;

public class Sound {
    private Media media;
    private int soundVolume;

    public Sound(String fileName, int soundVolume){
        this.soundVolume = soundVolume;
        try{
            InputStream inputStream = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
            media = MediaManager.createMedia(inputStream, "audio/wav");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void play(){
        media.setVolume(soundVolume);
        media.setTime(0);
        media.play();
    }
}
