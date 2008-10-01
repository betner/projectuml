
package projectuml;

/**
 *
 * @author Steve Eriksson
 */
public class CrazyGunner extends Gunner{
    
    /** Creates a new instance of CrazyGunner */
    public CrazyGunner() {
    }
    
    public void update(Ship ship){
        ship.fire();
    }
}
