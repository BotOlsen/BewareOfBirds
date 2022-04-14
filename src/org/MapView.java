package org.csc133.a3;

/** Represents a MapView
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class MapView extends Container implements IObserver{

    private GameObjectCollection gameObjectCollection;

    /**
     * Constructor for MapView.
     * @param gw GameWorld Object to be used so accessing GameObjects is possible.
     */
    public MapView(GameWorld gw){
        gameObjectCollection = gw.getGameObjectCollection();
    }

    /**
     * {@inheritDoc}
     */
    public void paint(Graphics g){
        for(GameObject obj : gameObjectCollection){
            obj.draw(g, new Point(this.getX(), this.getY()));
        }
    }

    /**
     * Calls the repaint method to update
     * location of all objects in the MapView
     */
    public void update(){
        repaint();
    }

}
