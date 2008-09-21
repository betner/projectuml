package projectuml;

import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class Pause extends GameState {
    
    private Font font;
    private final String MESSAGE = "Press any key";
    
    public Pause() {
        font = new Font("Courier New", Font.ITALIC, 20);
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        // 640x480 screen, 320 middle
        g.fillRect(0, 300, 640, 40);
        g.setColor(Color.black);
        g.drawString(MESSAGE, 0, 340);
    }

    public void keyEvent(KeyEvent event) {
        removeMe = true;
    }

    public void mouseEvent(MouseEvent event) {
    }

    public void update(Player player) {
    }
    
}
