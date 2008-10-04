
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
      powerUp.setTouchBehaviour(new heal(100));
      return powerUp;
  }
}
