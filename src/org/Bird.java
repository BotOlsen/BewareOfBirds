package org.csc133.a3;

/** Represents a Bird GameObject.
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Point;

import java.io.IOException;
import java.util.Random;

public class Bird extends Moveable {

    private Image birdImage;

    /**
     * Constructor for the Bird Object.
     * @param size Integer to represent the number of pixels of
     *             object Length on Screen.
     * @param color Integer to represent the color of the Object.
     * @param x Float representing the x coordinate of the Bird
     * @param y Float representing the y coordinate of the Bird
     * @param heading Integer direction in degrees of where the
     *                Bird is travelling.
     * @param speed Integer to represent the Speed of the Bird
     */
    public Bird(int size, int color, float x, float y, int heading, int speed, String filename){
        super(size, color, x, y, heading, speed);
        try{
            birdImage = Image.createImage(filename);
        }catch(IOException e){e.printStackTrace();}
    }

    /**
     * Implements the logic for the calling object to move around the
     * map.
     */
    @Override
    public void move() {
        Random rand = new Random();
        int upperBoundPosNeg = 2;
        int upperBoundHeading = 6;
        int int_random = rand.nextInt(upperBoundPosNeg);

        if(int_random == 1){
            this.setHeading(getHeading() + rand.nextInt(upperBoundHeading));
        }else{
            this.setHeading(getHeading() - rand.nextInt(upperBoundHeading));
        }

        super.move();
    }

    /**
     * Setter for Bird to stop color changing.
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
        return super.toString();
    }

    /**
     * Draws the Bird GameObject in the MapView
     * @param g Graphics component
     * @param containerOrigin MapView Origin
     */
    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(this.getColor());
        if (birdImage != null)
            g.drawImage(birdImage.rotate(getHeading() - 90),
                    (int) (containerOrigin.getX() + this.getXLocation() - this.getSize()),
                    (int) (containerOrigin.getY() + this.getYLocation() - this.getSize()));
        else {
            g.drawArc((int) (containerOrigin.getX() + this.getXLocation() - this.getSize()),
                    (int) (containerOrigin.getY() + this.getYLocation() - this.getSize()),
                    2 * this.getSize(),
                    2 * this.getSize(),
                    0,
                    360);
            g.drawLine((int) (containerOrigin.getX() + this.getXLocation()),
                    (int) (containerOrigin.getY() + this.getYLocation()),
                    (int) (containerOrigin.getX() + this.getXLocation() - 2 * getSize() * Math.cos(Math.toRadians(90 + getHeading()))),
                    (int) (containerOrigin.getY() + this.getYLocation() - 2 * getSize() * Math.sin(Math.toRadians(90 + getHeading()))));
        }
    }

    @Override
    public boolean collidesWith(GameObject otherObject) {
        return false;
    }

    @Override
    public void handleCollision(GameObject otherObject) { }
}