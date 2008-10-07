package projectuml;

import java.awt.*;
import java.awt.event.*;

/**
 * Victory screen - the player completed the
 * game!
 * 
 * @author Jens Thuresson, Steve Eriksson
 */
public class VictoryScreen extends GameState {

    private FadeText shadow;
    private FadeText victory;
    private EnemyFactory factory;
    private Level level;
    private PlayerShip playership;
    private Font smallfont;
    private Timestamp timestamp;
    
    /**
     * Initiates the victory screen
     */
    public VictoryScreen() {
        victory = new FadeText("Victory!", Color.white);
        shadow = new FadeText(victory.getText(), new Color(32, 32, 32, 20));
        
        // Position text etc.
        victory.setFont(new Font("Arial", Font.BOLD, 40));
        shadow.setFont(new Font("Arial", Font.BOLD, 40));
        Point where = new Point(240, 200);
        victory.setPosition(where);
        where.translate(5, 5);
        shadow.setPosition(where);
        
        // Start fading
        victory.fadeIn();
        shadow.fadeIn();
        
        // Rest
        timestamp = new Timestamp();
        smallfont = new Font("Arial", Font.PLAIN, 10);
        factory = new EnemyFactory();
        initLevel();
    }
    
    /**
     * Initiates the fake level
     */
    private void initLevel() {
        level = new Level();
        level.setScenery(new StarField(200));
        
        // Fake playership
        playership = new PlayerShip(new Player());
    }
    
    /**
     * Update the victory screen
     * 
     * @param player Active player
     */
    public void update(Player player) {
        level.update(playership);
        victory.update();
        shadow.update();
        playership.update(level);
        
        // Add monsters if enough time has passed
        if (timestamp.havePassed(4000)) {
            // Create cyclic path with random points
            Path path = new Path(true);
            int num = Randomizer.getRandomNumber(4, 12);
            while (num-- > 0) {
                int x = Randomizer.getRandomNumber(0, 640);
                int y = Randomizer.getRandomNumber(0, 480);
                path.addPoint(new Point(x, y));
            }
            
            // Create enemy and add it
            EnemyShip ship = factory.createRandom();
            ship.setPath(path);
            level.addShip(ship);
            
            // Reset timer
            timestamp.reset();
        }
    }
    
    /**
     * Draw the victory screen
     * 
     * @param g2D Where to draw
     */
    public void draw(Graphics2D g2D) {
        level.draw(g2D);
        shadow.draw(g2D);                
        victory.draw(g2D);
        playership.draw(g2D);
        
        if (true) { //victory.finished()) {
            g2D.setFont(smallfont);
            g2D.setColor(Color.white);
            g2D.drawString("Congratulations, you have completed the game!",
                    (int)victory.getPosition().getX() - 40,
                    (int)victory.getPosition().getY() + 100);
            g2D.drawString("But the princess is in another castle!",
                    (int)victory.getPosition().getX() - 10,
                    (int)victory.getPosition().getY() + 140);
        }
    }
    
    /**
     * Right key to proceed to main menu?
     * 
     * @param event Key event
     * @return True if it's the right one
     */
    private boolean isRightKey(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_ESCAPE:
                return true;
                
            default:
                // Nope, not the right one
                return false;
        }
    }

    /**
     * Respond to key events
     * 
     * @param event Key event
     * @param down Is key down?
     */
    public void keyEvent(KeyEvent event, boolean down) {
        if (!down) {
            // Return to main menu on enter and ESC
            if (isRightKey(event)) {
                getGameStateManager().change(new MainMenu());
            }
        }
    }

    /**
     * Respond to mouse event
     * 
     * @param event Mouse event
     */
    public void mouseEvent(MouseEvent event) {
        
    }

    /**
     * We've gained focus
     */
    public void gainedFocus() {
        
    }

    /**
     * We've lost focus
     */
    public void lostFocus() {
        
    }

}
