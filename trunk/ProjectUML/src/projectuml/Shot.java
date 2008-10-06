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
public class Shot extends Sprite {

    private int dx;  // Movement in x-direction
    private int dy;  // Movement in y-direction
    private int damage; // Damage done on impact

    /**
     * Creates a new instance of Shot 
     */
    public Shot() {
    }

    /**
     * Creates new instance of Shot with default values
     * for dx, and dy.
     * 
     * @param damage
     * @param position
     * @param imageFile
     */
    public Shot(int damage, Point position, String imageFile) {
        this(damage, 0, 0, position, imageFile);
    }

    /**
     * Creates new instance of Shot.
     * 
     * @param damage
     * @param dx
     * @param dy
     * @param position
     * @param imageFile
     */
    public Shot(int damage, int dx, int dy, Point position, String imageFile) {
        setTouchBehaviour(new HurtShip(damage));
        setDx(dx);
        setDy(dy);
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
     * Set shot's dx.
     * 
     * @param newDx
     */
    public void setDx(int newDx) {
        dx = newDx;
    }

    /**
     * Set shot's dy.
     * 
     * @param newDy
     */
    public void setDy(int newDy) {
        dy = newDy;
    }

    /**
     * DX can be set with double values
     * but they will be downcast and can loose
     * some precision.
     *
     * @param newDx
     */
    public void setDx(double newDx) {
        dx = (int) newDx;
    }

    /**
     * DY can be set with double values
     * but they will be downcast and can loose
     * some precision.
     *
     * @param newDy
     */
    public void setDy(double newDy) {
        dy = (int) newDy;
    }
}
