package org.csc133.a3;

/** Represents a NPHelicopter GameObject.
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Rectangle;

import java.io.IOException;
import java.util.ArrayList;

public class NPHelicopter extends Helicopter implements IDrawable{

    private IStrategy currentStrategy;
    private Image nphImage;


    /**
     * Constructor for the NPHelicopter Object.
     * @param size Integer to represent the number of pixels of
     *             object Length on Screen.
     * @param color Integer to represent the color of the Object.
     * @param x Float representing the x coordinate of the Bird
     * @param y Float representing the y coordinate of the Bird
     * @param heading Integer direction in degrees of where the
     *                Helicopter is travelling.
     * @param speed Integer to represent the Speed of the Helicopter
     * @param stickAngle Integer direction in degrees to represent
     *                   stickAngle of the Helicopter.
     * @param fuelLevel Integer to represent the number of gallons
     *                  of fuel left in Helicopter Object.
     * @param fuelConsumptionRate Integer representing the number of
     *                            gallons the helicopter uses per
     *                            tick of time clock.
     * @param damageLevel Integer to represent the damageLevel of the
     *                    Helicopter Object.
     * @param lastSkyScraperReached Integer to represent the Skyscraper
     *                              number of the lest visited Skyscraper
     */
    public NPHelicopter(int size, int color, float x, float y, int heading, int speed,
                        int stickAngle, int fuelLevel, int fuelConsumptionRate,
                        int damageLevel, int lastSkyScraperReached, IStrategy randStrat, String filename){
        super(size, color, x, y, heading, speed, stickAngle, fuelLevel,
                fuelConsumptionRate, damageLevel, lastSkyScraperReached);
        currentStrategy = randStrat;
        try{
            nphImage = Image.createImage(filename);
        }catch(IOException e){e.printStackTrace();}
    }


    /**
     * This causes the NPHelicopter to invoke the strategy and change it's heading
     * Called before the move method.
     * @param gameObjects Allows the targets to be accessed.
     */
    public void invokeStrategy(GameObjectCollection gameObjects){
        this.setStickAngle((int)(currentStrategy.executeStrategy(gameObjects, this)) - this.getHeading());
    }

    /**
     * Setter for the Strategy the NPHelicopter
     * can have.
     * @param strategy IStrategy that can take many different
     *                 strategy forms.
     */
    public void setStrategy(IStrategy strategy) {
        currentStrategy = strategy;
    }


    /**
     * Getter for NPHImage. Allows to access asset Size
     * @return Image
     */
    public Image getNphImage(){
        return nphImage;
    }

    /**
     * Draws the NPHelicopter GameObject in the MapView
     * @param g Graphics component
     * @param containerOrigin MapView Origin
     */
    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(this.getColor());
        if(nphImage != null)
            g.drawImage(nphImage.rotate(getHeading()),
                    (int)(containerOrigin.getX() + this.getXLocation() - this.getSize()),
                    (int)(containerOrigin.getY() + this.getYLocation()  - this.getSize()));
        else {
            g.fillArc((int) (containerOrigin.getX() + this.getXLocation() - this.getSize()),
                    (int) (containerOrigin.getY() + this.getYLocation() - this.getSize()),
                    2 * this.getSize(),
                    2 * this.getSize(),
                    0,
                    360);
            g.drawLine((int)(containerOrigin.getX() + this.getXLocation()),
                    (int)(containerOrigin.getY() + this.getYLocation()),
                    (int)(containerOrigin.getX() + this.getXLocation() - 2*getSize()*Math.cos(Math.toRadians(90 + getHeading()))),
                    (int)(containerOrigin.getY() + this.getYLocation() - 2*getSize()*Math.sin(Math.toRadians(90 + getHeading()))));
        }
    }

    /**
     * Returns a boolean if there was a collision between two GameObejcts
     * @param otherObject Game Object to check for collision with
     * @return Boolean True if new collision
     */
    @Override
    public boolean collidesWith(GameObject otherObject) {
        int otherObjectSize = otherObject.getSize();
        float otherObjectLocX = otherObject.getXLocation();
        float otherObjectLocY = otherObject.getYLocation();
        boolean newCollision = false;

        if(otherObject instanceof Bird || otherObject instanceof Helicopter){
            if((this.nphImage.getWidth()/2 + (otherObject instanceof Bird ? otherObjectSize : this.nphImage.getWidth()/2)) >= (Math.sqrt(Math.pow(this.getXLocation() - otherObjectLocX ,2) + Math.pow( this.getYLocation() - otherObjectLocY, 2)))){
                if(!this.getCollisionVector().contains(otherObject)) {
                    this.getCollisionVector().add(otherObject);
                    otherObject.getCollisionVector().add(this);
                    newCollision = true;
                }
            }else{
                if(this.getCollisionVector().contains(otherObject)){
                    this.getCollisionVector().remove(otherObject);
                    otherObject.getCollisionVector().remove(this);
                }
            }
        }
        else if (otherObject instanceof RefuelingBlimp){
            int otherObjectHeight = ((RefuelingBlimp) otherObject).getFullBLimp().getHeight();
            int otherObjectWidth = ((RefuelingBlimp) otherObject).getFullBLimp().getWidth();
            Rectangle rect1 = new Rectangle((int)this.getXLocation(), (int)this.getYLocation(), this.getSize()*2, this.getSize() * 2);
            Rectangle rect2 = new Rectangle((int)otherObjectLocX - ((RefuelingBlimp) otherObject).getFullBLimp().getWidth()/2,
                    (int)otherObjectLocY - ((RefuelingBlimp) otherObject).getFullBLimp().getHeight()/2,
                    otherObjectWidth, otherObjectHeight);
            if(rect1.intersects(rect2)){
                if(!this.getCollisionVector().contains(otherObject)) {
                    this.getCollisionVector().add(otherObject);
                    otherObject.getCollisionVector().add(this);
                    newCollision = true;
                }
            }else{
                if(this.getCollisionVector().contains(otherObject)){
                    this.getCollisionVector().remove(otherObject);
                    otherObject.getCollisionVector().remove(this);
                }
            }
        }
        else if (otherObject instanceof SkyScraper){
            int otherObjectHeight = ((SkyScraper) otherObject).getSkyscraperImage().getHeight();
            int otherObjectWidth = ((SkyScraper) otherObject).getSkyscraperImage().getWidth();
            Rectangle rect1 = new Rectangle((int)this.getXLocation(), (int)this.getYLocation(), this.getSize(), this.getSize());
            Rectangle rect2 = new Rectangle((int)otherObjectLocX - ((SkyScraper) otherObject).getSkyscraperImage().getWidth()/2,
                    (int)otherObjectLocY - ((SkyScraper) otherObject).getSkyscraperImage().getHeight()/2,
                    otherObjectWidth, otherObjectHeight);
            if(rect1.intersects(rect2)){
                if(!this.getCollisionVector().contains(otherObject)) {
                    this.getCollisionVector().add(otherObject);
                    otherObject.getCollisionVector().add(this);
                    newCollision = true;
                }
            }else{
                if(this.getCollisionVector().contains(otherObject)){
                    this.getCollisionVector().remove(otherObject);
                    otherObject.getCollisionVector().remove(this);
                }
            }
        }
        return newCollision;
    }

    /**
     * If there is a collision with the parameter. Collision function in gameworld is called
     * @param otherObject GameObject
     */
    @Override
    public void handleCollision(GameObject otherObject) {
        if(otherObject instanceof Bird)
            GameWorld.birdCollision(this);
        else if (otherObject instanceof Helicopter)
            GameWorld.helicopterCollision(this, (Helicopter)otherObject);
        else if (otherObject instanceof RefuelingBlimp) {
            GameWorld.refuelingBlimpCollision(this, (RefuelingBlimp) otherObject);
        }
        else if (otherObject instanceof SkyScraper)
            GameWorld.skyScraperCollision(this, (SkyScraper) otherObject);
    }
}
