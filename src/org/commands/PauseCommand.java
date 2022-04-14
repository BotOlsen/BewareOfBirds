package org.csc133.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.Game;
import org.csc133.a3.GameClockComponent;

/**
 * Toggle for the pause button of the game
 */
public class PauseCommand extends Command {

    private Game gameForm;
    private boolean paused = false;

    public PauseCommand(Game gameForm){
        super("Pause");
        this.gameForm = gameForm;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(paused) {
            paused = false;
            GameClockComponent.startElapsedTime();
        }else{
            paused = true;
            GameClockComponent.stopElapsedTime();
        }
        gameForm.togglePause(paused);
    }
}