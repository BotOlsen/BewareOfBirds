package org.csc133.a3;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

import java.io.InputStream;

public class BGSound implements Runnable {
    private Media media;
    private int soundVolume;
    private boolean soundFlag = true;

    public BGSound(String fileName, int soundVol) {
        this.soundVolume = soundVol;
        try {
            InputStream inputStream = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
            media = MediaManager.createMedia(inputStream, "audio/wav", this);
            media.setVolume(soundVolume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        media.pause();
    }

    public void play() {
        media.play();
    }

    @Override
    public void run() {
        media.setTime(0);
        media.play();
    }

    public void toggleSound(boolean onOff) {
        soundFlag = onOff;
        if (onOff) {
            media.play();
        } else {
            media.pause();
        }
    }

    public boolean getSoundFlag(){
        return soundFlag;
    }
}

