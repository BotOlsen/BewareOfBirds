package org.csc133.a3;

/** Represents a Fixed GameObject.
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

public abstract class Fixed extends GameObject{

    /**
     * Constructor for the Fixed Object.
     * @param size Integer to represent the number of pixels of
     *             object Length on Screen.
     * @param color Integer to represent the color of the Object.
     * @param x X-Coordinate that represents holds the (x) location
     *              of the Object.
     * @param y Y-Coordinate that represents holds the (x) location
     *              of the Object
     */
    public Fixed(int size, int color, float x, float y){
        super(size, color, x, y);

    }

    /**
     * Setter for the Point2D representation of the GameObject Location
     * that makes it so Location of fixed objects cannot be changed.
     * @param x X-Coordinate that represents holds the (x) location
     *              of the Object.
     * @param y Y-Coordinate that represents holds the (y) location
     *              of the Object
     */
    @Override
    public void setLocation(float x, float y){

    }

}