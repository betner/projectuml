package projectuml;

import java.awt.*;
import java.awt.event.*;

/**
 * GameOver 
 * 
 * Game over, man! Game over!
 * 
 * @author Jens Thuresson, Steve Eriksson
 */
public class GameOver extends GameState {

    private StarField scenery;
    private SoundPlayer sound;
    private FadeText header;

    /**
     * Initiates the game over-state.
     */
    public GameOver() {
        // Header text
        header = new FadeText("GAME OVER", Color.white);
        header.setPosition(10, 240);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.fadeIn();

        // Background
        scenery = new StarField(200);

        // Sound
        sound = new SoundPlayer();
        sound.loadSound("gameover.wav");
    }

    /**
     * Updates the state.
     * 
     * @param player
     */
    public void update(Player player) {
        scenery.update();
        header.update();

        if (header.finished()) {
            header.fadeOut();
        }
    }

    /**
     * Draws the state.
     * 
     * @param g
     */
    public void draw(Graphics2D g) {
        //scenery.draw(g);
        header.draw(g);
    }

    /**
     * Respond to key events.
     * 
     * @param event
     * @param down Is key down?
     */
    public void keyEvent(KeyEvent event, boolean down) {
        // Any key (up) event means: go back to main menu
        if (!down) {
            getGameStateManager().change(new MainMenu());
        }
    }

    /**
     * Respond to mouse events.
     * 
     * @param event
     */
    public void mouseEvent(MouseEvent event) {
    }

    /**
     * Our state has gained focus.
     */
    public void gainedFocus() {
    }

    /**
     * Our state has lost focus.
     */
    public void lostFocus() {
    }
}
