package projectuml;

import java.awt.*;
import java.awt.event.*;

/**
 * MainMenu
 * 
 * Main menu in the game.
 *
 * @see GameState
 * @see GameStates
 * @author Jens Thuresson, Steve Eriksson
 */
public class MainMenu extends GameState {

    private StarField background;
    private SoundPlayer sound;
    private FadeText logo;
    private FadeText credits;
    private Timestamp stamp;

    /**
     * Initiates the main menu.
     */
    public MainMenu() {
        background = new StarField(200);

        // Main logo
        logo = new FadeText("PROJECT U.M.L.", Color.white);
        logo.setPosition(10, 240);
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        logo.fadeIn();

        // Credits
        credits = new FadeText("By Steve Eriksson & Jens Thuresson", Color.gray);
        credits.setPosition(10, 260);
        credits.setFont(new Font("Arial", Font.PLAIN, 10));
        credits.fadeIn(2);

        // Load theme sound and play it!
        sound = new SoundPlayer();
        sound.loadSound("mainmenu.wav");

        // Record the current time
        stamp = new Timestamp();
    }

    /**
     * Draws the main menu.
     *
     * @param g2D
     */
    public void draw(Graphics2D g2D) {
        background.draw(g2D);
        logo.draw(g2D);
        credits.draw(g2D);
    }

    /**
     * Respond to keyevents.
     * 
     * @param event Key event
     * @param down Is key down?
     */
    public void keyEvent(KeyEvent event, boolean down) {
        if (!down) {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_P:
                case KeyEvent.VK_PAUSE:
                    getGameStateManager().push(new Pause());
                    break;

                case KeyEvent.VK_F12:
                    getGameStateManager().push(new LevelEditor());
                    break;

                default:
                    // Any other key starts the game
                    getGameStateManager().change(new GameRunning());
                    break;
            }
        }
    }

    /** Not used **/
    public void mouseEvent(MouseEvent event) {
    }

    /**
     * Updates the main menu.
     * 
     * @param player Active player
     */
    public void update(Player player) {
        background.update();

        // Delay the nice fading
        if (stamp.havePassed(2000)) {
            logo.update();

            // When the main logo is fully visible,
            // start fading in the credits section
            // When the main logo is fully visible,
            // start fading in the credits section
            if (logo.finished()) {
                credits.update();
            }
        }
    }

    /**
     * We've lost focus, pause our music.
     */
    public void lostFocus() {
        sound.stop("mainmenu");
    }

    /**
     * We've gained focus, resume our music.
     */
    public void gainedFocus() {
        sound.loopPlay("mainmenu");
    }
}
