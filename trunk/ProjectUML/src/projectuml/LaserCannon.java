
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
    
    /** Creates a new instance of LaserCannon */
    public LaserCannon(Point position, Boolean isPlayer) {
        setPosition(position);
        setPlayer(isPlayer);
        setShotImageFile("lasershot.png");
        setSoundFile("");
    }
    
    public void fire(Level level){
        // ToDo:
        // add code for cool down timer
        //level.addShot(new LaserShot(position));
        Point position = clone(getPosition());
        Shot shot = new Shot(DAMAGE, position, getShotImageFile());

        if(isPlayer()){
            shot.setDx(DX);
  //          level.addPlayerShot(shot);
        }else{
            shot.setDx(DX * -1);
//            level.addEnemyShot(shot);
        }
        
    }
    
    public void playSound(){
        
    }
    
    public void fire(TestDrive td){
        System.out.println("Weapon: fire()");
        Point position = clone(getPosition());
        Shot shot = new Shot(DAMAGE, DX, DY , position, getShotImageFile());
        shot.activate();
        shot.show();
        td.addShot(shot);
        System.out.println("Shot pos: " + position);
       
    }
    
}