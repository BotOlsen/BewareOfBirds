package org.csc133.a3;

/** Represents a org.csc133.a3.Game
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import org.csc133.a3.commands.*;

import java.io.IOException;

public class Game extends Form implements Runnable{

    private GameWorld gw;
    private GlassCockpit glassCockpit;
    private SouthContainer southContainer;
    private Toolbar toolbar;
    private AboutCommand about;
    private AccelerateCommand accel;
    private BrakeCommand brakeCommand;
    private ChangeStrategiesCommand changeStrategiesCommand;
    private ExitCommand exitCommand;
    private HelpCommand helpCommand;
    private LeftCommand leftCommand;
    private RightCommand rightCommand;
    private MapView mapView;
    private ToggleSoundCommand toggleSoundCommand;
    private BGSound bgSound;
    private PauseCommand pauseCommand;
    private boolean paused;

    /**
     * Constructor for the org.csc133.a3.Game
     */
    public Game() {

        gw = new GameWorld();

        bgSound = new BGSound("Background.wav", 25);
        bgSound.play();

        createCommands();
        createToolbar();
        addKeyListeners();

        glassCockpit = new GlassCockpit(gw);
        Command[] buttonCommands = {leftCommand, accel, brakeCommand, rightCommand};
        southContainer = new SouthContainer(buttonCommands);

        mapView = new MapView(gw);

        this.setTitle("Avoid the Birds!!!");
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.NORTH, glassCockpit);
        this.add(BorderLayout.SOUTH, southContainer);
        this.add(BorderLayout.CENTER, mapView);

        this.show();

        GameWorld.setSize(mapView.getWidth(), mapView.getHeight());

        gw.init();
        gw.addObservers(mapView, glassCockpit);
    }

    private void createCommands(){
        about = new AboutCommand(gw, this);
        accel = new AccelerateCommand(gw);
        brakeCommand = new BrakeCommand(gw);
        changeStrategiesCommand = new ChangeStrategiesCommand(gw);
        exitCommand = new ExitCommand(gw, this);
        helpCommand = new HelpCommand(gw, this);
        leftCommand = new LeftCommand(gw);
        rightCommand = new RightCommand(gw);
        toggleSoundCommand = new ToggleSoundCommand(gw, bgSound, this);
        pauseCommand = new PauseCommand(this);
    }

    private void createToolbar(){
        toolbar = new Toolbar();
        setToolbar(toolbar);

        toolbar.removeCommand(this.toggleSoundCommand);
        toolbar.addComponentToLeftSideMenu(new Label("   "));
        toolbar.addCommandToRightBar(this.toggleSoundCommand);
        try {

            toolbar.addCommandToRightBar("", Image.createImage("/Headphone.png"), this.toggleSoundCommand);
            toolbar.addCommandToLeftBar("", Image.createImage("/PausePlay.png"), this.pauseCommand);
        }catch(IOException e){e.printStackTrace();}
        toolbar.addCommandToLeftSideMenu(this.changeStrategiesCommand);
        toolbar.addCommandToLeftSideMenu(this.about);
        toolbar.addCommandToLeftSideMenu(this.helpCommand);
        toolbar.addCommandToLeftSideMenu(this.exitCommand);

    }

    private void addKeyListeners(){
        this.addKeyListener('a',accel);
        this.addKeyListener('b',brakeCommand);
        this.addKeyListener('l',leftCommand);
        this.addKeyListener('r',rightCommand);
        this.addKeyListener('x', exitCommand);
        this.addKeyListener('5',accel);
        this.addKeyListener('2',brakeCommand);
        this.addKeyListener('1',leftCommand);
        this.addKeyListener('3',rightCommand);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     java.lang.Thread#run()
     */
    @Override
    public void run() {
        if(!paused)
            gw.clockTick();
    }

    public void togglePause(boolean paused) {
        this.paused = paused;
        if(paused) {
            bgSound.pause();
            accel.pause();
            brakeCommand.pause();
            leftCommand.pause();
            rightCommand.pause();
            return;
        }
        else if(bgSound.getSoundFlag())
            bgSound.play();

        accel.unpause();
        brakeCommand.unpause();
        leftCommand.unpause();
        rightCommand.unpause();

    }

    public boolean getPausedFlag(){
        return paused;
    }
}
