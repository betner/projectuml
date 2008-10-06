package projectuml;

import java.awt.Point;

/**
 * PowerUpFactory
 * 
 * A simple helper class for creating powerups.
 * 
 * @see PowerUp
 * @author Steve Eriksson, Jens Thuresson
 */
public class PowerUpFactory {

    /** Creates a new instance of PowerUpFactory */
    public PowerUpFactory() {
    }

    /**
     * Create a health powerup.
     * 
     * @param position
     * @param health
     * @return PowerUp
     */
    public static PowerUp createHealthPowerUp(Point position, int health) {
        PowerUp powerUp = new PowerUp(position, "heart.png");
        powerUp.setTouchBehaviour(new HealShip(100));
        return powerUp;
    }

    /**
     * Create a weapon powerup, a missile launcher.
     * 
     * @param position
     * @return PowerUp
     */
    public static PowerUp createMissileLauncherPowerUp(Point position) {
        PowerUp powerUp = new PowerUp(position, "missilepowerup.png");
        powerUp.setTouchBehaviour(new GiveWeapon(new MissileLauncher(true)));
        return powerUp;
    }
}
