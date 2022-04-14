package org.csc133.a3;

/**
 * Represents an in game Clock to be
 * displayed in the GlassCockpit
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import java.io.IOException;

public class GameClockComponent extends com.codename1.ui.Component {
    private com.codename1.ui.Image[] digitImages = new com.codename1.ui.Image[10];
    private com.codename1.ui.Image[] digitImagesWithPeriod = new com.codename1.ui.Image[10];
    private com.codename1.ui.Image colonImage;
    private com.codename1.ui.Image[] clockDigits = new com.codename1.ui.Image[6];
    private int colonIndex = 2;
    private int numOfImages = 6;
    private int ledColor;
    private int tsColor;
    private static long startTime;
    private static long pausedTime = 0;
    private static long totalPause = 0;

    /**
     * Constructor for GameClock Component
     */
    public GameClockComponent() {
        ledColor = ColorUtil.rgb(0,255,240);
        tsColor = ColorUtil.rgb(0,61,152);
        resetElapsedTime();

        try {
            for (int i = 0; i < 10; i++) {
                digitImages[i] = com.codename1.ui.Image.createImage("/LED_digit_" + i + ".png");
                digitImagesWithPeriod[i] = com.codename1.ui.Image.createImage("/LED_digit_" + i + "_with_dot.png");
            }
            colonImage = com.codename1.ui.Image.createImage("/LED_colon.png");
        } catch (
                IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < numOfImages; i++) {
            clockDigits[i] = digitImages[0];
        }

        clockDigits[2] = colonImage;
        clockDigits[4] = digitImagesWithPeriod[0];

    }

    /**
     * Takes in Minutes, Seconds and seconds/10 to
     * uoppdate the component
     * @param m
     * @param s
     * @param ts
     */
    private void setTime(int m, int s, int ts){
        clockDigits[0] = digitImages[m/10];
        clockDigits[1] = digitImages[m%10];
        clockDigits[3] = digitImages[(s%60)/10];
        clockDigits[4] = digitImagesWithPeriod[(s%60)%10];
        clockDigits[5] = digitImages[ts%10];
    }

    /**
     * Calculates the Minutes, Seconds and secodns/10
     * to send to setTime()
     */
    private void setCurrentTime(){
        long elapsedTime = getElapsedTime();
        setTime((int)(elapsedTime/60000), (int)(elapsedTime/1000) , (int)(elapsedTime/100));
        if(elapsedTime >= 150000){
            ledColor = ColorUtil.rgb(255,0,0);
            tsColor = ColorUtil.rgb(82,0,0);
        }
    }

    public void start(){
        getComponentForm().registerAnimated(this);
    }

    public void stop(){
        getComponentForm().deregisterAnimated(this);
    }

    /**
     * This is a callback method to inform the Component when it's been laidout
     * on the parent Container
     */
    public void laidOut(){
        this.start();
    }


    /**
     * {@inheritDoc}
     */
    public boolean animate(){
        setCurrentTime();
        return true;
    }

    /**
     * Calculates the preferred size based on component content. This method is
     * invoked lazily by getPreferred size.
     *
     * @return the calculated preferred size based on component content
     */
    protected Dimension calcPreferredSize(){
        return new Dimension(colonImage.getWidth()*numOfImages,colonImage.getHeight());
    }

    /**
     * This method paints the Component on the screen, it should be overriden
     * by subclasses to perform custom drawing or invoke the UI API's to let
     * the PLAF perform the rendering.
     *
     * @param g the component graphics
     */
    public void paint(Graphics g){
        super.paint(g);
        final int COLOR_PAD = 1;

        int digitWidth = clockDigits[0].getWidth();
        int digitHeight = clockDigits[0].getHeight();
        int clockWidth = numOfImages*digitWidth;

        float scaleFactor = Math.min(
                getInnerHeight()/(float)digitHeight,
                getInnerWidth()/(float) clockWidth);

        int displayDigitWidth = (int) (scaleFactor * digitWidth);
        int displayDigitHeight = (int) (scaleFactor * digitHeight);
        int displayClockWidth = numOfImages*displayDigitWidth;

        int displayX = getX() + (getWidth()-displayClockWidth)/2;
        int displayY = getY() + (getHeight()-displayDigitHeight)/2;

        g.setColor(ledColor);
        g.fillRect(displayX+COLOR_PAD,
                displayY+COLOR_PAD,
                displayClockWidth - COLOR_PAD*2 - displayDigitWidth,
                displayDigitHeight-COLOR_PAD*2);

        g.setColor(tsColor);
        g.fillRect(displayX + displayClockWidth - COLOR_PAD*3 - displayDigitWidth,
                displayY+COLOR_PAD,
                displayDigitWidth - 2*COLOR_PAD,
                displayDigitHeight-COLOR_PAD*2);

        for(int digitIndex = 0; digitIndex < numOfImages; digitIndex++)
        {
            g.drawImage(clockDigits[digitIndex], displayX+digitIndex*displayDigitWidth, displayY, displayDigitWidth, displayDigitHeight);
        }

    }

    /**
     * Getter for elapsed Time
     * @return long representing time in ms the program has been running
     */
    public long getElapsedTime(){
        return System.currentTimeMillis() - startTime - totalPause;
    }

    public static void startElapsedTime(){
        pausedTime = System.currentTimeMillis() - pausedTime;
        totalPause += pausedTime;
    }

    public static void stopElapsedTime(){
        pausedTime = System.currentTimeMillis();
    }

    /**
     * Resets the elapsed time
     */
    public void resetElapsedTime() {
        startTime = System.currentTimeMillis();
        pausedTime = 0;
        totalPause = 0;
    }
}
