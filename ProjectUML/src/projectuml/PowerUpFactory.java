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
public class PowerUpFactory extends GeneralFactory<PowerUp> {

    /** Creates a new instance of PowerUpFactory */
    public PowerUpFactory() {
        add("Health", new HealthCreator());
        add("Missile launcher", new MissileLauncherCreator());
    }

    /**
     * Creates a health powerup.
     */
    private class HealthCreator extends GeneralFactoryCreator<PowerUp> {

        public PowerUp create() {
            PowerUp powerUp = new PowerUp("heart.png");
            powerUp.setTouchBehaviour(new HealShip(100));
            return powerUp;
        }
    }

    /**
     * Creates a weapon power-up (a missile launcher)
     */
    private class MissileLauncherCreator extends GeneralFactoryCreator<PowerUp> {

        public PowerUp create() {
            PowerUp powerUp = new PowerUp("missilepowerup.png");
            powerUp.setTouchBehaviour(new GiveWeapon(new MissileLauncher(true)));
            return powerUp;
        }
    }
}
