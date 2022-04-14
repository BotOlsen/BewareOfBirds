package org.csc133.a3;

/** Represents a Button
 * @author  Daniel Olsen
 * @version 2.0
 * @since   11/20/2020
 */

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Image;

import java.io.IOException;

public class SouthButton extends Button {

    /**
     * Constructor for button in South Container
     * @param imagePath String for the image file path
     * @param command Command to execute on click
     */
    public SouthButton(String imagePath, Command command) {
        this.addActionListener(command);
        try {
            this.setIcon(Image.createImage(imagePath));
        }catch(IOException e){e.printStackTrace();}
    }
}
