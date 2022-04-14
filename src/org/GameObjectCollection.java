package org.csc133.a3;

/**
 * Collection to store GameObjects
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */


import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameObjectCollection extends CopyOnWriteArrayList<GameObject> {

    /**
     * Constructs the empty GameObjectCollection
     */
    public GameObjectCollection(){

    }

    /**
     * @return ArrayList of NPHelicopters
     */
    public ArrayList<NPHelicopter> getNPHelicopters(){
        ArrayList<NPHelicopter> temp = new ArrayList();
        for(GameObject obj : this){
            if(obj instanceof NPHelicopter)
                temp.add((NPHelicopter) obj);
        }
        return temp;
    }

    /**
     * @return ArrayList of SkyScraper
     */
    public ArrayList<SkyScraper> getSkyScrapers(){
        ArrayList<SkyScraper> temp = new ArrayList();
        for(GameObject obj : this){
            if(obj instanceof SkyScraper)
                temp.add((SkyScraper) obj);
        }
        return temp;
    }

    /**
     * @return ArrayList of Bird
     */
    public ArrayList<Bird> getBirds(){
        ArrayList<Bird> temp = new ArrayList();
        for(GameObject obj : this){
            if(obj instanceof Bird)
                temp.add((Bird) obj);
        }
        return temp;
    }

    /**
     * @return ArrayList of RefuelingBlimp
     */
    public ArrayList<RefuelingBlimp> getRefuelingBlimps(){
        ArrayList<RefuelingBlimp> temp = new ArrayList();
        for(GameObject obj : this){
            if(obj instanceof RefuelingBlimp)
                temp.add((RefuelingBlimp) obj);
        }
        return temp;
    }

    /**
     * @return PlayerHelicopter
     */
    public PlayerHelicopter getPlayerHelicopter(){
        return PlayerHelicopter.getInstance();
    }

}
