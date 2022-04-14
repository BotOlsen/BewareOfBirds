package org.csc133.a3;

/**
 * Represents Visit Skyscraper Stategy
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.util.MathUtil;

import java.util.ArrayList;

public class VisitSkyscraperStrategy implements IStrategy{

    private double gameWidth = GameWorld.getWidth();
    private double gameHeight = GameWorld.getHeight();
    private boolean reachedSkyscraper = false;
    private boolean reachedCenter = false;
    /**
     * Allows implementing classes to execute a strategy
     * @param gameObjects GameObjectCollection to access targets
     * @param self  GameObject Executing the strategy
     * @return Heading for NPH to move towards
     */
    @Override
    public double executeStrategy(GameObjectCollection gameObjects, GameObject self) {
        float targetX;
        float targetY;
        SkyScraper target = null;

        ArrayList<SkyScraper> skyScrapers = gameObjects.getSkyScrapers();
        for(SkyScraper skyScraper : skyScrapers)
            if(skyScraper.getSequenceNumber() == ((NPHelicopter) self).getLastSkyScraperReached() + 1)
                target = skyScraper;

        targetX = target.getXLocation();
        targetY = target.getYLocation();

        double stickAngle = Math.toDegrees(MathUtil.atan((self.getYLocation() - targetY)/(self.getXLocation() - targetX)));

        if(self.getXLocation() - targetX > 0 )
            return 270 + stickAngle;

        return 90 + stickAngle;

    }
}
