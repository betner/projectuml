
package projectuml;

import java.awt.Point;

/**
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class LaserCannon extends Weapon{
    
   
    
    /** Creates a new instance of LaserCannon */
    public LaserCannon(Point position, Boolean isPlayer) {
        setPosition(position);
        setPlayer(isPlayer);
        setShotImageFile("lasershot.png");
        
        //DEBUG
        System.out.println("LaserCannon created");
        System.out.println("At position: " + getPosition());
        System.out.println("Point:" + getPosition().getClass().hashCode());
        
    }
    
    public void fire(Level level){
        // ToDo:
        // add code for cool down timer
        //level.addShot(new LaserShot(position));
        Point position = clone(getPosition());
        Shot shot = new Shot(position, getShotImageFile());

        if(isPlayer()){
            shot.setDx(1);
  //          level.addPlayerShot(shot);
        }else{
            shot.setDx(-1);
//            level.addEnemyShot(shot);
        }
        
    }
    
    public void fire(TestDrive td){
        System.out.println("Weapon: fire()");
        Point position = clone(getPosition());
        Shot shot = new Shot(-10, 1, position, getShotImageFile());
        shot.activate();
        shot.show();
        td.addShot(shot);
        System.out.println("Shot pos: " + position);
       
    }
    
}
