package projectuml;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.JFrame;

/**
 *
 * @author steeri
 */
public class TestDrive extends JFrame implements KeyListener{
    
    Player player;
    PlayerShip ship;
    ArrayList<Shot> shots;
    
    public TestDrive(){
        shots = new ArrayList<Shot>();
        player = new Player();
        ship = new PlayerShip(player);
        addKeyListener(this);
        setSize(400, 400);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        ship.show();
        ship.activate();
    }
    
    public void run(){
        while(true){
            try{
               ship.update();
               for(Shot shot : shots){
                   shot.update();
               }
               repaint();
            
            Thread.sleep(30);
            }catch(Exception e){
                System.out.println("Exception in run(): " + e);
            }
        }
    }
    
    public void addShot(Shot shot){
        shots.add(shot);
    }
    
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getWidth());
        ship.draw((Graphics2D)g);
        for(Shot shot : shots){
            shot.draw((Graphics2D)g);
           // System.out.println("shot.draw()");
        }
    }

    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                ship.destroyShip();
                System.out.println("Space");
                System.out.println(player.getLives());
                break;
            case KeyEvent.VK_LEFT:
                ship.goLeft();
                System.out.println("VK_LEFT");
                break;
            case KeyEvent.VK_RIGHT:
                ship.goRight();
                System.out.println("VK_RIGHT");
                break;
            case KeyEvent.VK_UP:
                ship.goUp();
                System.out.println("VK_UP");
                break;
            case KeyEvent.VK_DOWN:
                ship.goDown();
                System.out.println("VK_DOWN");
                break;
            case KeyEvent.VK_F:
                ship.fire(this);
                System.out.println("VK_F");
            default:
                break;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        //gamestates.keyEvent(e);
         switch(e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                ship.destroyShip();
                System.out.println("Space");
                break;
            case KeyEvent.VK_LEFT:
                ship.resetDx();
                System.out.println("VK_LEFT");
                break;
            case KeyEvent.VK_RIGHT:
                ship.resetDx();
                System.out.println("VK_RIGHT");
                break;
            case KeyEvent.VK_UP:
                ship.resetDy();
                System.out.println("VK_UP");
                break;
            case KeyEvent.VK_DOWN:
                ship.resetDy();
                System.out.println("VK_DOWN");
                break;
             default:
                break;
        }
    }
    
    public void keyTyped(KeyEvent e) {
        //gamestates.keyEvent(e);
    }
    
    public static void main(String[] args){
        TestDrive td = new TestDrive();
        td.run();
    }
}
