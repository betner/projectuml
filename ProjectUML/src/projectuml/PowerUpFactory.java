
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


  public static PowerUp createHealthPowerUp(int health){
      PowerUp powerUp = new PowerUp(new Point(), 0, "heart.png");
      powerUp.setTouchBehaviour(new HealShip(100));
      return powerUp;
  }
  
  public static PowerUp createMissileLauncherPowerUp(){
      PowerUp powerUp = new PowerUp(new Point(), 0, "missilepowerup.png");
      powerUp.setTouchBehaviour(new GiveWeapon(new MissileLauncher(true)));
      return powerUp;
  }
}
