package org.csc133.a3;

/** Represents a Moveable GameObject.
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

public abstract class Moveable extends GameObject{
    private int heading;
    private int speed;

    /**
     * Constructor for the Moveable Object.
     * @param size Integer to represent the number of pixels of
     *             object Length on Screen.
     * @param color Integer to represent the color of the Object.
     * @param x X-Coordinate that represents holds the (x) location
     *              of the Object.
     * @param y Y-Coordinate that represents holds the (x) location
     *              of the Object
     * @param heading Integer direction in degrees of where the
     *                Moveable is travelling.
     * @param speed Integer to represent the Speed of the Moveable
     */
    public Moveable(int size, int color, float x, float y, int heading, int speed){
        super(size, color, x, y);
        this.heading = heading;
        this.speed = speed;
    }

    /**
     * Getter for the heading for the Moveable Object
     * @return heading of the Moveable GameObject
     */
    public int getHeading() {
        return heading;
    }

    /**
     * Setter for the heading variable of the Moveable Object
     * @param heading Integer to assign to Moveable GameObject
     */
    public void setHeading(int heading) {
        if(heading > 360)
            this.heading = heading - 360;
        else if (heading < 0)
            this.heading = heading + 360;
        else
            this.heading = heading;
    }

    /**
     * Getter for the speed for the Moveable Object
     * @return speed of the Moveable GameObject
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Setter for the speed variable of the Moveable Object
     * @param speed Integer to assign to Moveable GameObject
     */
    public void setSpeed(int speed) {
        if(speed <= 10)
            this.speed = speed;
        else
            this.speed = 10;
    }

    /**
     * Calculates adjusted speed with damage taken into account.
     * @return Integer representing Adjusted Speed
     */
    public int getAdjustedSpeed(){
        if(this instanceof PlayerHelicopter){
            return Math.max(this.getSpeed() * (100 - ((PlayerHelicopter) this).getDamageLevel())/100, 1);
        }else
            return this.getSpeed();
    }

    /**
     * Implements the logic for the calling object to move around the
     * map.
     */
    public void move(){
        double newLocationX, newLocationY;
        double theta = Math.toRadians(90 + this.getHeading());
        double deltaX = -1*Math.cos(theta)*this.getAdjustedSpeed();
        double deltaY = -1*Math.sin(theta)*this.getAdjustedSpeed();

        newLocationX = this.getXLocation() + deltaX;
        newLocationY = this.getYLocation() + deltaY;
        if(newLocationX > GameWorld.getWidth()) {
            newLocationX = GameWorld.getWidth();
            this.setHeading(360 - this.getHeading());
        } else if(newLocationX < 0) {
            newLocationX = 0;
            this.setHeading(360 - this.getHeading());
        }else if(newLocationY > GameWorld.getHeight()){
            newLocationY = GameWorld.getHeight();
            this.setHeading(180 - this.getHeading());
        }else if(newLocationY < 0){
            newLocationY = 0;
            this.setHeading(180 - this.getHeading());
        }

        this.setLocation((float)newLocationX, (float)newLocationY);
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
        return super.toString() +
                ", heading=" + heading +
                ", speed=" + speed;
    }
}