package projectuml;

import java.awt.Point;

/**
 * MultiWeapon 
 * 
 * Shoots in several directions.
 * 
 * @author Jens Thuresson, Steve Eriksson
 */
public class MultiWeapon extends Weapon {

    /**
     * Create new instance of MultiWeapon.
     */
    public MultiWeapon() {
        setShotImageFile("lasershot.png");
    }

    /**
     * Fire weapon.
     * 
     * @param level
     * @param sprite
     */
    public void fire(Level level, Sprite sprite) {
        level.addEnemyShot(new Shot(50, -5, 5, new Point(sprite.getPosition()), getShotImageFile()));
        level.addEnemyShot(new Shot(100, -5, -5, new Point(sprite.getPosition()), getShotImageFile()));
        level.addEnemyShot(new Shot(50, -5, 0, new Point(sprite.getPosition()), getShotImageFile()));
        level.playSound("lasershot");
    }
}
