package projectuml;

import java.awt.*;
import java.awt.event.*;

/**
 * Main menu in the game
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class MainMenu extends GameState {
    
    private Font font;
    private StarField background;
    private Point point;
    private SoundPlayer sound;
    
    /**
     * Initiates the main menu
     **/
    public MainMenu() {
        point = new Point(0, 0);
        font = new Font("Arial", Font.BOLD, 30);
        background = new StarField(500);
        
        // Load theme sound and play it!
        sound = new SoundPlayer();
        sound.loadSound("theme.wav");
        sound.loopPlay("theme");
    }
    
    /**
     * Draws the main menu
     *
     * @param g
     **/
    public void draw(Graphics2D g) {
        background.draw(g);
        g.setFont(font);
        g.setColor(Color.red);
        g.drawString("Project U.M.L.", point.x, point.y);
        
    }

    public void keyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_PAUSE) {
            System.out.println("MainMenu pause");
            gamestates.push(new Pause());
        }
    }

    public void mouseEvent(MouseEvent event) {
        point.setLocation(event.getPoint());
    }

    public void update(Player player) {
        background.update();
    }

    /**
     * We've lost focus, pause our music
     */
    public void lostFocus() {
        // TODO: fix this
        sound.fadeOutEverything();
    }
   
    /**
     * We've gained focus, resume our music
     */
    public void gainedFocus() {
        // TODO: fix this
        sound.loopPlay("theme");
    }
}
