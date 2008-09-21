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
    
    /**
     * Initiates the main menu
     **/
    public MainMenu() {
        font = new Font("Arial", Font.BOLD, 30);
        background = new StarField(500);
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
        g.drawString("Project U.M.L.", 0, 30);
        
    }

    public void keyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_PAUSE) {
            gamestates.push(new Pause());
        }
    }

    public void mouseEvent(MouseEvent event) {
    }

    public void update(Player player) {
        background.update();
    }
   
}
