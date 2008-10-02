
package projectuml;

import java.awt.*;

/**
 * MissileLauncher
 * 
 * Employs a cool down timer so that the number of rounds
 * per minute is restricted. 
 * When fired it creates missile shots in the level.
 * 
 * @see MissileShot
 * @author Steve Eriksson, Jens Thuresson
 */
public class MissileLauncher extends Weapon{
    
    private final int DAMAGE = 500;    // Shot damage
    private final int DX = 5;          // Shot movement, x-axis
    private final int DY = 0;
    private final int COOLDOWN = 1000; // Cooldown for weapon in seconds
    private Timestamp timestamp;
    
    /**
     * Create missile launcher at given point and
     * set cool down time to restrict rounds per minute
     * @param position
     */
    public MissileLauncher(Point position, boolean isPlayer){
        setPosition(position);
        setPlayer(isPlayer);
        setShotImageFile("missileshot.png");
        timestamp = new Timestamp();
    }
    
    /**
     * Overridden fire method
     * Uses a timer to check if weapons cooldown time has passed.
     */
    public void fire(Level level){
        if(timestamp.havePassed(COOLDOWN)){
            Point position = clone(getPosition());
            Shot shot = new Shot(DAMAGE, position, getShotImageFile());

            if(isPlayer()){
                shot.setDx(DX);
                level.addPlayerShot(shot);
            }else{
                // TODO:
                // Make sure the image gets flipped 90 deg
                shot.setDx(DX * -1);
                level.addEnemyShot(shot);
            }
            level.playSound("missileshot");
            timestamp.reset();
        }
    }
    
    public void playSound(){
        
    }
    
    //DEBUG Method
    public void fire(TestDrive td){
        System.out.println("hej");
    }
}
