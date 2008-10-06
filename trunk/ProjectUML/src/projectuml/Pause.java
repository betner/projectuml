package projectuml;

import java.awt.*;
import java.awt.event.*;

/**
 * Pause
 * 
 * A overengineered pause screen.
 * 
 * @see GameState
 * @see GameStates
 * @author Jens Thuresson, Steve Eriksson
 */
public class Pause extends GameState {

    private Font font;
    private Boolean closing;
    private int alpha;
    private final int MAX_ALPHA = 180;

    /**
     * Initiates the pause screen
     */
    public Pause() {
        font = new Font("Arial", Font.BOLD, 120);
        alpha = 0;
        closing = false;
    }

    /**
     * Displays our fancy pause screen.
     * 
     * @param g2D Graphics2D
     */
    public void draw(Graphics2D g2D) {
        // Fade out background
        g2D.setColor(new Color(0, 0, 0, alpha));
        g2D.fillRect(0, 0, 640, 480);

        // If alpha-blending doesn't work, we'll
        // have to use this 80-ish method instead
//        g.setColor(Color.black);
//        for (int y=0; y < 480; y += 2) {
//            g.drawLine(0, y, 640, y);
//        }

        // Draw text, final alpha value will be 255
        g2D.setColor(new Color(255, 255, 255, alpha + (255 - MAX_ALPHA)));
        g2D.setFont(font);
        g2D.drawString("Paused", 0, 480);
    }

    /**
     * We're ready to close.
     * 
     * @param event
     */
    public void keyEvent(KeyEvent event, boolean down) {
        closing = true;
    }

    /** Not used here **/
    public void mouseEvent(MouseEvent event) {
    }

    /**
     * Updates our alpha value for fading out/in.
     * 
     * @param player
     */
    public void update(Player player) {
        // If we're closing, fade up, otherwise
        // tone down
        if (closing) {
            if (alpha > 0) {
                alpha -= 40;
            } else {
                removeMe();
            }
            // Clamp values
            if (alpha < 0) {
                alpha = 0;
            }
        } else {
            // Fade in up to our max alpha
            if (alpha < MAX_ALPHA) {
                alpha += 10;
            }
        }
    }

    /** Not used **/
    public void lostFocus() {
    }

    /** Not used **/
    public void gainedFocus() {
    }
}
