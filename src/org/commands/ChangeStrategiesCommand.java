package org.csc133.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.GameWorld;

public class ChangeStrategiesCommand extends Command {
    private GameWorld targetGW;

    public ChangeStrategiesCommand(GameWorld gw){
        super("Change Strategies");
        targetGW = gw;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        targetGW.strategyChange();
    }
}
