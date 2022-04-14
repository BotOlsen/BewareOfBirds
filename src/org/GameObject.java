package org.csc133.a3;

/** Represents a GameObject.
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

import java.util.ArrayList;
import java.util.Vector;

public abstract class GameObject implements IDrawable, ICollider{
    private int size;
    private float pointX;
    private float pointY;
    private int color;
    private Vector<GameObject> collisionVector;

    /**
     * Constructor for the GameObject Object.
     * @param size Integer to represent the number of pixels of
     *             object Length on Screen.
     * @param color Integer to represent the color of the Object.
     * @param locX X-Coordinate that represents holds the (x) location
     *              of the Object.
     * @param locY Y-Coordinate that represents holds the (x) location
     *              of the Object
     */
    public GameObject(int size, int color, float locX, float locY){
        this.size = size;
        this.color = color;
        this.pointX = locX;
        this.pointY = locY;
        collisionVector = new Vector<>();
    }

    /**
     * Getter for the size for the GameObject
     * @return size of the GameObject
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Getter for the color of the Object using the ColorUtil.rgb(int,int,int)
     * return value
     * @return Integer combination of R, G, and B channels
     */
    public int getColor() {
        return color;
    }

    /**
     * Setter for the color of the Object using the ColorUtil.rgb(int,int,int)
     * return value
     * @param r Integer to assign to the Objects Red channel
     * @param g Integer to assign to the Objects Green channel
     * @param b Integer to assign to the Objects Blue channel
     */
    public void setColor(int r, int g, int b) {
        this.color = ColorUtil.rgb(r,g,b);
    }

    /**
     * Getter for the Point representation of the GameObject Location
     * @return Point that represents the (x,Y) location
     *         of the GameObject.
     */
    public float getXLocation(){
        return pointX;
    }

    public float getYLocation(){
        return pointY;
    }

    /**
     * Setter for the Point representation of the GameObject Location
     * @param x Float that represents the x location
     *                 of the GameObject.
     * @param y Float that represents the Y location
     *        of the GameObject.
     */
    public void setLocation(float x, float y) {
        this.pointY = y;
        this.pointX = x;
    }

    public Vector<GameObject> getCollisionVector(){
        return collisionVector;
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
        double locationX = this.getXLocation();
        double locationY = this.getYLocation();
        locationX = Math.round(locationX*100.0)/100.0;
        locationY = Math.round(locationY*100.0)/100.0;
        return  "location = (" + locationX + ", " + locationY + ") " +
                ", size=" + size +
                ", color = (" + ColorUtil.red(color) + ", " + ColorUtil.green(color) + ", " + ColorUtil.blue(color) + ")";
    }

}
