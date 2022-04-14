package org.csc133.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.GameWorld;
import org.csc133.a3.PlayerHelicopter;

/**
 * Adjusts the stick angle of the player Helicopter
 * 5 degrees in the negative direction.
 */
public class LeftCommand extends Command {
    private GameWorld targetGW;
    private boolean paused;

    public LeftCommand(GameWorld gw) {
        super("Left");
        targetGW = gw;
    }

    @Override
    public void actionPerformed(ActionEvent evt){
        if(!paused)
            targetGW.stickAngleLeft();
    }

    public void pause(){
        paused = true;
    }

    public void unpause(){
        paused = false;
    }
}
