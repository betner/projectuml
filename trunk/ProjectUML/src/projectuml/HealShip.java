
package projectuml;

/**
 * HealShip
 * 
 * This behaviour heals ships.
 * 
 * @author Steve Eriksson, Jens Thuresson
 */
public class HealShip extends TouchBehaviour{
    
    private int health;
    
    /**
     * Creates a new instance of hurt
     */
    public HealShip(int health){
        this.health = health;
    }
    
    /**
     * If the type of object that is touching is a ship, increase it's health.
     */
    public void action(Sprite sprite){
       if(sprite instanceof Ship){
        System.out.println("heal: action(Ship)"); 
        Ship ship = (Ship)sprite;
        ship.increaseHealth(health);
       }
    }
}