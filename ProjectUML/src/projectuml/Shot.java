
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
    private int dx;  // Movement in x-direction
    private int dy;  // Movement in y-direction
    private int damage; // Damage done on impact
    
    
    /** Creates a new instance of Shot */
    public Shot() {
    }
    
    public Shot(int damage,Point position, String imageFile){
        this(damage, 0, 0, position, imageFile);
    }
    
    public Shot(int damage, int dx, int dy, Point position, String imageFile) {
        this.damage = damage;
        setDx(dx);
        setDy(dx);
        //setImageFile(imageFile); // Set image path
        //setImage(loadImage(getImageFile())); // set the loaded image
        loadImageFrom(imageFile);
        setPosition(position);
    }
    
    /**
     * Update shot's position. By default we move it horisontally
     * depending on it's velocity.
     */
    public void update() {
        if (isActive()) {
            updatePosition(dx, dy);
        }
    }
    
    /**
     * Overriding Sprite's touch method.
     * If shot touches a ship it should cause damage.
     * Only runs if we're active
     */
    public void touch(Ship ship) {
        if (isActive()) {
            ship.decreaseHealth(damage);
            hide();
            deactivate();
        }
    }
    
    public void setDx(int newDx){
        dx = newDx;
    }
    public void setDy(int newDy) {
        dx = newDy;
    }
    
    /**
     * DX and DY can be set with double values
     * but they will be downcast and can loose
     * some precision.
     *
     * @param newDx
     */
    public void setDx(double newDx) {
        dx = (int)newDx;
    }
    public void setDy(double newDy) {
        dy = (int)newDy;
    }
}
