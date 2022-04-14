package org.csc133.a3;

/** Represents a GlassCockpit
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.table.TableLayout;
import java.io.IOException;


public class GlassCockpit extends Container implements IObserver {

    private GameClockComponent dclockc;
    private FuelComponent fuelc;
    private LivesComponent livesc;
    private LastComponent lastc;
    private HeadingComponent headc;
    private DamageComponent dmgc;
    private Label clockLabel;
    private Label fuelLabel;
    private Label livesLabel;
    private Label lastLabel;
    private Label headingLabel;
    private Label damageLabel;
    private GameWorld gameWorld;


    /**
     * Constructor for the GlassCockpit
     * @param gw GameWorld to be used to get values for components
     */
    public GlassCockpit(GameWorld gw) {
        gameWorld = gw;

        dclockc = new GameClockComponent();
        fuelc = new FuelComponent();
        livesc = new LivesComponent();
        lastc = new LastComponent();
        headc = new HeadingComponent();
        dmgc = new DamageComponent();

        try{
            clockLabel = new Label(Image.createImage("/Clock.png"));
            fuelLabel = new Label(Image.createImage("/Fuel.png"));
            livesLabel = new Label(Image.createImage("/Lives.png"));
            lastLabel = new Label(Image.createImage("/Last.png"));
            headingLabel = new Label(Image.createImage("/Head.png"));
            damageLabel = new Label(Image.createImage("/Dmg.png"));
        }catch (IOException e) {e.printStackTrace();}

        init();
    }

    /**
     * Initializes all the components of GlassCockpit
     */
    private void init(){
        TableLayout t1 = new TableLayout(2,6);
        t1.setGrowHorizontally(true);
        this.setLayout(t1);
        this.add(t1.createConstraint().widthPercentage(21).horizontalAlign(Component.CENTER),clockLabel);
        this.add(t1.createConstraint().widthPercentage(16).horizontalAlign(Component.CENTER),fuelLabel);
        this.add(t1.createConstraint().widthPercentage(16).horizontalAlign(Component.CENTER),livesLabel);
        this.add(t1.createConstraint().widthPercentage(16).horizontalAlign(Component.CENTER),lastLabel);
        this.add(t1.createConstraint().widthPercentage(16).horizontalAlign(Component.CENTER),headingLabel);
        this.add(t1.createConstraint().widthPercentage(16).horizontalAlign(Component.CENTER),damageLabel);

        this.setWidth(getInnerWidth());
        this.setHeight((int)(getInnerHeight() * .2));

        this.add(dclockc);
        this.add(fuelc);
        this.add(livesc);
        this.add(lastc);
        this.add(headc);
        this.add(dmgc);
    }

    /**
     * Called to update the components of the View
     */
    public void update(){
        fuelc.setFuel(gameWorld.getGameObjectCollection().getPlayerHelicopter().getFuelLevel());
        dmgc.setDmg(gameWorld.getGameObjectCollection().getPlayerHelicopter().getDamageLevel());
        livesc.setLives(gameWorld.getLives());
        lastc.setLast(gameWorld.getGameObjectCollection().getPlayerHelicopter().getLastSkyScraperReached());
        headc.setHeading(gameWorld.getGameObjectCollection().getPlayerHelicopter().getHeading());
        fuelc.repaint();
        dmgc.repaint();
        livesc.repaint();
        lastc.repaint();
        headc.repaint();
    }

    /**
     * {@inheritDoc}
     */
    public void paintComponentBackground(Graphics g) {
        super.paint(g);
        final int COLOR_PAD = 1;

        g.setColor(ColorUtil.BLACK);                            //Put a black rectangle behind everything
        g.fillRect(getX(), getY(), getWidth(), getHeight());

    }

    /**
     * Registers Form as animated
     */
    public void start(){
        getComponentForm().registerAnimated(this);
    }

    /**
     * This is a callback method to inform the Component when it's been laidout
     * on the parent Container
     */
    public void laidOut(){
        this.start();
    }

    /**
     * {@inheritDoc}
     */
    public boolean animate(){
        return true;
    }

}
