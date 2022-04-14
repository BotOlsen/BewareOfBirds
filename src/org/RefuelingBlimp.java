package org.csc133.a3;

/** Represents a RefuelingBlimp GameObject.
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Point;

import java.io.IOException;

public class RefuelingBlimp extends Fixed implements IDrawable{

    private int capacity;
    private Image emptyBlimp;
    private Image fullBLimp;

    /**
     * Constructor for the Helicopter Object.
     * @param size Integer to represent the number of pixels of
     *             object Length on Screen.
     * @param color Integer to represent the color of the Object.
     * @param x Float representing X location
     * @param y Float representing Y location
     */
    public RefuelingBlimp(int size, int color, float x, float y){
        super(size, color, x, y);
        this.capacity = (int)(size * 20);
        try{
            emptyBlimp = Image.createImage("/EmptyBlimp.png");
            fullBLimp = Image.createImage("/FullBlimp.png");
        }catch(IOException e){e.printStackTrace();}
    }

    /**
     * Getter for the capacity variable of the RefuelingBlimp Object
     * @return capacity Integer representing the capacity of RefuelingBlimp.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Setter for the capacity variable of the RefuelingBlimp Object
     * @param capacity Integer representing the capacity of RefuelingBlimp.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Image getFullBLimp(){
        return fullBLimp;
    }

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
        return super.toString() + ", capacity = " + capacity + "  ";
    }

    /**
     * Draws the RefuelingBlimp GameObject in the MapView
     * @param g Graphics component
     * @param containerOrigin MapView Origin
     */
    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(this.getColor());
        if(fullBLimp != null) {
            if (this.getCapacity() == 0) {
                g.drawImage(emptyBlimp,
                        (int) (containerOrigin.getX() + this.getXLocation() - fullBLimp.getWidth()/2),
                        (int) (containerOrigin.getY() + this.getYLocation()) - fullBLimp.getHeight()/2);
            } else {
                g.drawImage(fullBLimp,
                        (int) (containerOrigin.getX() + this.getXLocation() - fullBLimp.getWidth()/2),
                        (int) (containerOrigin.getY() + this.getYLocation()) - fullBLimp.getHeight()/2);
            }
        }
        else {
            g.fillArc((int) (containerOrigin.getX() + this.getXLocation() - this.getSize()),
                    (int) (containerOrigin.getY() + this.getYLocation() - this.getSize()),
                    2 * this.getSize(),
                    this.getSize(),
                    0,
                    360);
        }
        g.setColor(ColorUtil.BLACK);
        g.drawString( "0000".substring(Integer.toString(getCapacity()).length()) + getCapacity(),
                (int)(containerOrigin.getX() + this.getXLocation()) - 30,
                (int)(containerOrigin.getY() + this.getYLocation()) + 30);
    }

    @Override
    public boolean collidesWith(GameObject otherObject) {
        return false;
    }

    @Override
    public void handleCollision(GameObject otherObject) { }

}

