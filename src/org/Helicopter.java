package org.csc133.a3;

/** Represents a Helicopter GameObject.
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

public abstract class Helicopter extends Moveable implements ISteerable{
    private int stickAngle;
    private final int maximumSpeed = 10;
    private int fuelLevel;
    private int fuelConsumptionRate;
    private int damageLevel;
    private int lastSkyScraperReached;
    private final int maxDamageAllowed = 100;
    private final int maxStickAngle = 40;
    private final int maxFuelLevel = 9999;

    /**
     * Constructor for the Helicopter Object.
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
    public Helicopter(int size, int color, float x, float y, int heading, int speed,
                      int stickAngle, int fuelLevel, int fuelConsumptionRate,
                      int damageLevel, int lastSkyScraperReached){
        super(size, color, x, y, heading, speed);
        this.stickAngle = stickAngle;
        this.fuelLevel = fuelLevel;
        this.fuelConsumptionRate = fuelConsumptionRate;
        this.damageLevel = damageLevel;
        this.lastSkyScraperReached = lastSkyScraperReached;
    }

    /**
     * Getter for the maximumSpeed for the helicopters
     * @return maximumSpeed of the GameObject Helicopter
     */
    public int getMaximumSpeed(){
        return maximumSpeed;
    }

    /**
     * Getter for the maxDamageAllowed for the helicopters
     * @return maxDamageAllowed of the GameObject Helicopter
     */
    public int getMaxDamageAllowed(){
        return maxDamageAllowed;
    }

    /**
     * Getter for the maxStickAngle for the helicopters
     * @return maxStickAngle of the GameObject Helicopter
     */
    public int getMaxStickAngle() {
        return maxStickAngle;
    }

    /**
     * Getter for the stickAngle for the helicopters
     * @return stickAngle of the GameObject Helicopter
     */
    public int getStickAngle() {
        return stickAngle;
    }

    /**
     * Setter for the stickAngle variable of the Helicopter Object
     * @param newStickAngle Integer to assign to Helicopter's stickAngle
     */
    public void setStickAngle(int newStickAngle){
        if(Math.abs(newStickAngle) <= this.getMaxStickAngle()){
            this.stickAngle = newStickAngle;
        }else if(newStickAngle > 40)
            if(newStickAngle < 180)
                this.stickAngle = 40;
            else
                this.stickAngle = -40;
        else
            if(newStickAngle < -180)
                this.stickAngle = 40;
            else
                this.stickAngle = -40;
    }

    /**
     * Getter for the fuelLevel for the helicopters
     * @return fuelLevel of the GameObject Helicopter
     */
    public int getFuelLevel() {
        return fuelLevel;
    }

    /**
     * Setter for the fuelLevel variable of the Helicopter Object
     * @param fuelLevel Integer to assign to Helicopter's fuelLevel
     */
    public void setFuelLevel(int fuelLevel) {
        this.fuelLevel = Math.min(fuelLevel, maxFuelLevel);
    }

    /**
     * Causes Helicopter to Burn fuel
     */
    public void burnFuel(){
        this.setFuelLevel(this.getFuelLevel() - this.getFuelConsumptionRate());
    }

    /**
     * Getter for the fuelConsumptionRate for the helicopters
     * @return fuelConsumptionRate of the GameObject Helicopter
     */
    public int getFuelConsumptionRate() {
        return fuelConsumptionRate;
    }

    /**
     * Getter for the damageLevel for the helicopters
     * @return damageLevel of the GameObject Helicopter
     */
    public int getDamageLevel() {
        return this.damageLevel;
    }

    /**
     * Setter for the damageLevel variable of the Helicopter Object
     * @param damageLevel Integer to assign to Helicopter's damageLevel
     */
    protected void setDamageLevel(int damageLevel) {
        this.damageLevel = damageLevel;
    }

    /**
     * Getter for the maximum allowed fuel level for the helicopters
     * @return last Skyscraper reached for Helicopter Object
     */
    public int getLastSkyScraperReached() {
        return lastSkyScraperReached;
    }

    /**
     * Setter for the lastSkyScraper variable of the Helicopter Object
     * @param lastSkyScraperReached Integer representing the last Skyscraper
     *                              reached.
     */
    public void setLastSkyScraperReached(int lastSkyScraperReached) {
        this.lastSkyScraperReached = lastSkyScraperReached;
    }

    /**
     * Method that from the ISteerable interface that allows the
     * player helicopter to change it's heading with player input
     * @param degrees Integer to adjust the helicopter heading by.
     */
    public void steer(int degrees){
        int newHeading = getHeading() + Math.min(degrees, 5);
        if(newHeading > 360) {
            newHeading = newHeading - 360;
            setHeading(newHeading);
        }
        else if(newHeading < 0) {
            newHeading = newHeading + 360;
            setHeading(newHeading);
        }else{
            setHeading(newHeading);
        }
        if(this.getStickAngle() > 0)
            this.setStickAngle(degrees - 5);
        else if (this.getStickAngle() < 0){
            this.setStickAngle(degrees + 5);
        }
    }

    /**
     * Getter for the maximum allowed fuel level for the helicopters
     * @return fuel level of the GameObject Helicopter
     */
    public int getMaxFuelLevel(){
        return this.maxFuelLevel;
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
        return super.toString() + ", stickAngle=" + stickAngle +
                ", maximumSpeed=" + maximumSpeed +
                ", fuelLevel=" + fuelLevel +
                ", fuelConsumptionRate=" + fuelConsumptionRate +
                ", damageLevel=" + damageLevel +
                ", lastSkyScraperReached=" + lastSkyScraperReached +
                ", maxDamageAllowed=" + maxDamageAllowed +
                ", maxStickAngle=" + maxStickAngle +
                "   ";
    }
}