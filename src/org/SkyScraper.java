package org.csc133.a3;

/** Represents a Skyscraper GameObject.
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Point;

import java.io.IOException;

public class SkyScraper extends Fixed{

    private int sequenceNumber;
    private Image skyscraperImage;
    private boolean checkpointReached = false;

    /**
     * Constructor for the Skyscraper Object.
     * @param size Integer to represent the number of pixels of
     *             object Length on Screen.
     * @param color Integer to represent the color of the Object.
     * @param x Float representing X location
     * @param y Float representing Y location
     * @param sequenceNumber Integer to represent what number the
     *                       Skyscraper Object is in the GameWorld.
     */
    public SkyScraper(int size, int color, float x, float y, int sequenceNumber, String filename){
        super(size, color, x, y);
        this.sequenceNumber = sequenceNumber;
        if (sequenceNumber == 1)
            checkpointReached = true;
        try{
            skyscraperImage = Image.createImage(filename);
        }catch(IOException e){e.printStackTrace();}
    }
    /**
     * Getter for the sequence number of the Skyscraper
     * @return sequence number of the Skyscraper GameObject
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Setter for the sequenceNumber variable of the Skyscraper Object
     * @param sequenceNumber Integer to assign to Skyscraper's sequence number
     */
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Image getSkyscraperImage(){
        return skyscraperImage;
    }

    public void setCheckpointReached(){
        checkpointReached = true;
    }

    /**
     * Setter for Skyscraper to stop color changing.
     * @param r Integer to assign to the Objects Red channel
     * @param g Integer to assign to the Objects Green channel
     * @param b Integer to assign to the Objects Blue channel
     */
    @Override
    public void setColor(int r, int g, int b) { }

    /**
     * Returns a string representation of the object. In general, the
     * method returns a string that "textually represents" this object.
     * The result should be a concise but informative representation
     * that is easy for a person to read.
     *
     * @return  a string representation of the object.
     */
    @Override
    public String toString() {
        return super.toString() + ", sequenceNumber=" + sequenceNumber + "   ";
    }

    /**
     * Draws the SkyScraper GameObject in the MapView
     * @param g Graphics component
     * @param containerOrigin MapView Origin
     */
    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(getColor());
        if(skyscraperImage != null) {
            g.drawImage(skyscraperImage,
                    (int) (containerOrigin.getX() + this.getXLocation() - this.getSize()),
                    (int) (containerOrigin.getY() + this.getYLocation() - this.getSize()));
            if(checkpointReached){
                g.setColor(ColorUtil.rgb(255,0,0));
                g.drawRect((int)(containerOrigin.getX() + this.getXLocation() - skyscraperImage.getWidth()/3 ),
                        (int)(containerOrigin.getY() + this.getYLocation()) - skyscraperImage.getHeight()/3,
                        skyscraperImage.getWidth(),
                        skyscraperImage.getHeight());
            }
        }
        else{
            g.fillRect((int)(containerOrigin.getX() + this.getXLocation() - this.getSize()),
                    (int)(containerOrigin.getY() + this.getYLocation()  - this.getSize()),
                    2*this.getSize(),
                    2*this.getSize());
        }
        g.setColor(ColorUtil.BLACK);
        g.drawChar( (char) (getSequenceNumber() + 48),
                (int)(containerOrigin.getX() + this.getXLocation()  - this.getSize()/4),
                (int)(containerOrigin.getY() + this.getYLocation() - this.getSize()/2));
    }

    @Override
    public boolean collidesWith(GameObject otherObject) {
        return false;
    }

    @Override
    public void handleCollision(GameObject otherObject) {
    }
}