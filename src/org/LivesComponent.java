package org.csc133.a3;

/** Represents a Lives Component
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import java.io.IOException;

public class LivesComponent extends Component {
    private com.codename1.ui.Image[] digitImages = new com.codename1.ui.Image[10];
    private com.codename1.ui.Image livesDigit;
    private int ledColor;
    private int numOfDigits = 1;

    /**
     * Constructs Lives Component
     */
    public LivesComponent() {

        ledColor = ColorUtil.rgb(132, 177, 250);

        try {
            for (int i = 0; i < 6; i++) {
                digitImages[i] = com.codename1.ui.Image.createImage("/LED_digit_" + i + ".png");
            }
        } catch (IOException e) { e.printStackTrace(); }


        livesDigit = digitImages[0];
    }

    /**
     * Setter for lives to be displayed on Screen
     * @param lives
     */
    public void setLives(int lives){
        livesDigit = digitImages[lives];
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

        int digitWidth = livesDigit.getWidth();
        int digitHeight = livesDigit.getHeight();
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

        g.drawImage(livesDigit, displayX, displayY, displayDigitWidth, displayDigitHeight);

    }
}
