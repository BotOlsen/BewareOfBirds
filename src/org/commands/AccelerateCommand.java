package org.csc133.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.GameWorld;


/**
 * Accelerates the player Helicopter by 1 unit of speed
 * if under the max speed allowed.
 */
public class AccelerateCommand extends Command {
    private GameWorld targetGW;
    private boolean paused = false;

    public AccelerateCommand(GameWorld gw) {
        super("Accelerate");
        this.targetGW = gw;
    }

    @Override
    public void actionPerformed(ActionEvent evt){
        if(!paused)
            targetGW.accelerate();
     }

    public void pause(){
        paused = true;
    }

    public void unpause(){
        paused = false;
    }
}

