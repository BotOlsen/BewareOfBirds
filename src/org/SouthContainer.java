package org.csc133.a3;

/**
 * Represents South Container
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.layouts.GridLayout;

public class SouthContainer extends Container {

    /**
     * Constructs the south container
     * @param commands Takes array of Commands to assign to buttons
     */
    public SouthContainer(Command[] commands){
        this.setLayout(new GridLayout(1,4));
        for(int i = 0; i <= 3; i++)
            this.add(new SouthButton("/arrow" + i + ".png", commands[i]));
        this.getAllStyles().setMargin(0,0,0,0);
        this.getAllStyles().getPadding(0);
    }

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

    /**
     * {@inheritDoc}
     */
    public void paintComponentBackground(Graphics g) {
        super.paint(g);
        final int COLOR_PAD = 1;

        g.setColor(ColorUtil.BLACK);                            //Put a black rectangle behind everything
        g.fillRect(getX(), getY(), getWidth(), getHeight());

    }

}
