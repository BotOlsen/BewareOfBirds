package org.csc133.a3;

/**
 * Represents Fuel Component
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import java.io.IOException;

public class FuelComponent extends com.codename1.ui.Component {

    private com.codename1.ui.Image[] digitImages = new com.codename1.ui.Image[10];
    private com.codename1.ui.Image[] fuelDigits = new com.codename1.ui.Image[4];
    private int ledColor;
    private int numOfDigits = 4;

    public FuelComponent() {
        ledColor = ColorUtil.rgb(143, 69, 255);

        try {
            for (int i = 0; i < 10; i++) {
                digitImages[i] = com.codename1.ui.Image.createImage("/LED_digit_" + i + ".png");
            }
        } catch (IOException e) { e.printStackTrace(); }


        for (int i = 0; i < numOfDigits; i++) {
            fuelDigits[i] = digitImages[0];
        }
    }

    /**
     * Sets the fuel to be displayed on the Component
     * @param fuel Integer
     */
    public void setFuel(int fuel){
        if(fuel > 9999)
            return;
        fuelDigits[0] = digitImages[fuel/1000];
        fuelDigits[1] = digitImages[(fuel%1000)/100];
        fuelDigits[2] = digitImages[((fuel%1000)%100)/10];
        fuelDigits[3] = digitImages[(((fuel%1000)%100)%10)];
    }

    /**
     * Calculates the preferred size based on component content. This method is
     * invoked lazily by getPreferred size.
     *
     * @return the calculated preferred size based on component content
     */
    protected Dimension calcPreferredSize(){
        return new Dimension(digitImages[0].getWidth()*numOfDigits, digitImages[0].getHeight());
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

        int digitWidth = fuelDigits[0].getWidth();
        int digitHeight = fuelDigits[0].getHeight();
        int clockWidth = numOfDigits*digitWidth;

        float scaleFactor = Math.min(
                getInnerHeight()/(float)digitHeight,
                getInnerWidth()/(float) clockWidth);

        int displayDigitWidth = (int) (scaleFactor * digitWidth);
        int displayDigitHeight = (int) (scaleFactor * digitHeight);
        int displayFuelWidth = numOfDigits*displayDigitWidth;

        int displayX = getX() + (getWidth()-displayFuelWidth)/2;
        int displayY = getY() + (getHeight()-displayDigitHeight)/2;




        g.setColor(ledColor);
        g.fillRect(displayX+COLOR_PAD,
                displayY+COLOR_PAD,
                displayFuelWidth - COLOR_PAD*2,
                displayDigitHeight-COLOR_PAD*2);
        for(int digitIndex = 0; digitIndex < numOfDigits; digitIndex++)
        {
            g.drawImage(fuelDigits[digitIndex], displayX+digitIndex*displayDigitWidth, displayY, displayDigitWidth, displayDigitHeight);
        }

    }
}
