package org.csc133.a3;

/** Represents a Damage Component of the GlassCockpit
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Dimension;

import java.io.IOException;

public class DamageComponent extends Component {
    private com.codename1.ui.Image[] digitImages = new com.codename1.ui.Image[10];

    private com.codename1.ui.Image[] dmgDigit = new Image[2];
    private int ledColor;
    private int numOfDigits = 2;


    /**
     * Constructor for Damage Component of GlassCockpit
     */
    public DamageComponent() {
        ledColor = ColorUtil.rgb(0, 255, 56);
        try {
            for (int i = 0; i < 10; i++) {
                digitImages[i] = com.codename1.ui.Image.createImage("/LED_digit_" + i + ".png");
            }
        } catch (IOException e) { e.printStackTrace(); }
        for (int i = 0; i < numOfDigits; i++) {
            dmgDigit[i] = digitImages[0];
        }
    }

    /**
     * Setter for damage to be displayed
     * @param dmg Integer
     */
    public void setDmg(int dmg){
        if(dmg >= 50 && dmg < 85 )
            setLedColor(ColorUtil.rgb(233, 245, 66));
        else if(dmg >= 85)
            setLedColor(ColorUtil.rgb(255,0,0));
        else
            ledColor = ColorUtil.rgb(0, 255, 56);
        dmgDigit[0] = digitImages[dmg/10];
        dmgDigit[1] = digitImages[dmg%10];
    }

    /**
     * Sets the led Colo of dmg component
     * @param ledColor Int representing ColorUtil.rgb() return integer.
     */
    private void setLedColor(int ledColor){
        this.ledColor = ledColor;
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

        int digitWidth = dmgDigit[0].getWidth();
        int digitHeight = dmgDigit[0].getHeight();
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
            g.drawImage(dmgDigit[digitIndex], displayX+digitIndex*displayDigitWidth, displayY, displayDigitWidth, displayDigitHeight);

    }
}