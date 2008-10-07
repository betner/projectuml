package projectuml;

import java.awt.Point;

/**
 * LaserCannon
 * 
 * @see Weapon
 * @see Shot
 * @author Steve Eriksson, Jens Thuresson
 */
public class LaserCannon extends Weapon {

    private final int DAMAGE = 100; // Shot damage
    private final int DX = 10; // Shot movement, x-axis
    private final int DY = 0;

    /**
     * Creates a new instance of LaserCannon with default values.
     * Position is (0,0) and it's a player weapon.
     */
    public LaserCannon() {
        this(new Point(), true);

    }

    /**
     * Creates a new instance of LaserCannon.
     * 
     * @param isPlayer true if weapon is a player weapon
     */
    public LaserCannon(boolean isPlayer) {
        this(new Point(), isPlayer);
    }

    /** 
     * Creates a new instance of LaserCannon. 
     *
     * @param position 
     * @param isPlayer
     */
    public LaserCannon(Point position, Boolean isPlayer) {
        setPosition(position);
        setPlayer(isPlayer);
        setShotImageFile("lasershot.png");
    }

    /**
     * Fire weapon. Shots created gets position from the weapon translated
     * over the owning sprite's current position.
     * Depending on if it's a player that owns the weapon or an enemy we 
     * must make sure that the shot travels in the apropriate direction.
     *
     * @param level
     * @param sprite
     */
    public void fire(Level level, Sprite sprite) {
        // Clone weapons position
        Point position = (Point) getPosition().clone();

        // Translate position over the sprite that owns the weapon
        position.translate(sprite.getIntPositionX(), sprite.getIntPositionY());
        Shot shot = new Shot(DAMAGE, position, getShotImageFile());

        // Make the shot travel in the correct direction
        if (isPlayer()) {
            shot.setDx(DX);
            level.addPlayerShot(shot);
        } else {
            shot.setDx(DX * -1); // Reverse direction
            level.addEnemyShot(shot);
        }
        level.playSound("shot1");
    }

    public void playSound() {
    }
}
