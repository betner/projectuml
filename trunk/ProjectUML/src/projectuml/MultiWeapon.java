
package projectuml;

import java.awt.Point;

/**
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class MultiWeapon extends Weapon {
    
    public MultiWeapon() {
        setShotImageFile("lasershot.png");
    }
    
    public void fire(Level level, Sprite sprite) {
        level.addEnemyShot(new Shot(1, -5, 5, new Point(sprite.getPosition()), getShotImageFile()));
        level.addEnemyShot(new Shot(1, -5, -5, new Point(sprite.getPosition()), getShotImageFile()));
    }
    
}
