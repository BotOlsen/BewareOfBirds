package org.csc133.a3;

/**
 * Logic for a GameWorld Singleton
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import java.util.ArrayList;
import java.util.Random;

public class GameWorld{

    private static boolean glassCockPitChange;
    private static int lives = 3;
    private static int lastSkyscraper;
    private static GameObjectCollection gameObjectCollection;
    private static double height;
    private static double width;
    private static Sound respawnSound = null;
    private static Sound birdCollisionSound = null;
    private static Sound helicopterCrashSound = null;
    private static Sound refuelingBlimpCollisionSound = null;
    private MapView mapView;
    private GlassCockpit glassCockpit;
    private static boolean soundFlag = true;


    public GameWorld(){
        gameObjectCollection = new GameObjectCollection();
        glassCockPitChange = false;
    }

    /**
     * Initializes all the game objects of the GameWorld
     */
    public void init(){
        Random rand = new Random();
        int verticalOffset;
        int horizontalOffset;
        int numOfSkyScraper = 4 + rand.nextInt(5);
        int numOfBirds = 2 + rand.nextInt(4);
        int numOfRefuelingBlimps = 2 + rand.nextInt(3);
        int numOfNPH = 3 + rand.nextInt(2);
        int randBirdSize;

        lastSkyscraper = numOfSkyScraper;

        //Generate the SkyScrapers and put them in the GameObject ArrayList. Only random generated locations till level system is developed.
        for(int sequenceNumber = 0; sequenceNumber < numOfSkyScraper; sequenceNumber++){
            verticalOffset = rand.nextInt(500) - 150;
            horizontalOffset = rand.nextInt(350) - 175;
            float locationX = (float)Math.min(width - sequenceNumber*(width/numOfSkyScraper) + horizontalOffset, width - 50);
            float locationY = (float)Math.min(height - sequenceNumber*(height/numOfSkyScraper) + verticalOffset, height - 100);
            gameObjectCollection.add(new SkyScraper(40 , ColorUtil.rgb(3, 252, 252), locationX, locationY, sequenceNumber + 1, "/SkyScraper.png"));
        }

        //Create the player helicopter
        PlayerHelicopter playerHelicopter = PlayerHelicopter.getInstance(40, ColorUtil.rgb(0, 0, 0),
                gameObjectCollection.getSkyScrapers().get(0).getXLocation(),
                gameObjectCollection.getSkyScrapers().get(0).getYLocation(), 0,
                0, 0, 1500, 3, 0, 1, "/PlayerHelicopter.png");
        gameObjectCollection.add(playerHelicopter);

        //Generate the Birds and put them in the GameObject ArrayList
        for(int birdNumber = 0; birdNumber < numOfBirds; birdNumber++){
            randBirdSize = (rand.nextInt(4) + 10);
            float locationX = rand.nextInt((int)(width - 4) + 4);
            float locationY = rand.nextInt((int)(height - 4) + 4);
            int heading = rand.nextInt(360);
            int speed = rand.nextInt(5) + 2;
            gameObjectCollection.add(new Bird(randBirdSize, ColorUtil.rgb(138, 101, 0), locationX, locationY, heading, speed, "/Bird.png"));
        }

        //Generate the RefuelingBlimps and put them in the GameObject ArrayList
        for(int blimpNumber = 0; blimpNumber < numOfRefuelingBlimps; blimpNumber++){
            int blimpSize = (rand.nextInt(30) + 30);
            float locationX = rand.nextInt((int)(width - blimpSize) + blimpSize);
            float locationY = rand.nextInt((int)(height - blimpSize) + blimpSize);
            gameObjectCollection.add(new RefuelingBlimp( blimpSize, ColorUtil.rgb(0, 255, 21), locationX, locationY));
        }

        //Generate the NPHelicopters and put them in the GameObject ArrayList
        for(int nphNumber = 0; nphNumber < numOfNPH; nphNumber++){
            float locationX = rand.nextInt((int)getWidth());
            float locationY = rand.nextInt((int)getHeight());
            gameObjectCollection.add(new NPHelicopter(30, ColorUtil.rgb(158, 109, 176), locationX, locationY, 0,
                    3, 0, 5000, 2, 0, 0, randomStrategy(), "/NPH.png"));
        }
    }

    /**
     * Getter for the height of the GameWorld
     * @return height of GameWorld Singleton
     */
    public static double getHeight(){
        return height;
    }


    /**
     * Adds the mapview and glasscockpit observers to
     * the gameworld.
     * @param mv MapView observer
     * @param gcp GlassCockPit observer
     */
    public void addObservers(MapView mv, GlassCockpit gcp){
        this.glassCockpit = gcp;
        this.mapView = mv;
    }

    /**
     * Notifies the Observers of gameworld they need
     * to update
     */
    private void notifyObservers(){
        mapView.update();
        if(glassCockPitChange)
            glassCockpit.update();
    }

    /**
     * Getter for the width of the GameWorld
     * @return width of GameWorld Singleton
     */
    public static double getWidth(){
        return width;
    }

    /**
     * Getter for the lives of the GameWorld
     * @return lives of GameWorld Singleton
     */
    public static int getLives() {
        return lives;
    }

    /**
     * Accelerates the player Helicopter by 1 unit of speed
     * if under the max speed allowed.
     */
    public void accelerate(){
        System.out.println("Player Accelerated");
        PlayerHelicopter playerHelicopter = gameObjectCollection.getPlayerHelicopter();
        playerHelicopter.setSpeed(playerHelicopter.getSpeed() + 1);
    }

    /**
     * Decelerates the player Helicopter by 1 unit of speed
     */
    public void brake(){
        System.out.println("Player Braked");
        PlayerHelicopter playerHelicopter = gameObjectCollection.getPlayerHelicopter();
        int currentSpeed = playerHelicopter.getSpeed();
        if(currentSpeed > 0){
            playerHelicopter.setSpeed(currentSpeed - 1);
        }
    }

    /**
     * Adjusts the stick angle of the player Helicopter
     * 5 degrees in the negative direction.
     */
    public void stickAngleLeft(){
        System.out.println("Stick Angle: -5");
        PlayerHelicopter playerHelicopter = gameObjectCollection.getPlayerHelicopter();
        playerHelicopter.setStickAngle(playerHelicopter.getStickAngle() - 5);
    }

    /**
     * Adjusts the stick angle of the player Helicopter
     * 5 degrees in the positive direction.
     */
    public void stickAngleRight() {
        System.out.println("Stick Angle: +5");
        PlayerHelicopter playerHelicopter = gameObjectCollection.getPlayerHelicopter();
        playerHelicopter.setStickAngle(playerHelicopter.getStickAngle() + 5);
    }

    /**
     * Pretends a collision occurred between two Helicopters
     */
    public static void helicopterCollision(Helicopter heli1, Helicopter heli2) {
        System.out.println("Ouch!!! Two Helicopters Collided");
        if(helicopterCrashSound == null)
            helicopterCrashSound = new Sound("CrashSound.wav", 25);
        if(soundFlag)
            helicopterCrashSound.play();
        int heli1DamageLevel = heli1.getDamageLevel();
        int heli2DamageLevel = heli2.getDamageLevel();
        int helicopterDamageConstant = 10;

        int newHeli1DamageLevel = heli1DamageLevel + helicopterDamageConstant;
        int newHeli2DamageLevel = heli2DamageLevel + helicopterDamageConstant;
        int currentLives = GameWorld.getLives();

        if (heli1 instanceof PlayerHelicopter) {
            if(newHeli2DamageLevel >= heli2.getMaxDamageAllowed()){
                newHeli2DamageLevel = 100;
                heli2.setSpeed(0);
            }
            heli2.setDamageLevel(newHeli2DamageLevel);
            heli2.setColor(255 - heli2.getDamageLevel() * 255 / 100, 0, 255 - heli2.getDamageLevel() * 255 / 100);

            if(newHeli1DamageLevel >= heli1.getMaxDamageAllowed()){
                GameWorld.setLives(currentLives - 1);
                if(lives < 0) {
                    System.out.println("Out of Lives!!!");
                    Display.getInstance().exitApplication();
                }
                System.out.println("\nHelicopter Died: Respawning at 1st Skyscraper!");
                resetPlayerHelicopter();
            }else {
                heli1.setDamageLevel(newHeli1DamageLevel);
                heli1.setColor(newHeli1DamageLevel * 255 / 100, 0, 0);
            }

        } else if (heli2 instanceof PlayerHelicopter){
            if(newHeli1DamageLevel >= heli1.getMaxDamageAllowed()){
                newHeli1DamageLevel = 100;
                heli1.setSpeed(0);
            }
            heli1.setDamageLevel(newHeli1DamageLevel);
            heli1.setColor(255 - heli1.getDamageLevel() * 255 / 100, 0, 255 - heli1.getDamageLevel() * 255 / 100);

            if(newHeli2DamageLevel >= heli2.getMaxDamageAllowed()){
                GameWorld.setLives(currentLives - 1);
                if(lives < 0) {
                    System.out.println("Out of Lives!!!");
                    Display.getInstance().exitApplication();
                }
                System.out.println("\nHelicopter Died: Respawning at 1st Skyscraper!");
                resetPlayerHelicopter();
            }else {
                heli2.setDamageLevel(newHeli2DamageLevel);
                heli2.setColor(newHeli2DamageLevel * 255 / 100, 0, 0);
            }

        }else{
            if(newHeli1DamageLevel >= heli1.getMaxDamageAllowed()){
                newHeli1DamageLevel = 100;
                heli1.setSpeed(0);
            }
            heli1.setDamageLevel(newHeli1DamageLevel);
            heli1.setColor(255 - heli1.getDamageLevel() * 255 / 100, 0, 255 - heli1.getDamageLevel() * 255 / 100);

            if(newHeli2DamageLevel >= heli2.getMaxDamageAllowed()){
                newHeli2DamageLevel = 100;
                heli2.setSpeed(0);
            }
            heli2.setDamageLevel(newHeli2DamageLevel);
            heli2.setColor(255 - heli2.getDamageLevel() * 255 / 100, 0, 255 - heli2.getDamageLevel() * 255 / 100);
        }

        glassCockPitChange = true;
    }

    /**
     * Mutator for the number of lives the player has.
     * @param i Integer representing number of lives player has.
     */
    protected static void setLives(int i) {
        lives = i;
        glassCockPitChange = true;
    }

    /**
     * Pretends a collision occurred between the player helicopter
     * and a Bird
     */
    public static void birdCollision(Helicopter heli){
        System.out.println("Goony Bird was hit. Unfortunately they're invincible. Oh no.");
        if(birdCollisionSound == null)
            birdCollisionSound = new Sound("BirdSound.wav", 25);
        if(soundFlag)
            birdCollisionSound.play();
        int currentDamageLevel = heli.getDamageLevel();
        int birdDamageConstant = 5;
        int newDamageLevel = currentDamageLevel + birdDamageConstant;

        if(heli instanceof PlayerHelicopter){
            int currentLives = GameWorld.getLives();
            if(newDamageLevel >= heli.getMaxDamageAllowed()){
                System.out.println("\nHelicopter Died: Respawning at 1st Skyscraper!");
                resetPlayerHelicopter();
                GameWorld.lives = currentLives - 1;
                if(lives < 0)
                    Display.getInstance().exitApplication();
            }else {
                heli.setDamageLevel(newDamageLevel);
                heli.setColor(newDamageLevel * 255 / 100, 0, 0);
            }
            glassCockPitChange = true;
        }else{
            if(newDamageLevel >= heli.getMaxDamageAllowed()){
                newDamageLevel = 100;
                heli.setSpeed(0);
            }
            heli.setDamageLevel(newDamageLevel);
            heli.setColor(255 - heli.getDamageLevel() * 255 / 100, 0, 255 - heli.getDamageLevel() * 255 / 100);
        }
    }

    /**
     * Pretends a collision occurred between the player helicopter
     * and a RefuelingBlimp
     */
    public static void refuelingBlimpCollision(Helicopter heli, RefuelingBlimp refuelingBlimp){
        System.out.println("A helicopter just got some fuel!");
        if(refuelingBlimpCollisionSound == null)
            refuelingBlimpCollisionSound = new Sound("RefuelSound.wav", 25);
        if(heli.getFuelLevel() >= heli.getMaxFuelLevel()) {
            System.out.println("Fuel already Full!");
            return;
        }
        if(refuelingBlimp.getCapacity() != 0){
            if(soundFlag)
                refuelingBlimpCollisionSound.play();
            addBlimpToGameObjectCollection();
            heli.setFuelLevel(heli.getFuelLevel() + refuelingBlimp.getCapacity());
            refuelingBlimp.setCapacity(0);
            refuelingBlimp.setColor(173, 255, 180);
        }

        glassCockPitChange = true;
    }

    /**
     * Pretends a collision occurred between the player helicopter
     * and the Skyscraper
     */
    public static void skyScraperCollision(Helicopter heli, SkyScraper skyScraper){


        int newSkyScraper = skyScraper.getSequenceNumber();
        if(heli.getLastSkyScraperReached() == newSkyScraper - 1) {
            if(heli instanceof PlayerHelicopter)
                skyScraper.setCheckpointReached();
            System.out.println("Someone just reached Skyscraper: " + newSkyScraper);
            heli.setLastSkyScraperReached(newSkyScraper);
        }
        if(heli instanceof PlayerHelicopter){
            glassCockPitChange = true;
            if(lastSkyscraper == heli.getLastSkyScraperReached()) {
                System.out.println("You won!!!");
                System.exit(0);
            }
        }else{
            if(lastSkyscraper == heli.getLastSkyScraperReached()){
                System.out.println("You Lost!!!");
                System.exit(0);
            }
        }

    }

    /**
     * Calls the program to exit with code 0
     */
    public void playerExit(){
        boolean exitFlag = Dialog.show("Quit", "Are you sure you want to quit?","Yup", "Nope");
        if(exitFlag){
            Display.getInstance().exitApplication();
        }
    }

    /**
     * Acts as a time clock in the game.
     * Does this by: steering the helicopter
     *               Move the helicopter
     *               Reduce Fuel Level of Helicopter
     *               Move all other Moveable Objects
     *               Increments clock count variable
     */
    public void clockTick(){
        PlayerHelicopter playerHelicopter = gameObjectCollection.getPlayerHelicopter();
        int currentLives = getLives();
        playerHelicopter.steer(playerHelicopter.getStickAngle());
        playerHelicopter.burnFuel();



        if(playerHelicopter.getFuelLevel() > 0){
            playerHelicopter.move();
        }else{
            resetPlayerHelicopter();
            lives = currentLives - 1;
            if(lives < 0)
                Display.getInstance().exitApplication();
            System.out.println("Out of Fuel!");
        }
        for(GameObject obj : gameObjectCollection){
            if(obj instanceof Moveable){
                if(obj instanceof NPHelicopter) {
                    ((NPHelicopter) obj).invokeStrategy(this.getGameObjectCollection());
                    ((NPHelicopter) obj).steer(((NPHelicopter) obj).getStickAngle());
                }
                ((Moveable) obj).move();
            }
        }

        for(GameObject object1: gameObjectCollection){
            if(object1 instanceof Bird || object1 instanceof SkyScraper || object1 instanceof RefuelingBlimp)
                continue;
            for (GameObject object2 : gameObjectCollection){
                if(!object1.equals(object2)) {
                    if(object1.collidesWith(object2))
                        object1.handleCollision(object2);
                }
            }
        }
        notifyObservers();
    }


    /**
     * Gets called on player helicopter death to
     * respawn a new one at the first Skyscraper.
     */
    private static void resetPlayerHelicopter(){
        System.out.println("Respawned at first Skyscraper");
        if(respawnSound == null)
            respawnSound = new Sound("RespawnSound.wav", 25);
        if(soundFlag)
            respawnSound.play();
        PlayerHelicopter playerHelicopter= gameObjectCollection.getPlayerHelicopter();
        playerHelicopter.setColor(0,0,0);
        playerHelicopter.setDamageLevel(0);
        playerHelicopter.setFuelLevel(2500);
        playerHelicopter.setLocation(gameObjectCollection.getSkyScrapers().get(0).getXLocation(),
                gameObjectCollection.getSkyScrapers().get(0).getYLocation());
        playerHelicopter.setHeading(0);
        playerHelicopter.setSpeed(0);
        glassCockPitChange = true;
    }

    /**
     * Gets called when a strategy change is requested.
     */
    public void strategyChange() {
        System.out.println("Strategy Changed");
        ArrayList<NPHelicopter> npHelicopterArrayList= gameObjectCollection.getNPHelicopters();
        for(NPHelicopter nph : npHelicopterArrayList){
            nph.setStrategy(randomStrategy());
        }
    }


    /**
     * Gets called to return a random strategy
     * @return IStrategy
     */
    private IStrategy randomStrategy(){
        Random rand = new Random();
        if(rand.nextBoolean()){
            return new AttackStrategy();
        }else{
            return new VisitSkyscraperStrategy();
        }
    }


    /**
     * Gets called to display about information
     */
    public void about() {
        Dialog.show("About", "Daniel Olsen\nCSC 133\nVersion 3.0\n" +
                "Sound made by Eric Matyas, www.soundimage.org\n" +
                "Background Sound licensed by Adobe","Ok", null);
    }

    /**
     * Gets called to display Command help
     */
    public void help() {
        Dialog.show("Help", "'a' - Accelerate command\n" +
                "'b' - Break command\n" +
                "'l' - Left Turn 5 Degrees\n" +
                "'r' - Right Turn 5 Degrees\n" +
                "'x' - Exit Game", "Ok", null);
    }

    /**
     * Gets called to Toggle Sound
     */
    public void toggleSound(boolean flag){
        soundFlag = flag;
    }

    /**
     * Accessor for the GameObjectColleciton held
     * in storage.
     */
    public static GameObjectCollection getGameObjectCollection() {
        return gameObjectCollection;
    }

    /**
     * Sets size of the gameworld with respect to the mapview
     * @param x Width of MapView
     * @param y Height of MapView
     */
    public static void setSize(int x , int y){
        height = y;
        width = x;
    }

    private static void addBlimpToGameObjectCollection(){
        Random rand = new Random();
        int blimpSize = (rand.nextInt(30) + 30);
        float locationX = rand.nextInt((int)(width - blimpSize) + blimpSize);
        float locationY = rand.nextInt((int)(height - blimpSize) + blimpSize);
        gameObjectCollection.add(new RefuelingBlimp( blimpSize, ColorUtil.rgb(0, 255, 21), locationX, locationY));
    }
}