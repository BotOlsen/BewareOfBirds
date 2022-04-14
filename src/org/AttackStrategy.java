package org.csc133.a3;

/**
 * Represents Attack Stategy
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */


import com.codename1.util.MathUtil;

public class AttackStrategy implements IStrategy{

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

        PlayerHelicopter target = gameObjects.getPlayerHelicopter();

        targetX = target.getXLocation();
        targetY = target.getYLocation();

        double newTempheading = Math.toDegrees(MathUtil.atan((self.getYLocation() - targetY)/(self.getXLocation() - targetX)));

        if(self.getXLocation() - targetX > 0 )
            return 270 + newTempheading;

        return 90 + newTempheading;

    }
}
