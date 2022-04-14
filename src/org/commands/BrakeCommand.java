package org.csc133.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.GameWorld;


/**
 * Decelerates the player Helicopter by 1 unit of speed
 */
public class BrakeCommand extends Command {
    private GameWorld targetGW;
    private boolean paused = false;

    public BrakeCommand(GameWorld gw) {
        super("Brake");
        targetGW = gw;
    }

    @Override
    public void actionPerformed(ActionEvent evt){
        if(!paused)
            targetGW.brake();
    }

    public void pause(){
        paused = true;
    }

    public void unpause(){
        paused = false;
    }
}
