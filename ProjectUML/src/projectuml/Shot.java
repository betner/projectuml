
package projectuml;
import java.awt.Point;
import java.awt.image.BufferedImage;


/**
 * Shot
 * 
 * Created by a weapon when it is fired.
 * A shot can cause damage to game objects.
 * Delta X and Delta Y are set to zero by default.
 *
 * @see Weapon
 * @author Steve Eriksson, Jens Thuresson
 */
public class Shot extends Sprite{
  
    //private double velocity; // Shot's speed
    private double dx;  // Movement in x-direction, 0 by default
    private double dy;  // Movement in y-direction, 0 by default
    private int damage; // Damage done on impact
    
        
    /** Creates a new instance of Shot */
    public Shot() {
    }
        
    public Shot(int dx, int dy, Point position, String imageFile){
        this(position, imageFile);
        setDx(dx);
        setDy(dy);
    }
    
    public Shot(Point position, String imageFile){
        setDx(0);
        setDy(0);
        setImageFile(imageFile); // Set image path
        setImage(loadImage(getImageFile())); // set the loaded image
        setPosition(position);
    }
    /**
     * Update shot's position. By default we move it horisontally 
     * depending on it's velocity.
     */
    public void update(){
        double x = getPositionX() + dx;
        double y = getPositionY() + dy;
        setPosition(x, y);
    }
    
    /**
     * Overriding Sprite's touch method.
     * If shot touches a ship it should cause damage
     */
    public void touch(Ship ship){
       ship.decreaseHealth(damage);
       hide();
       deactivate();
    }
    
    public int getDamage(){
        return damage;
    }
    
    public void setDamage(int newDamage){
        damage = newDamage;
    }
                    
    public void setDx(int newDx){
        dx = newDx;
    }
    public void setDy(int newDy) {
        dx = newDy;
    }
        
    public void setDx(double newDx) {
        dx = newDx;
    }
    public void setDy(double newDy) {
        dx = newDy;
    }
}
