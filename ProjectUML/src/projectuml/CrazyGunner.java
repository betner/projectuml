
package projectuml;

/**
 *
 * @author Steve Eriksson
 */
public class CrazyGunner extends Gunner{
    
    private int time;
    
    /** Creates a new instance of CrazyGunner */
    public CrazyGunner(Level level, Ship ship) {
        setShip(ship);
        //setLevel(level);
        setTimestamp(new Timestamp());
        
        // time is between 0 and 2 seconds
        time = Randomizer.getRandomNumber(0, 2000); 
    }
    
    public void update(Level level){
        if(getTimestamp().havePassed(time)){
            // time is between 0 and 2 seconds
            time = Randomizer.getRandomNumber(0, 2000); 
            //getShip().fire(getLevel());
            getShip().fire(level);
        }
    }
}
