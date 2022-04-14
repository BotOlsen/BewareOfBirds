package org.csc133.a3;

/**
 * @author  Daniel Olsen
 * @version 1.0
 * @since   2020-10-12
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose
 * of building native mobile applications using Java.
 */

import static com.codename1.ui.CN.*;
import static com.codename1.ui.util.UITimer.timer;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;


public class AppMain {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
    }

    public void start() {
        if(current != null){
            current.show();
            return;
        }
        timer(35, true, new Game());
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroy() {
    }
}