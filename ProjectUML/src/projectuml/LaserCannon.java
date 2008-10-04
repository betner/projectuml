
package projectuml;

import java.awt.Point;

/**
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class LaserCannon extends Weapon{
   
    private final int DAMAGE = 100; // Shot damage
    private final int DX = 10; // Shot movement, x-axis
    private final int DY = 0;
    
    /**
     * Creates a cannon with default values.
     * Position is (0,0) and it's a player weapon.
     */
    public LaserCannon(){
     this(new Point(), true);     
    }
    
    /** Creates a new instance of LaserCannon */
    public LaserCannon(Point position, Boolean isPlayer) {
        setPosition(position);
        setPlayer(isPlayer);
        setShotImageFile("lasershot.png");
    }
    
    /**
     * Fire weapon. Shots created gets position from the weapon translated
     * over the owning sprite's current position.
     * Depending on if it's a player that owns the weapon or an enemy we 
     * must make sure that the shot travels in the apropriate direction.
     *
     * @param level
     * @param sprite
     */
    public void fire(Level level, Sprite sprite){
        // Clone weapons position
        Point position = (Point)getPosition().clone();
        // Translate position over the sprite that owns the weapon
        position.translate(sprite.getIntPositionX(), sprite.getIntPositionY());
        Shot shot = new Shot(DAMAGE, position, getShotImageFile());

        // Make the shot travel in the correct direction
        if(isPlayer()){
            shot.setDx(DX);
            level.addPlayerShot(shot);
        }else{
            shot.setDx(DX * -1);
            level.addEnemyShot(shot);
        }
        level.playSound("lasershot");
    }
    
    public void playSound(){
        
    }
    
}
