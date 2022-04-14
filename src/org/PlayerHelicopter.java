package org.csc133.a3;

/** Represents a PlayerHelicopter GameObject.
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Rectangle;
import java.io.IOException;


public class PlayerHelicopter extends Helicopter{
    private static PlayerHelicopter single_instance = null;
    private static Image playerHelicopterImage;


    private PlayerHelicopter(int size, int color, float x, float y, int heading, int speed,
                             int stickAngle, int fuelLevel, int fuelConsumptionRate,
                             int damageLevel, int lastSkyScraperReached){
        super(size, color, x, y, heading, speed, stickAngle, fuelLevel,
                fuelConsumptionRate, damageLevel, lastSkyScraperReached);
    }

    /**
     * Determines if the PlayerHelicopter singleton has been created;
     * if not, it creates the PlayerHelicopter Object
     * @return Singleton of PlayerHelicopter
     */
    public static PlayerHelicopter getInstance(int size, int color, float x, float y, int heading, int speed,
                                               int stickAngle, int fuelLevel, int fuelConsumptionRate,
                                               int damageLevel, int lastSkyScraperReached, String filename){
        if(single_instance == null) {
            single_instance = new PlayerHelicopter(size, color, x, y, heading, speed, stickAngle, fuelLevel,
                    fuelConsumptionRate, damageLevel, lastSkyScraperReached);
            try{
                playerHelicopterImage = Image.createImage(filename);
            }catch(IOException e){e.printStackTrace();}
        }
        return single_instance;
    }

    /**
     * Determines if the PlayerHelicopter singleton has been created;
     * if not, it creates the PlayerHelicopter Object
     * @return Singleton of PlayerHelicopter
     */
    public static PlayerHelicopter getInstance(){
        return single_instance;
    }

    /**
     * Draws the PlayerHelicopter GameObject in the MapView
     * @param g Graphics component
     * @param containerOrigin MapView Origin
     */
    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(this.getColor());
        if(playerHelicopterImage != null)
            g.drawImage(playerHelicopterImage.rotate(getHeading()),
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
            if((this.playerHelicopterImage.getWidth()/2 + (otherObject instanceof Bird ? otherObjectSize : ((NPHelicopter) otherObject).getNphImage().getWidth()/2)) >= (Math.sqrt(Math.pow(this.getXLocation() - otherObjectLocX ,2) + Math.pow( this.getYLocation() - otherObjectLocY, 2)))){
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
            Rectangle rect1 = new Rectangle((int)this.getXLocation(), (int)this.getYLocation(), this.getSize()*2, this.getSize() * 2);
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
