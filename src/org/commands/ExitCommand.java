package org.csc133.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.Game;
import org.csc133.a3.GameClockComponent;
import org.csc133.a3.GameWorld;

public class ExitCommand extends Command {

    private GameWorld targetGW;
    private Game game;

    public ExitCommand(GameWorld gw, Game game){
        super("Exit");
        targetGW = gw;
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(!game.getPausedFlag())
            GameClockComponent.stopElapsedTime();
        targetGW.playerExit();
        if(!game.getPausedFlag())
            GameClockComponent.startElapsedTime();
    }
}
