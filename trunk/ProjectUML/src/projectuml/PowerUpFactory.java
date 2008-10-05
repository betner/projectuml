
package projectuml;

import java.awt.Point;


/**
 *
 * @author Steve Eriksson
 */
public class PowerUpFactory {
    
    /** Creates a new instance of PowerUpFactory */
    public PowerUpFactory() {
    }


  public static PowerUp createHealthPowerUp(Point position, int health){
      PowerUp powerUp = new PowerUp(position, "heart.png");
      powerUp.setTouchBehaviour(new HealShip(100));
      return powerUp;
  }
  
  public static PowerUp createMissileLauncherPowerUp(Point position){
      PowerUp powerUp = new PowerUp(position, "missilepowerup.png");
      powerUp.setTouchBehaviour(new GiveWeapon(new MissileLauncher(true)));
      return powerUp;
  }
}
