package org.csc133.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.BGSound;
import org.csc133.a3.Game;
import org.csc133.a3.GameWorld;

/**
 * Toggle's the sound of the game
 */
public class ToggleSoundCommand extends Command {

    private GameWorld targetGW;
    private BGSound bgSound;
    private boolean soundFlag = true;
    private Game gameForm;

    public ToggleSoundCommand(GameWorld gw, BGSound bgSound, Game gameForm){
        super("");
        targetGW = gw;
        this.bgSound = bgSound;
        this.gameForm = gameForm;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(!gameForm.getPausedFlag()) {
            if(soundFlag == true)
                soundFlag = false;
            else
                soundFlag = true;
            targetGW.toggleSound(soundFlag);
            bgSound.toggleSound(soundFlag);
            System.out.println("Sound toggled");
        }

    }
}